package cafe.adriel.voxrecorder.util

import android.media.MediaMetadataRetriever
import android.support.v7.app.AppCompatActivity
import java.io.File

fun AppCompatActivity.recreateWithThemeMode(){
    delegate.setLocalNightMode(Util.getThemeMode())
    recreate()
}

fun File.getAudioDuration() : Float {
    try {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        mmr.release()
        return duration.toFloat() / 1000
    } catch (e : Exception){
        e.printStackTrace()
        return 0f
    }
}

// Because can be corrupted
fun File.isAudioPlayable() : Boolean {
    try {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        mmr.release()
        return true
    } catch (e : Exception){
        e.printStackTrace()
        return false
    }
}