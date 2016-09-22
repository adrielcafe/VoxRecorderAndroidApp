package cafe.adriel.voxrecorder.view.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.FileChangedEvent
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.presenter.MainPresenter
import cafe.adriel.voxrecorder.view.IMainView
import cafe.adriel.voxrecorder.view.adapter.RecordingAdapter
import cafe.adriel.voxrecorder.view.ui.base.BaseFragment
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemDecoration
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.github.phajduk.rxfileobserver.FileEvent
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment: BaseFragment(), IMainView {

    private val presenter = MainPresenter(this)

    private lateinit var adapter: RecordingAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        view.vRecordings.let {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = RecordingAdapter(presenter, layoutManager!!)
            it.setHasFixedSize(true)
            it.addItemDecoration(RecyclerItemDecoration())
            it.adapter = adapter
            it.layoutManager = layoutManager
        }

        Bus.observe<FileChangedEvent>()
                .subscribe { onFileChanged(it.fileEvent) }
                .registerInBus(this)

        presenter.load()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.recordingPresenter.onDestroy()
    }

    override fun onFileChanged(fileEvent: FileEvent) {

    }

    override fun updateRecordings(recordings: List<Recording>) {
        adapter.updateRecordings(recordings)
    }

    override fun onRecordingEdited(recording: Recording) {
        adapter.onRecordingEdited(recording)
    }

    override fun onRecordingDeleted(recording: Recording) {
        adapter.onRecordingDeleted(recording)
    }

}