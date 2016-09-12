package cafe.adriel.voxrecorder.model

import android.os.Parcel
import android.os.Parcelable

import java.io.File
import java.io.Serializable
import java.util.*

data class AudioRecording (var file : File,  var duration: Float) : Parcelable {

    fun getFileName() = file.nameWithoutExtension

    fun getFileExtension() = file.extension

    fun getFileSizeInKb() = file.length() / 1024

    fun getFileSizeInMb() = getFileSizeInKb() / 1024

    fun getFileDate() = Date(file.lastModified())

    constructor(source: Parcel): this(source.readSerializable() as File, source.readFloat())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeSerializable(file)
        dest?.writeFloat(duration)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<AudioRecording> = object : Parcelable.Creator<AudioRecording> {
            override fun createFromParcel(source: Parcel): AudioRecording = AudioRecording(source)
            override fun newArray(size: Int): Array<AudioRecording?> = arrayOfNulls(size)
        }
    }

}