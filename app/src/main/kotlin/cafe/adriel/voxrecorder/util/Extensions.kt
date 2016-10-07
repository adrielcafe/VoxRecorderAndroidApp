package cafe.adriel.voxrecorder.util

import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.format.Formatter
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import cafe.adriel.voxrecorder.App
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.pawegio.kandroid.defaultSharedPreferences
import java.io.File
import java.text.DateFormat
import java.util.*

fun AppCompatActivity.recreateWithThemeMode() {
    delegate.setLocalNightMode(Util.getThemeMode())
    recreate()
}

fun MenuItem.setFontIcon(icon: IIcon) {
    setIcon(IconicsDrawable(App.instance)
            .icon(icon)
            .color(Color.WHITE)
            .sizeDp(24))
}

fun File.getAudioDuration(): Int {
    try {
        var duration = ""
        MediaMetadataRetriever().run {
            setDataSource(path)
            duration = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            release()
        }
        return duration.toInt()
    } catch (e: Exception){
        e.printStackTrace()
        return 0
    }
}

fun Boolean?.orFalse() = this ?: false

fun Date.prettyDate() = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()).format(this)
fun Long.prettySize() = Formatter.formatShortFileSize(App.instance, this)
fun Int.prettyDuration(): String {
    val h = this / 3600
    val m = (this - h * 3600) / 60
    val s = this - (h * 3600 + m * 60)
    return "%02d:%02d:%02d".format(h, m, s)
}

fun View.fadeIn() = startAnimation(AnimationUtils.loadAnimation(App.instance, android.R.anim.fade_in))
fun View.fadeOut() = startAnimation(AnimationUtils.loadAnimation(App.instance, android.R.anim.fade_out))

fun pref() = App.instance.defaultSharedPreferences
fun drawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(App.instance, resId)
fun string(@StringRes resId: Int, vararg args: String = emptyArray()) = App.instance.getString(resId, *args)
fun color(@ColorRes resId: Int) = ContextCompat.getColor(App.instance, resId)
