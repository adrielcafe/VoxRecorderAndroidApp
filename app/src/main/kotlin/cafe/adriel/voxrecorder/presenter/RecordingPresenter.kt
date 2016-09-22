package cafe.adriel.voxrecorder.presenter

import android.media.MediaPlayer
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.view.IRecordingView
import com.pawegio.kandroid.e

class RecordingPresenter(val view: IRecordingView): IRecordingPresenter {

    private var player: MediaPlayer? = null
    private var currentRecording: Recording? = null
    private var isPaused = false

    override fun onDestroy() {
        try {
            player?.apply {
                reset()
                release()
            }
            player = null
            currentRecording = null
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun play(recording: Recording) {
        currentRecording?.let {
            stop()
        }
        currentRecording = recording
        currentRecording?.let {
            try {
                player = MediaPlayer().apply {
                    setOnSeekCompleteListener {
                        this@RecordingPresenter.stop()
                    }
                    setDataSource(it.filePath)
                    prepare()
                    start()
                }
                isPaused = false
                view.onPlay(it)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun pause() {
        currentRecording?.let {
            try {
                if (player?.isPlaying as Boolean) {
                    player?.pause()
                    isPaused = true
                    view.onPause(it)
                }
            } catch (e: Exception){
                isPaused = false
                e.printStackTrace()
            }
        }
    }

    override fun stop() {
        currentRecording?.let {
            try {
                player?.release()
                isPaused = false
                view.onStop(it)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun seekTo(progress: Int) {
        currentRecording?.let {
            try {
                e { "${player?.isPlaying}" }
                if (player?.isPlaying as Boolean) {
                    player?.seekTo(progress)
                    view.onSeekTo(it, progress)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}