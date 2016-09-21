package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.repository.RecordingRepository
import cafe.adriel.voxrecorder.view.IMainView

class MainPresenter(val view: IMainView): IMainPresenter {

    val recordingRepo = RecordingRepository()

    override fun load() {
        recordingRepo.get({ recordings ->
            view.updateRecordings(recordings)
        })
    }

    override fun play(recording: Recording) {
        view.onPlay(recording)
    }

    override fun pause(recording: Recording) {
        view.onPause(recording)
    }

    override fun stop(recording: Recording) {
        view.onStop(recording)
    }

    override fun setPlayTime(recording: Recording, playTime: Int) {
        view.onSetPlayTime(recording, playTime)
    }

    override fun updatePlayedTime(recording: Recording, playedTime: Int) {
        view.onUpdatePlayedTime(recording, playedTime)
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