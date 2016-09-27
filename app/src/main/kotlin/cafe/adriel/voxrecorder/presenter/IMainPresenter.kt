package cafe.adriel.voxrecorder.presenter

import android.app.Activity
import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainPresenter {
    fun load()
    fun showRenameDialog(recording: Recording)
    fun showDeleteDialog(recording: Recording)
    fun rename(recording: Recording, newName: String)
    fun delete(recording: Recording)
    fun share(activity: Activity, recording: Recording)
    fun isValidFileName(fileName: String): Boolean
    fun unsubscribe()
}