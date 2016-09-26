package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainView {
    fun onRecordingAdded(recording: Recording)
    fun onRecordingUpdated(recording: Recording)
    fun onRecordingDeleted(recording: Recording)
}