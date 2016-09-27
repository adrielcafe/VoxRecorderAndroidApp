package cafe.adriel.voxrecorder.model.entity

import android.os.Parcel
import android.os.Parcelable
import cafe.adriel.voxrecorder.util.getAudioDuration
import java.io.File
import java.util.*

data class Recording(val filePath: String): Parcelable {
    val file = File(filePath)
    val name = file.nameWithoutExtension
    val format = file.extension
    val nameWithFormat = "$name.$format"
    val size = file.length()
    val duration = file.getAudioDuration().toInt() / 1000
    val playable = duration > 0
    val date = Date(file.lastModified())

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Recording> = object: Parcelable.Creator<Recording> {
            override fun createFromParcel(source: Parcel): Recording = Recording(source)
            override fun newArray(size: Int): Array<Recording?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel): this(source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(filePath)
    }
}