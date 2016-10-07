package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainView {
    fun onLoadRecordings(recordings: List<Recording>)
    fun onRecordingAdded(recording: Recording)
    fun onRecordingDeleted(recording: Recording)
    fun showRenameDialog(recording: Recording)
    fun showDeleteDialog(recording: Recording)
    fun showError(resId: Int)
    fun updateState()
}