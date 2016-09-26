package cafe.adriel.voxrecorder.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.presenter.IMainPresenter
import cafe.adriel.voxrecorder.presenter.RecordingPresenter
import cafe.adriel.voxrecorder.util.Util
import cafe.adriel.voxrecorder.util.prettyDate
import cafe.adriel.voxrecorder.util.prettyDuration
import cafe.adriel.voxrecorder.util.prettySize
import cafe.adriel.voxrecorder.view.IRecordingView
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemMenu
import co.mobiwise.library.ProgressLayoutListener
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import kotlinx.android.synthetic.main.list_item_recording.view.*
import org.zakariya.flyoutmenu.FlyoutMenuView
import java.util.*

class RecordingAdapter(val mainPresenter: IMainPresenter, val layoutManager: RecyclerView.LayoutManager):
        RecyclerView.Adapter<RecordingAdapter.ViewHolder>(), IRecordingView {

    val recordingPresenter = RecordingPresenter(this)
    val recordings = LinkedList<Recording>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_recording, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(recordings[position])
    }

    override fun getItemCount(): Int {
        return recordings.size
    }

    override fun onPlay(recording: Recording) {
        updateRecordingView(recording, {
            it.vPlayedDuration.text = "00:00:00"
            it.vPlayedDuration.visibility = View.VISIBLE
            it.vControl.text = Util.getPauseIcon()
            it.vProgress.isEnabled = true
            it.vProgress.start()
        })
    }

    override fun onPause(recording: Recording) {
        updateRecordingView(recording, {
            it.vPlayedDuration.text = "00:00:00"
            it.vPlayedDuration.visibility = View.INVISIBLE
            it.vControl.text = Util.getPlayIcon()
            it.vProgress.isEnabled = false
            it.vProgress.stop()
        })
    }

    override fun onStop(recording: Recording) {
        updateRecordingView(recording, {
            it.vPlayedDuration.visibility = View.INVISIBLE
            it.vPlayedDuration.text = "00:00:00"
            it.vControl.text = Util.getPlayIcon()
            it.vProgress.isEnabled = false
            it.vProgress.cancel()
        })
    }

    override fun onSeekTo(recording: Recording, progress: Int) {
        updateRecordingView(recording, {
            if (it.vProgress.isPlaying) {
                it.vProgress.setCurrentProgress(progress)
            }
        })
    }

    fun addRecording(recording: Recording) {
        if(!recordings.contains(recording)) {
            recordings.run {
                add(recording)
                sortWith(Comparator { r1: Recording, r2: Recording ->
                    r2.date.compareTo(r1.date).apply {
                        if(this == 0){
                            r1.name.compareTo(r2.name)
                        } else {
                            this
                        }
                    }
                })
                notifyItemInserted(indexOf(recording))
            }
        }
    }

    fun removeRecording(recording: Recording) {
        val index = recordings.indexOf(recording)
        if(recordings.remove(recording)){
            notifyItemRemoved(index)
        }
    }

    private fun updateRecordingView(recording: Recording, callback: (View) -> Unit) {
        val index = getRecordingIndex(recording)
        val holder = layoutManager?.findViewByPosition(index)?.tag as ViewHolder
        holder.itemView.run { callback(this) }
    }

    private fun getRecordingIndex(recording: Recording) =
            recordings.indexOfFirst{ it.equals(recording) }

    inner class ViewHolder(val v: View): RecyclerView.ViewHolder(v) {
        fun bind(recording: Recording){
            v.tag = this
            v.vTitle.text = recording.name
            v.vFormat.text = recording.format
            v.vDate.text = recording.date.prettyDate()
            v.vSize.text = recording.size.prettySize()
            v.vDuration.text = recording.duration.prettyDuration()
            bindMenu(recording)
            bindControl(recording)
            bindProgress(recording)
        }

        private fun bindMenu(recording: Recording) {
            val menuItems = ArrayList<RecyclerItemMenu.MenuItem>().apply {
                add(RecyclerItemMenu.MenuItem(0, GoogleMaterial.Icon.gmd_share))
                add(RecyclerItemMenu.MenuItem(1, GoogleMaterial.Icon.gmd_edit))
                add(RecyclerItemMenu.MenuItem(2, GoogleMaterial.Icon.gmd_delete))
            }
            v.vMenu.run {
                layout = FlyoutMenuView.GridLayout(3, FlyoutMenuView.GridLayout.UNSPECIFIED)
                adapter = FlyoutMenuView.ArrayAdapter(menuItems)
                buttonRenderer = RecyclerItemMenu.ButtonRenderer()
                selectionListener = object: FlyoutMenuView.SelectionListener {
                    override fun onItemSelected(flyoutMenuView: FlyoutMenuView?, item: FlyoutMenuView.MenuItem?) {
                        onMenuItemSelected(recording, item?.id ?: -1)
                        flyoutMenuView?.dismiss()
                    }
                    override fun onDismissWithoutSelection(flyoutMenuView: FlyoutMenuView?) {
                        flyoutMenuView?.dismiss()
                    }
                }
            }
        }

        private fun bindControl(recording: Recording){
            v.vControl.let {
                it.text = Util.getPlayIcon()
                it.setOnClickListener {
                    if(v.vProgress.isPlaying){
                        recordingPresenter.pause()
                    } else {
                        recordingPresenter.play(recording)
                    }
                }
                it.setOnLongClickListener {
                    recordingPresenter.stop()
                    true
                }
            }
        }

        private fun bindProgress(recording: Recording){
            v.vProgress.let {
                it.setMaxProgress(recording.duration)
                it.setOnTouchListener { v, motionEvent ->
                    val progress = (motionEvent.x * recording.duration) / v.width
                    recordingPresenter.seekTo(progress.toInt())
                    true
                }
                it.setProgressLayoutListener(object: ProgressLayoutListener {
                    override fun onProgressChanged(seconds: Int) {
                        updatePlayedTime(seconds)
                    }
                    override fun onProgressCompleted() {

                    }
                })
            }
        }

        private fun onMenuItemSelected(recording: Recording, menuId: Int) {
            when(menuId){
                0 -> mainPresenter.share(recording)
                1 -> mainPresenter.edit(recording)
                2 -> mainPresenter.delete(recording)
            }
        }

        private fun updatePlayedTime(progress: Int) {
            v.vPlayedDuration.text = progress.prettyDuration()
        }
    }

}
