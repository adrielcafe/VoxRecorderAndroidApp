package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainPresenter {
    fun load()
    fun play(recording: Recording)
    fun pause(recording: Recording)
    fun stop(recording: Recording)
    fun setPlayTime(recording: Recording, playTime: Int)
    fun updatePlayedTime(recording: Recording, playedTime: Int)
    fun share(recording: Recording)
    fun edit(recording: Recording)
    fun delete(recording: Recording)
}