package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.RecordingChangedEvent
import cafe.adriel.voxrecorder.util.Util
import com.eightbitlab.rxbus.Bus
import com.github.phajduk.rxfileobserver.FileEvent
import com.github.phajduk.rxfileobserver.RxFileObserver
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class RecordingRepository: IRepository<Recording> {

    private var fileObserver: Observable<FileEvent>? = null

    override fun initFileObserver() {
        if(fileObserver == null){
            fileObserver = RxFileObserver.create(Constant.RECORDING_FOLDER)
            fileObserver!!
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .retry()
                .distinctUntilChanged()
                .subscribe({ evt ->
                    if(canHandleEvent(evt) && Util.isSupportedFormat(evt.path)){
                        Bus.send(RecordingChangedEvent(evt))
                    }
                })
        }
    }

    override fun add(item: Recording) {

    }

    override fun update(item: Recording) {

    }

    override fun remove(item: Recording) {

    }

    override fun get(): Observable<ArrayList<Recording>> {
        return Observable.defer {
            val files = Constant.RECORDING_FOLDER.listFiles(){ file, name ->
                Util.isSupportedFormat(name)
            }
            Observable.from(files)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { file ->
                Recording(file.path)
            }.toSortedList { r1, r2 ->
                r2.date.compareTo(r1.date)
            }.map { recordings ->
                ArrayList(recordings)
            }
        }
    }

    private fun canHandleEvent(evt: FileEvent) =
            evt.isCreate || evt.isModify || evt.isDelete || evt.isMovedFrom || evt.isMovedTo

}