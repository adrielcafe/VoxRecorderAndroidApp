package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.util.Util
import rx.Observable

class RecordingRepository: IRepository<Recording> {

    override fun get(): Observable<Recording> = Observable.defer {
        Observable.from(Constant.RECORDING_FOLDER.listFiles(){ file ->
            Util.isSupportedFormat(file.name)
        })
        .map { Recording(it.path) }
    }

    override fun add(item: Recording) {

    }

    override fun update(item: Recording) {

    }

    override fun remove(item: Recording) {

    }

}