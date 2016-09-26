package cafe.adriel.voxrecorder.view.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.RecordingAddedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingDeletedEvent
import cafe.adriel.voxrecorder.model.entity.RecordingUpdatedEvent
import cafe.adriel.voxrecorder.presenter.MainPresenter
import cafe.adriel.voxrecorder.view.IMainView
import cafe.adriel.voxrecorder.view.adapter.RecordingAdapter
import cafe.adriel.voxrecorder.view.ui.base.BaseFragment
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemDecoration
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.list_item_recording.view.*

class MainFragment: BaseFragment(), IMainView {

    private val presenter = MainPresenter(this)

    private lateinit var adapter: RecordingAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        view.vRecordings.let {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = RecordingAdapter(presenter, layoutManager!!)
            it.addItemDecoration(RecyclerItemDecoration())
            it.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                        adapter?.recordings?.forEachIndexed { i, r ->
                            layoutManager?.findViewByPosition(i)?.vMenu?.dismiss()
                        }
                    }
                }
            })
            it.adapter = adapter
            it.layoutManager = layoutManager
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.load()

        Bus.observe<RecordingAddedEvent>()
                .subscribe { onRecordingAdded(it.recording) }
                .registerInBus(this)
        Bus.observe<RecordingUpdatedEvent>()
                .subscribe { onRecordingUpdated(it.recording) }
                .registerInBus(this)
        Bus.observe<RecordingDeletedEvent>()
                .subscribe { onRecordingDeleted(it.recording) }
                .registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
        adapter.recordingPresenter.onDestroy()
    }

    override fun onRecordingAdded(recording: Recording) {
        adapter.addRecording(recording)
    }

    // TODO
    override fun onRecordingUpdated(recording: Recording) {
//        adapter.addRecording(recording)
    }

    override fun onRecordingDeleted(recording: Recording) {
        adapter.removeRecording(recording)
    }

}