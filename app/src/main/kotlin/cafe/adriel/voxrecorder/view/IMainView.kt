package cafe.adriel.voxrecorder.view

import cafe.adriel.voxrecorder.model.Recording

interface IMainView {
    fun updateRecordings(recordings: List<Recording>)
    fun playRecording()
}