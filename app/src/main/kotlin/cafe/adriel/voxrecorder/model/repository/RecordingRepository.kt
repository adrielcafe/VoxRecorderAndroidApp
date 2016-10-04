package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.RecordingAddedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingDeletedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingErrorEvent
import cafe.adriel.voxrecorder.util.Util
import com.eightbitlab.rxbus.Bus
import rx.Observable
import java.io.File

class RecordingRepository: IRepository<Recording> {

    override fun get(): Observable<List<Recording>> = Observable.defer {
        Observable.from(Constant.RECORDING_FOLDER.listFiles(){ file ->
            Util.isSupportedFormat(file.name)
        })
        .map { Recording(it.path) }
        .toList()
    }

    override fun convert(item: Recording) {

    }

    override fun rename(item: Recording, newName: String) {
        val newFile = File(item.file.parentFile, "$newName.${item.format}")
        if(!item.file.path.equals(newFile.path)) {
            if (item.file.renameTo(newFile)) {
                Bus.send(RecordingDeletedEvent(item))
                Bus.send(RecordingAddedEvent(Recording(newFile.path)))
            } else {
                Bus.send(RecordingErrorEvent(R.string.unable_rename_file))
            }
        }
    }

    override fun delete(item: Recording) {
        if(item.file.delete()){
            Bus.send(RecordingDeletedEvent(item))
        } else {
            Bus.send(RecordingErrorEvent(R.string.unable_delete_file))
        }
    }

}