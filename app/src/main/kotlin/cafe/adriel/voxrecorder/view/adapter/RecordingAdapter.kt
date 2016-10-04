package cafe.adriel.voxrecorder.view.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.DateSeparator
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.presenter.IMainPresenter
import cafe.adriel.voxrecorder.presenter.RecordingPresenter
import cafe.adriel.voxrecorder.util.prettyDate
import cafe.adriel.voxrecorder.util.prettyDuration
import cafe.adriel.voxrecorder.util.prettySize
import cafe.adriel.voxrecorder.util.string
import cafe.adriel.voxrecorder.view.IRecordingView
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemMenu
import co.mobiwise.library.ProgressLayoutListener
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import khronos.*
import kotlinx.android.synthetic.main.list_item_date_separator.view.*
import kotlinx.android.synthetic.main.list_item_recording.view.*
import org.zakariya.flyoutmenu.FlyoutMenuView
import java.util.*

class RecordingAdapter(val activity: Activity, val mainPresenter: IMainPresenter, val layoutManager: RecyclerView.LayoutManager):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), IRecordingView {

    val VIEW_TYPE_SEPARATOR = 0
    val VIEW_TYPE_RECORDING = 1

    val recordingPresenter = RecordingPresenter(this)
    val items = LinkedList<Any>()

    val iconPlay = GoogleMaterial.Icon.gmd_play_arrow.formattedName!!
    val iconPause = GoogleMaterial.Icon.gmd_pause.formattedName!!

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_SEPARATOR){
            val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_date_separator, parent, false)
            return DateSeparatorViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_recording, parent, false)
            return RecordingViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when(getItemViewType(position)){
            VIEW_TYPE_SEPARATOR ->
                (holder as DateSeparatorViewHolder).bind(items[position] as DateSeparator)
            VIEW_TYPE_RECORDING ->
                (holder as RecordingViewHolder).bind(items[position] as Recording)
        }
    }

    override fun getItemViewType(position: Int) =
            if(items[position] is DateSeparator) VIEW_TYPE_SEPARATOR else VIEW_TYPE_RECORDING

    override fun getItemCount() = items.size

    override fun onPlay(recording: Recording) {
        updateRecordingView(recording, {
            it.vPlayedDuration.text = "00:00:00"
            it.vPlayedDuration.visibility = View.VISIBLE
            it.vControl.text = iconPause
            it.vProgress.isEnabled = true
            it.vProgress.start()
        })
    }

    override fun onPause(recording: Recording) {
        updateRecordingView(recording, {
            it.vPlayedDuration.text = "00:00:00"
            it.vPlayedDuration.visibility = View.INVISIBLE
            it.vControl.text = iconPlay
            it.vProgress.isEnabled = false
            it.vProgress.stop()
        })
    }

    override fun onStop(recording: Recording) {
        updateRecordingView(recording, {
            it.vPlayedDuration.visibility = View.INVISIBLE
            it.vPlayedDuration.text = "00:00:00"
            it.vControl.text = iconPlay
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

    fun loadRecordings(recordings: List<Recording>){
        if(recordings.isNotEmpty()) {
            items.run {
                clear()
                // TODO must check if separator exists and add if needed
//                addAll(getDateSeparators())
                addAll(recordings)
                sort()
                // TODO removing separators ok
//                updateSeparators()
                notifyDataSetChanged()
            }
        }
    }

    fun addRecording(recording: Recording) {
        if(!items.contains(recording)) {
            items.run {
                add(recording)
                sort()
                notifyItemInserted(indexOf(recording))
            }
        }
    }

    fun removeRecording(recording: Recording) {
        val index = items.indexOf(recording)
        if(items.remove(recording)){
            notifyItemRemoved(index)
        }
    }

    private fun updateRecordingView(recording: Recording, callback: (View) -> Unit) {
        val index = getItemIndex(recording)
        val holder = getViewHolder(index)
        holder.itemView.run { callback(this) }
    }

    private fun sort(){
        items.sortWith(Comparator { r1: Any, r2: Any ->
            val date1 = if(r1 is DateSeparator) r1.date else (r1 as Recording).date
            val date2 = if(r2 is DateSeparator) r2.date else (r2 as Recording).date
            date2.compareTo(date1).apply {
                if(r1 is DateSeparator){
                    -1
                } else if(r2 is DateSeparator){
                    1
                } else if(this == 0){
                    (r1 as Recording).name.toLowerCase().compareTo((r2 as Recording).name.toLowerCase())
                } else {
                    this
                }
            }
        })
    }

    private fun getViewHolder(index: Int) = layoutManager?.findViewByPosition(index)?.tag as RecyclerView.ViewHolder

    private fun getItemIndex(item: Any) =
            items.indexOfFirst{ it.equals(item) }

    private fun getDateSeparators(): List<DateSeparator> {
        val today = Dates.today.with(hour = 23, minute = 59, second = 59)
        val week = today - 1.day
        val month = today - 1.week
        val year = today - 1.year
        val oldest = today - 2.years
        return listOf(
                DateSeparator(R.string.today, today),
                DateSeparator(R.string.this_week, week),
                DateSeparator(R.string.this_month, month),
                DateSeparator(R.string.this_year, year),
                DateSeparator(R.string.oldest, oldest)
        )
    }

    private fun updateSeparators(){
        if(items.isNotEmpty()) {
            var it = items.listIterator()
            while(it.hasNext()){
                val i = it.nextIndex()
                val item = it.next()
                if(item is DateSeparator) {
                    if(items.lastIndex == i){
                        it.remove()
                        notifyItemRemoved(i)
                    } else if(items[i + 1] is DateSeparator){
                        it.remove()
                        notifyItemRemoved(i)
                    }
                }
            }
        }
    }

    inner class DateSeparatorViewHolder(val v: View): RecyclerView.ViewHolder(v) {
        fun bind(dateSeparator: DateSeparator){
            v.tag = this
            v.vDateSeparator.text = string(dateSeparator.titleResId)
        }
    }

    inner class RecordingViewHolder(val v: View): RecyclerView.ViewHolder(v) {
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
                it.text = iconPlay
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
                0 -> mainPresenter.share(activity, recording)
                1 -> mainPresenter.showRenameDialog(recording)
                2 -> mainPresenter.showDeleteDialog(recording)
            }
        }

        private fun updatePlayedTime(progress: Int) {
            v.vPlayedDuration.text = progress.prettyDuration()
        }
    }

}
