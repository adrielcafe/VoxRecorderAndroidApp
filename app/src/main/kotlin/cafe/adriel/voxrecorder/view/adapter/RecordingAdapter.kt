package cafe.adriel.voxrecorder.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.presenter.IMainPresenter
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

class RecordingAdapter(val presenter: IMainPresenter): RecyclerView.Adapter<RecordingAdapter.ViewHolder>() {

    val recordings = ArrayList<Recording>()

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

    fun updateRecordings(newRecordings: List<Recording>) {
        recordings.apply {
            clear()
            addAll(newRecordings)
            notifyItemRangeChanged(0, size - 1)
        }
    }

    fun onRecordingEdited(recording: Recording) {

    }

    fun onRecordingDeleted(recording: Recording) {

    }

    inner class ViewHolder(val v: View): RecyclerView.ViewHolder(v), IRecordingView {
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
            v.vMenu.let {
                it.layout = FlyoutMenuView.GridLayout(3, FlyoutMenuView.GridLayout.UNSPECIFIED)
                it.adapter = FlyoutMenuView.ArrayAdapter(menuItems)
                it.buttonRenderer = RecyclerItemMenu.ButtonRenderer()
                it.selectionListener = object: FlyoutMenuView.SelectionListener {
                    override fun onItemSelected(flyoutMenuView: FlyoutMenuView?, item: FlyoutMenuView.MenuItem?) {
                        onMenuItemSelected(recording, item?.id ?: -1)
                    }
                    override fun onDismissWithoutSelection(flyoutMenuView: FlyoutMenuView?) {

                    }
                }
            }
        }

        private fun bindControl(recording: Recording){
            v.vControl.let {
                it.text = Util.getPlayIcon()
                it.setOnClickListener {
                    if(v.vProgress.isPlaying){
                        presenter.pause(recording)
                    } else {
                        presenter.play(recording)
                    }
                }
                it.setOnLongClickListener {
                    presenter.stop(recording)
                    true
                }
            }
        }

        private fun bindProgress(recording: Recording){
            v.vProgress.let {
                it.setMaxProgress(recording.duration)
                it.setOnTouchListener { v, motionEvent ->
                    val playTime = (motionEvent.x * recording.duration) / v.width
                    presenter.setPlayTime(recording, playTime.toInt())
                    true
                }
                it.setProgressLayoutListener(object: ProgressLayoutListener {
                    override fun onProgressChanged(seconds: Int) {
                        presenter.updatePlayedTime(recording, seconds)
                    }
                    override fun onProgressCompleted() {
                        presenter.stop(recording)
                    }
                })
            }
        }

        override fun onMenuItemSelected(recording: Recording, menuId: Int) {
            when(menuId){
                0 -> presenter.share(recording)
                1 -> presenter.edit(recording)
                2 -> presenter.delete(recording)
            }
        }

        override fun onPlay() {
            v.vPlayedDuration.text = "00:00:00"
            v.vPlayedDuration.visibility = View.VISIBLE
            v.vControl.text = Util.getPauseIcon()
            v.vProgress.start()
        }

        override fun onPause() {
            v.vPlayedDuration.text = "00:00:00"
            v.vPlayedDuration.visibility = View.INVISIBLE
            v.vControl.text = Util.getPlayIcon()
            v.vProgress.stop()
        }

        override fun onStop() {
            v.vPlayedDuration.visibility = View.INVISIBLE
            v.vPlayedDuration.text = "00:00:00"
            v.vControl.text = Util.getPlayIcon()
            v.vProgress.cancel()
        }

        override fun onSetPlayTime(playTime: Int) {
            if(v.vProgress.isPlaying) {
                v.vProgress.setCurrentProgress(playTime)
            }
        }

        override fun onUpdatePlayedTime(playedTime: Int) {
            v.vPlayedDuration.text = playedTime.prettyDuration()
        }
    }

}