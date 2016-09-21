package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.entity.Recording

interface IRecordingView {
    fun onMenuItemSelected(recording: Recording, menuId: Int)
    fun onPlay()
    fun onPause()
    fun onStop()
    fun onSetPlayTime(playTime: Int)
    fun onUpdatePlayedTime(playedTime: Int)
}