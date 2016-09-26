package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.model.entity.Recording
import rx.Observable

interface IRepository<in T> {
    fun get(): Observable<Recording>
    fun add(item: T)
    fun update(item: T)
    fun remove(item: T)
}