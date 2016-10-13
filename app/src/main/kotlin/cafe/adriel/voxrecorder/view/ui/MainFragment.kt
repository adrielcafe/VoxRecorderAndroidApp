package cafe.adriel.voxrecorder.view.ui

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.*
import cafe.adriel.voxrecorder.presenter.MainPresenter
import cafe.adriel.voxrecorder.util.string
import cafe.adriel.voxrecorder.view.IMainView
import cafe.adriel.voxrecorder.view.adapter.RecordingAdapter
import cafe.adriel.voxrecorder.view.ui.base.BaseFragment
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemDecoration
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.pawegio.kandroid.find
import com.pawegio.kandroid.inflateLayout
import com.pawegio.kandroid.toast
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.list_item_recording.view.*

class MainFragment: BaseFragment(), IMainView {

    val presenter = MainPresenter(this)

    private lateinit var adapter: RecordingAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        view.vRecordings.let {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = RecordingAdapter(activity, presenter, layoutManager)
            it.addItemDecoration(RecyclerItemDecoration())
            it.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                        adapter.items.forEachIndexed { i, r ->
                            layoutManager.findViewByPosition(i)?.vMenu?.dismiss()
                        }
                    }
                }
            })
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
        view.vState.let {
            it.setEmptyResource(R.layout.state_empty)
            it.setLoadingResource(R.layout.state_loading)
            it.showLoading()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Bus.observe<LoadRecordingsEvent>()
                .subscribe { presenter.load() }
                .registerInBus(this)
        Bus.observe<SaveRecordingEvent>()
                .subscribe { presenter.save(it.recording) }
                .registerInBus(this)
        Bus.observe<RecordingAddedEvent>()
                .subscribe { onRecordingAdded(it.recording) }
                .registerInBus(this)
        Bus.observe<RecordingDeletedEvent>()
                .subscribe { onRecordingDeleted(it.recording) }
                .registerInBus(this)
        Bus.observe<RecordingErrorEvent>()
                .subscribe { showError(it.errorResId) }
                .registerInBus(this)
    }

    override fun onStart() {
        super.onStart()
        // TODO Sync files
    }

    override fun onPause() {
        super.onPause()
        adapter.recordingPresenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.recordingPresenter.onDestroy()
        presenter.unsubscribe()
    }

    override fun onLoadRecordings(recordings: List<Recording>) {
        adapter.loadRecordings(recordings)
        updateState()
    }

    override fun onRecordingAdded(recording: Recording) {
        adapter.addRecording(recording)
        updateState()
    }

    override fun onRecordingDeleted(recording: Recording) {
        adapter.removeRecording(recording)
        updateState()
    }

    override fun showRenameDialog(recording: Recording) {
        val vRenameLayout = activity.inflateLayout(R.layout.dialog_rename_recording)
        val vNewName = vRenameLayout.find<EditText>(R.id.vNewName)
        vNewName.setText(recording.name)
        AlertDialog.Builder(context)
                .setTitle(R.string.rename_recording)
                .setView(vRenameLayout)
                .setNegativeButton(R.string.cancel, { di, i -> })
                .setPositiveButton(R.string.rename, { di, i ->
                    val newName = vNewName.text.toString()
                    if(presenter.isValidFileName(newName)) {
                        presenter.rename(recording, newName)
                    } else {
                        toast(string(R.string.invalid_filename))
                    }
                })
                .show()
    }

    override fun showDeleteDialog(recording: Recording) {
        AlertDialog.Builder(context)
                .setTitle(R.string.delete_recording)
                .setMessage(string(R.string.confirm_deletion_recording, recording.nameWithFormat))
                .setNegativeButton(R.string.cancel, { di, i -> })
                .setPositiveButton(R.string.delete, { di, i ->
                    presenter.delete(recording)
                })
                .show()
    }

    override fun showError(resId: Int) {
        toast(string(resId))
    }

    override fun updateState() {
        adapter.items.let {
            if(it.isNotEmpty()){
                vState.showContent()
            } else {
                vState.showEmpty()
            }
        }
    }

}