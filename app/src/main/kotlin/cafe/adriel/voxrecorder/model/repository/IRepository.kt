package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.model.entity.Recording
import rx.Observable
import java.util.*

interface IRepository<T> {
    fun initFileObserver()
    fun add(item: T)
    fun update(item: T)
    fun remove(item: T)
    fun get(): Observable<ArrayList<Recording>>
    fun unsubscribe()
}