package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainView {
    fun updateRecordings(recordings: List<Recording>)
    fun onRecordingEdited(recording: Recording)
    fun onRecordingDeleted(recording: Recording)
}