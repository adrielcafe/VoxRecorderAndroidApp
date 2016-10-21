package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.androidaudioconverter.model.AudioFormat
import cafe.adriel.voxrecorder.model.entity.Recording
import rx.Observable

interface IRepository<in T> {
    fun get(): Observable<List<Recording>>
    fun save(item: T)
    fun convert(item: T, format: AudioFormat)
    fun rename(item: T, newName: String)
    fun delete(item: T)
}