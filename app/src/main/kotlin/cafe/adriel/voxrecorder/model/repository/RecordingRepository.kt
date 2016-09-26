package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.RecordingDeletedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingUpdatedEvent
import cafe.adriel.voxrecorder.util.Util
import com.eightbitlab.rxbus.Bus
import rx.Observable
import java.io.File

class RecordingRepository: IRepository<Recording> {

    override fun get(): Observable<Recording> = Observable.defer {
        Observable.from(Constant.RECORDING_FOLDER.listFiles(){ file ->
            Util.isSupportedFormat(file.name)
        })
        .map { Recording(it.path) }
    }

    override fun convert(item: Recording) {

    }

    override fun rename(item: Recording, newName: String) {
        val newFile = File(item.file.parentFile, newName)
        if(item.file.renameTo(newFile)){
            Bus.send(RecordingUpdatedEvent(item))
        }
    }

    override fun delete(item: Recording) {
        if(item.file.delete()){
            Bus.send(RecordingDeletedEvent(item))
        }
    }

}