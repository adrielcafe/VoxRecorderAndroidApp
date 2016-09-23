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
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.pawegio.kandroid.defaultSharedPreferences
import java.io.File
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun AppCompatActivity.recreateWithThemeMode() {
    delegate.setLocalNightMode(Util.getThemeMode())
    recreate()
}

fun MenuItem.setFontIcon(icon: GoogleMaterial.Icon) {
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

fun Date.prettyDate() = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault()).format(this)
fun Long.prettySize() = Formatter.formatShortFileSize(App.instance, this)
fun Int.prettyDuration(): String {
    val sec = TimeUnit.SECONDS
    val duration = toLong()
    return "%02d:%02d:%02d".format(sec.toHours(duration), sec.toMinutes(duration), sec.toSeconds(duration))
}

fun Any.pref() = App.instance.defaultSharedPreferences
fun Any.drawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(App.instance, resId)
fun Any.string(@StringRes resId: Int) = App.instance.getString(resId)
fun Any.color(@ColorRes resId: Int) = App.instance.resources.getColor(resId)

fun View.fadeIn() = startAnimation(AnimationUtils.loadAnimation(App.instance, android.R.anim.fade_in))
fun View.fadeOut() = startAnimation(AnimationUtils.loadAnimation(App.instance, android.R.anim.fade_out))