package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.model.entity.FileChangedEvent
import cafe.adriel.voxrecorder.model.entity.Recording
import com.eightbitlab.rxbus.Bus
import com.github.phajduk.rxfileobserver.FileEvent
import com.github.phajduk.rxfileobserver.RxFileObserver
import com.pawegio.kandroid.runAsync
import com.pawegio.kandroid.runOnUiThread
import rx.Observable

class RecordingRepository: IRepository<Recording> {

    private var fileObserver: Observable<FileEvent>? = null

    override fun initFileObserver() {
        if(fileObserver == null){
            fileObserver = RxFileObserver.create(Constant.RECORDING_FOLDER)
            fileObserver?.subscribe({
                if(it.isCreate || it.isModify || it.isDelete || it.isMovedFrom || it.isMovedTo) {
                    Bus.send(FileChangedEvent(it))
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

    override fun get(callback: (List<Recording>) -> Unit) {
        runAsync {
            val recordings = Constant.RECORDING_FOLDER.listFiles { file, s ->
                Constant.SUPPORTED_EXTENSIONS.any { s.endsWith(it, true) }
            }.map {
                Recording(it.path)
            }.sortedByDescending {
                it.date
            }
            runOnUiThread {
                callback(recordings)
            }
        }
    }

}