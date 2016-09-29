package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording

interface IRecordingView {
    fun onPlay(recording: Recording)
    fun onPause(recording: Recording)
    fun onStop(recording: Recording)
    fun onSeekTo(recording: Recording, progress: Int)
}