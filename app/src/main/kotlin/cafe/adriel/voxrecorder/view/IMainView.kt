package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.Recording

interface IMainView {
    fun updateRecordings(recordings: List<Recording>)
    fun onRecordingEdited(recording: Recording)
    fun onRecordingDeleted(recording: Recording)
    fun onPlay(recording: Recording)
    fun onPause(recording: Recording)
    fun onStop(recording: Recording)
    fun onSetPlayTime(recording: Recording, playTime: Int)
    fun onUpdatePlayedTime(recording: Recording, playedTime: Int)
}