package cafe.adriel.voxrecorder.presenter

import cafe.adriel.voxrecorder.model.entity.Recording

interface IMainPresenter {
    fun load()
    fun share(recording: Recording)
    fun edit(recording: Recording)
    fun delete(recording: Recording)
}