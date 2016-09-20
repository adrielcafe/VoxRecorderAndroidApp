package cafe.adriel.voxrecorder.view.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.Recording
import cafe.adriel.voxrecorder.presenter.MainPresenter
import cafe.adriel.voxrecorder.view.IMainView
import cafe.adriel.voxrecorder.view.adapter.RecordingAdapter
import cafe.adriel.voxrecorder.view.ui.base.BaseFragment
import cafe.adriel.voxrecorder.view.ui.widget.RecyclerItemDecoration
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment: BaseFragment(), IMainView {

    val presenter = MainPresenter(this)
    val adapter = RecordingAdapter(presenter)
    var layoutManager: LinearLayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main, container, false)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        view?.vRecordings?.setHasFixedSize(true)
        view?.vRecordings?.addItemDecoration(RecyclerItemDecoration())
        view?.vRecordings?.adapter = adapter
        view?.vRecordings?.layoutManager = layoutManager
        return view ?: super.onCreateView(inflater, container, savedInstanceState)
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

    override fun onPlay(recording: Recording) {
        getRecordingViewHolder(recording).onPlay()
    }

    override fun onPause(recording: Recording) {
        getRecordingViewHolder(recording).onPause()
    }

    override fun onStop(recording: Recording) {
        getRecordingViewHolder(recording).onStop()
    }

    override fun onSetPlayTime(recording: Recording, playTime: Int) {
        getRecordingViewHolder(recording).onSetPlayTime(playTime)
    }

    override fun onUpdatePlayedTime(recording: Recording, playedTime: Int) {
        getRecordingViewHolder(recording).onUpdatePlayedTime(playedTime)
    }

    fun init(){
        presenter.loadRecordings()
    }

    fun getRecordingViewHolder(recording: Recording): RecordingAdapter.ViewHolder {
        val index = getRecordingIndex(recording)
        return layoutManager?.findViewByPosition(index)?.tag as RecordingAdapter.ViewHolder
    }

    fun getRecordingIndex(recording: Recording) =
            adapter.recordings.indexOfFirst{ it.equals(recording) }

}