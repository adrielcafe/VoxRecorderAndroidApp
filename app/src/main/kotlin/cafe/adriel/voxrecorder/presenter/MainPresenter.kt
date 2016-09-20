package cafe.adriel.voxrecorder.presenter

import android.os.Environment
import cafe.adriel.voxrecorder.model.Recording
import cafe.adriel.voxrecorder.view.IMainView
import java.util.*

class MainPresenter(val view: IMainView): IMainPresenter {

    override fun loadRecordings() {
        val recordings = ArrayList<Recording>()
        recordings.add(Recording(Environment.getExternalStorageDirectory().path + "/recorded_audio.wav"))
        recordings.add(Recording(Environment.getExternalStorageDirectory().path + "/audio.mp3"))
        recordings.sortByDescending { it.date }
        view.updateRecordings(recordings)
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