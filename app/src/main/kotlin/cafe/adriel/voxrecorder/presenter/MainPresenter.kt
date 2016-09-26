package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.repository.RecordingRepository
import cafe.adriel.voxrecorder.view.IMainView
import rx.subscriptions.CompositeSubscription

class MainPresenter(val view: IMainView): IMainPresenter {

    private val recordingRepo = RecordingRepository()
    private val subscriptions = CompositeSubscription()

    override fun load() {
        unsubscribe()
        subscriptions.add(recordingRepo.get()
                .subscribe ({ view.onRecordingAdded(it) }, Throwable::printStackTrace))
    }

    override fun share(recording: Recording) {

    }

    override fun edit(recording: Recording) {
        view.onRecordingUpdated(recording)
    }

    override fun delete(recording: Recording) {
        view.onRecordingDeleted(recording)
    }

    override fun unsubscribe() {
        subscriptions.unsubscribe()
        subscriptions.clear()
    }

}