package cafe.adriel.voxrecorder.model

import android.os.Parcel
import android.os.Parcelable
import cafe.adriel.voxrecorder.util.getAudioDuration
import java.io.File
import java.util.*

data class AudioRecording (val filePath : String) : Parcelable {
    val file = File(filePath)

    val fileName = file.nameWithoutExtension

    val fileExtension = file.extension

    val fileSizeKb = file.length().toFloat() / 1024

    val audioDuration = file.getAudioDuration()

    val audioPlayable = audioDuration > 0

    val date = Date(file.lastModified())

    constructor(source: Parcel): this(source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(filePath)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<AudioRecording> = object : Parcelable.Creator<AudioRecording> {
            override fun createFromParcel(source: Parcel): AudioRecording = AudioRecording(source)
            override fun newArray(size: Int): Array<AudioRecording?> = arrayOfNulls(size)
        }
    }
}