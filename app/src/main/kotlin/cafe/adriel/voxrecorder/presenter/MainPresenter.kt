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

    override fun onPlayRecording() {

    }


}