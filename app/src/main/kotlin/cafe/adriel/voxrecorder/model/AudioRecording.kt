package cafe.adriel.voxrecorder.model

import java.io.File
import java.util.*

data class AudioRecording (var file : File,  var duration: Float) {

    fun getFileName() = file.nameWithoutExtension

    fun getFileExtension() = file.extension

    fun getFileSizeInKb() = file.length() / 1024

    fun getFileSizeInMb() = getFileSizeInKb() / 1024

    fun getFileDate() = Date(file.lastModified())

}