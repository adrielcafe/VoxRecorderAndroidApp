package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.model.entity.Recording

interface IRepository<T> {
    fun add(item: T)
    fun update(item: T)
    fun remove(item: T)
    fun get(callback: (List<Recording>) -> Unit)
}