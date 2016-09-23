package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.repository.RecordingRepository
import cafe.adriel.voxrecorder.view.IMainView
import rx.Subscription
import java.util.*

class MainPresenter(val view: IMainView): IMainPresenter {

    private val recordingRepo = RecordingRepository()

    private var subscription: Subscription? = null

    override fun load() {
        unsubscribe()
        recordingRepo.initFileObserver()
        subscription = recordingRepo.get().subscribe ({ recordings ->
            view.updateRecordings(recordings)
        }, Throwable::printStackTrace)
    }

    override fun share(recording: Recording) {

    }

    override fun edit(recording: Recording) {
        view.onRecordingEdited(recording)
    }

    override fun delete(recording: Recording) {
        view.onRecordingDeleted(recording)
    }

    override fun unsubscribe() {
        recordingRepo.unsubscribe()
        if(subscription?.isUnsubscribed == false) {
            subscription?.unsubscribe()
        }
    }

}