package cafe.adriel.voxrecorder.util

import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cafe.adriel.voxrecorder.App
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import java.io.File

fun AppCompatActivity.recreateWithThemeMode() {
    delegate.setLocalNightMode(Util.getThemeMode())
    recreate()
}

fun MenuItem.setFontIcon(icon : GoogleMaterial.Icon) {
    setIcon(IconicsDrawable(App.instance)
            .icon(icon)
            .color(Color.WHITE)
            .sizeDp(24))
}

fun File.getAudioDuration() : Float {
    try {
        var duration = ""
        MediaMetadataRetriever().apply {
            setDataSource(path)
            duration = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            release()
        }
        return duration.toFloat() / 1000
    } catch (e : Exception){
        e.printStackTrace()
        return 0f
    }
}