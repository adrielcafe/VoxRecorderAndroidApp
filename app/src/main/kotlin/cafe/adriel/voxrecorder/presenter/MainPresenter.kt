package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.repository.RecordingRepository
import cafe.adriel.voxrecorder.view.IMainView
import rx.Subscription
import java.util.*

class MainPresenter(val view: IMainView): IMainPresenter {

    private val recordingRepo = RecordingRepository()
    private val subscriptions = ArrayList<Subscription>()

    override fun load() {
        recordingRepo.initFileObserver()
        subscriptions.add(recordingRepo.get().subscribe { recordings ->
            view.updateRecordings(recordings)
        })
    }

    override fun share(recording: Recording) {

    }

    override fun edit(recording: Recording) {
        view.onRecordingEdited(recording)
    }

    override fun delete(recording: Recording) {
        view.onRecordingDeleted(recording)
    }

    override fun onDestroy() {
        subscriptions.forEach {
            if(!it.isUnsubscribed) {
                it.unsubscribe()
            }
        }
    }

}