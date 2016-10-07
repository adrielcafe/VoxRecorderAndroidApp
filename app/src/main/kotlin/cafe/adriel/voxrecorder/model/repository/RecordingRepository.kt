package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.androidaudioconverter.AndroidAudioConverter
import cafe.adriel.androidaudioconverter.callback.IConvertCallback
import cafe.adriel.androidaudioconverter.model.AudioFormat
import cafe.adriel.voxrecorder.App
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.RecordingAddedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingDeletedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingErrorEvent
import cafe.adriel.voxrecorder.util.AnalyticsUtil
import cafe.adriel.voxrecorder.util.Util
import cafe.adriel.voxrecorder.util.pref
import com.eightbitlab.rxbus.Bus
import rx.Observable
import java.io.File

class RecordingRepository: IRepository<Recording> {

    override fun get(): Observable<List<Recording>> = Observable.defer {
        Observable.from(Constant.RECORDING_FOLDER.listFiles(){ file ->
            Util.isSupportedFormat(file.name)
        })
        .map { Recording(it.path) }
        .filter { it.playable }
        .toList()
    }

    override fun save(item: Recording) {
        val format = pref().getString(Constant.PREF_RECORDING_FORMAT, AudioFormat.WAV.format)
        val newFile = File(Constant.RECORDING_FOLDER, item.nameWithFormat)
        if (item.file.renameTo(newFile)) {
            if(!item.format.equals(format)) {
                AndroidAudioConverter.with(App.instance)
                        .setFile(newFile)
                        .setFormat(AudioFormat.valueOf(format))
                        .setCallback(object: IConvertCallback {
                            override fun onSuccess(convertedFile: File?) {
                                if(convertedFile != null) {
                                    val newRecording = Recording(convertedFile.path)
                                    AnalyticsUtil.newRecordingEvent(newRecording)
                                    Bus.send(RecordingAddedEvent(newRecording))
                                }
                                newFile.delete()
                                item.file.delete()
                            }
                            override fun onFailure(error: Exception?) {
                                error?.printStackTrace()
                                Bus.send(RecordingErrorEvent(R.string.unable_save_file))
                            }
                        })
                        .convert()
            } else {
                val newRecording = Recording(newFile.path)
                AnalyticsUtil.newRecordingEvent(newRecording)
                Bus.send(RecordingAddedEvent(newRecording))
            }
        } else {
            Bus.send(RecordingErrorEvent(R.string.unable_save_file))
        }

    }

    override fun rename(item: Recording, newName: String) {
        val newFile = File(item.file.parentFile, "$newName.${item.format}")
        if(item.file.path.equals(newFile.path)) {
            Bus.send(RecordingErrorEvent(R.string.file_already_exists))
        } else {
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
            AnalyticsUtil.deleteRecordingEvent(item)
        } else {
            Bus.send(RecordingErrorEvent(R.string.unable_delete_file))
        }
    }

}