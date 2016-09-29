package cafe.adriel.voxrecorder.presenter

import android.app.Activity
import android.net.Uri
import android.support.v4.app.ShareCompat
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.DateSeparator
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.repository.RecordingRepository
import cafe.adriel.voxrecorder.util.string
import cafe.adriel.voxrecorder.view.IMainView
import khronos.day
import khronos.days
import khronos.minus
import rx.Subscription
import java.util.*

class MainPresenter(val view: IMainView): IMainPresenter {

    private val recordingRepo = RecordingRepository()
    
    private var subscription: Subscription? = null

    override fun load() {
        unsubscribe()
        subscription = recordingRepo.get()
                .subscribe ({ view.onRecordingAdded(it) }, Throwable::printStackTrace)
    }

    override fun showRenameDialog(recording: Recording) {
        view.showRenameDialog(recording)
    }

    override fun showDeleteDialog(recording: Recording) {
        view.showDeleteDialog(recording)
    }

    override fun rename(recording: Recording, newName: String) {
        recordingRepo.rename(recording, newName)
    }

    override fun delete(recording: Recording) {
        recordingRepo.delete(recording)
    }

    override fun share(activity: Activity, recording: Recording) {
        val text = string(R.string.this_recording_was_created_using_vox, Constant.GOOGLE_PLAY_URL)
        ShareCompat.IntentBuilder.from(activity)
                .setText(text)
                .setStream(Uri.fromFile(recording.file))
                .setType(Constant.MIME_TYPE_AUDIO)
                .startChooser()
    }

    override fun isValidFileName(fileName: String) = fileName.isNotEmpty()

    override fun getDateSeparators(): List<DateSeparator> {
        val today = Calendar.getInstance().let {
            it.set(2016, 8, 27, 23, 59, 59)
            it.time
        }
        val week = today - 1.day
        val month = today - 7.days
        val oldest = today - 30.days
        return arrayListOf(
                DateSeparator(R.string.today, today),
                DateSeparator(R.string.this_week, week),
                DateSeparator(R.string.this_month, month),
                DateSeparator(R.string.oldest, oldest)
        )
    }

    override fun unsubscribe() {
        if(subscription != null && !subscription!!.isUnsubscribed) {
            subscription?.unsubscribe()
        }
    }

}