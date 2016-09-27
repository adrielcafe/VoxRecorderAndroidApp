package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording

interface IRecordingPresenter {
    fun play(recording: Recording)
    fun pause()
    fun stop()
    fun seekTo(progress: Int)
    fun onDestroy()
}