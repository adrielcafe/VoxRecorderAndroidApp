package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording
import com.github.phajduk.rxfileobserver.FileEvent

interface IMainView {
    fun onFileChanged(fileEvent: FileEvent)
    fun updateRecordings(recordings: List<Recording>)
    fun onRecordingEdited(recording: Recording)
    fun onRecordingDeleted(recording: Recording)
}