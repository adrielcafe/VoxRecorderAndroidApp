package cafe.adriel.voxrecorder.model.repository

import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.model.entity.Recording
import com.pawegio.kandroid.runAsync

class RecordingRepository: IRepository<Recording> {

    override fun add(item: Recording) {

    }

    override fun update(item: Recording) {

    }

    override fun remove(item: Recording) {

    }

    override fun get(callback: (List<Recording>) -> Unit) {
        runAsync {
            val recordings = Constant.RECORDING_FOLDER.listFiles { file, s ->
                Constant.SUPPORTED_EXTENSIONS.any { s.endsWith(it, true) }
            }.map {
                Recording(it.path)
            }.sortedByDescending {
                it.date
            }
            callback(recordings)
        }
    }

}