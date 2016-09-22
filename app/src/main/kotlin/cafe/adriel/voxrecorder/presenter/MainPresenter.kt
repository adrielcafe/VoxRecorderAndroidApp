package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.repository.RecordingRepository
import cafe.adriel.voxrecorder.view.IMainView

class MainPresenter(val view: IMainView): IMainPresenter {

    private val recordingRepo = RecordingRepository()

    override fun load() {
        recordingRepo.initFileObserver()
        recordingRepo.get({ view.updateRecordings(it) })
    }

    override fun share(recording: Recording) {

    }

    override fun edit(recording: Recording) {
        view.onRecordingEdited(recording)
    }

    override fun delete(recording: Recording) {
        view.onRecordingDeleted(recording)
    }

}