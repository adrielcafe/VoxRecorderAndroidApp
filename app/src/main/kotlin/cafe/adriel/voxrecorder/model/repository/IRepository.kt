package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.model.entity.Recording
import rx.Observable

interface IRepository<in T> {
    fun get(): Observable<List<Recording>>
    fun convert(item: T)
    fun rename(item: T, newName: String)
    fun delete(item: T)
}