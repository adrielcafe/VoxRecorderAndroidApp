package cafe.adriel.voxrecorder.model.entity

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class DateSeparator(val titleResId: Int, val date: Date): Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<DateSeparator> = object : Parcelable.Creator<DateSeparator> {
            override fun createFromParcel(source: Parcel): DateSeparator = DateSeparator(source)
            override fun newArray(size: Int): Array<DateSeparator?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readSerializable() as Date)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(titleResId)
        dest?.writeSerializable(date)
    }
}