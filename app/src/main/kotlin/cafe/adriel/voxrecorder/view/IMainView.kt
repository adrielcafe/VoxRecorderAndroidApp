package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainView {
    fun onRecordingAdded(recording: Recording)
    fun onRecordingDeleted(recording: Recording)
    fun showRenameDialog(recording: Recording)
    fun showDeleteDialog(recording: Recording)
    fun showError(resId: Int)
}