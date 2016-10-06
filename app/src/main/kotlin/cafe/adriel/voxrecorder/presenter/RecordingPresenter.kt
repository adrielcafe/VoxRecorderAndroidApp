package cafe.adriel.voxrecorder.presenter

import android.media.MediaPlayer
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.util.AnalyticsUtil
import cafe.adriel.voxrecorder.util.orFalse
import cafe.adriel.voxrecorder.view.IRecordingView
import com.pawegio.kandroid.e

class RecordingPresenter(val view: IRecordingView): IRecordingPresenter {

    private var player: MediaPlayer? = null
    private var currentRecording: Recording? = null
    private var isPaused = false

    override fun play(recording: Recording) {
        if(!recording.equals(currentRecording)) {
            stop()
            AnalyticsUtil.viewRecordingEvent(recording)
        }
        currentRecording = recording
        currentRecording?.let {
            try {
                if(isPaused){
                    player?.start()
                } else {
                    player = MediaPlayer().apply {
                        setOnSeekCompleteListener {
                            this@RecordingPresenter.stop()
                        }
                        setDataSource(it.filePath)
                        prepare()
                        start()
                    }
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
                if (player?.isPlaying.orFalse()) {
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
                e { "${player?.currentPosition} $progress" }
                if (player?.isPlaying.orFalse()) {
                    player?.seekTo(progress)
                    view.onSeekTo(it, progress)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onPause() {
        pause()
    }

    override fun onDestroy() {
        try {
            currentRecording?.let {
                view.onStop(it)
            }
            isPaused = false
            player = null
            currentRecording = null
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}