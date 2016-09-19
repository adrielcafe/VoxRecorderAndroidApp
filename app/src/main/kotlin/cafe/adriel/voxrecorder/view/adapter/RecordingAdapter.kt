package cafe.adriel.voxrecorder.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.Recording
import cafe.adriel.voxrecorder.util.prettyDate
import cafe.adriel.voxrecorder.util.prettyDuration
import cafe.adriel.voxrecorder.util.prettySize
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemMenu
import co.mobiwise.library.ProgressLayoutListener
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import kotlinx.android.synthetic.main.list_item_recording.view.*
import org.zakariya.flyoutmenu.FlyoutMenuView
import java.util.*

class RecordingAdapter(): RecyclerView.Adapter<RecordingAdapter.ViewHolder>() {
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
        recordings.clear()
        recordings.addAll(newRecordings)
        notifyItemRangeChanged(0, recordings.size - 1)
    }

    class ViewHolder(val v: View): RecyclerView.ViewHolder(v){
        fun bind(recording: Recording){
            v.vTitle.text = recording.name
            v.vFormat.text = recording.format
            v.vDate.text = recording.date.prettyDate()
            v.vSize.text = recording.size.prettySize()
            v.vDuration.text = recording.duration.prettyDuration()
            bindMenu(recording)
            bindControlAndProgress(recording)
        }

        private fun bindMenu(recording: Recording) {
            val menuItems = ArrayList<RecyclerItemMenu.MenuItem>()
            menuItems.add(RecyclerItemMenu.MenuItem(0, GoogleMaterial.Icon.gmd_share))
            menuItems.add(RecyclerItemMenu.MenuItem(1, GoogleMaterial.Icon.gmd_edit))
            menuItems.add(RecyclerItemMenu.MenuItem(2, GoogleMaterial.Icon.gmd_delete))
            v.vMenu.layout = FlyoutMenuView.GridLayout(3, FlyoutMenuView.GridLayout.UNSPECIFIED)
            v.vMenu.adapter = FlyoutMenuView.ArrayAdapter(menuItems)
            v.vMenu.buttonRenderer = RecyclerItemMenu.ButtonRenderer()
            v.vMenu.selectionListener = object: FlyoutMenuView.SelectionListener {
                override fun onItemSelected(flyoutMenuView: FlyoutMenuView?, item: FlyoutMenuView.MenuItem?) {
                    when(item?.id){
                        0 -> shareRecording(recording)
                        1 -> editRecording(recording)
                        2 -> deleteRecording(recording)
                    }
                }
                override fun onDismissWithoutSelection(flyoutMenuView: FlyoutMenuView?) {

                }
            }
        }

        private fun bindControlAndProgress(recording: Recording){
            v.vControl.text = getPlayIcon()
            v.vControl.setOnClickListener {
                v.vPlayedDuration.text = "00:00:00"
                if (v.vProgress.isPlaying){
                    v.vPlayedDuration.visibility = View.INVISIBLE
                    v.vControl.text = getPlayIcon()
                    v.vProgress.stop()
                } else {
                    v.vPlayedDuration.visibility = View.VISIBLE
                    v.vControl.text = getPauseIcon()
                    v.vProgress.start()
                }
            }
            v.vControl.setOnLongClickListener {
                v.vPlayedDuration.visibility = View.INVISIBLE
                v.vPlayedDuration.text = "00:00:00"
                v.vControl.text = getPlayIcon()
                v.vProgress.cancel()
                true
            }

            v.vProgress.setMaxProgress(recording.duration)
            v.vProgress.setOnTouchListener { v, motionEvent ->
                if(this.v.vProgress.isPlaying) {
                    val currentDuration = (motionEvent.x * recording.duration) / v.width
                    this.v.vProgress.setCurrentProgress(currentDuration.toInt())
                    true
                } else {
                    false
                }
            }
            v.vProgress.setProgressLayoutListener(object: ProgressLayoutListener {
                override fun onProgressChanged(seconds: Int) {
                    v.vPlayedDuration.text = seconds.prettyDuration()
                }
                override fun onProgressCompleted() {
                    v.vPlayedDuration.visibility = View.INVISIBLE
                    v.vPlayedDuration.text = "00:00:00"
                    v.vControl.text = getPlayIcon()
                }
            })
        }

        private fun shareRecording(recording: Recording) {
            // TODO
        }

        private fun editRecording(recording: Recording) {
            // TODO
        }

        private fun deleteRecording(recording: Recording) {
            // TODO
        }

        private fun getPlayIcon() = GoogleMaterial.Icon.gmd_play_arrow.formattedName

        private fun getPauseIcon() = GoogleMaterial.Icon.gmd_pause.formattedName
    }

}