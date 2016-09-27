package cafe.adriel.voxrecorder

import android.net.Uri
import android.os.Environment
import cafe.adriel.voxrecorder.util.color
import java.io.File

object Constant {

    val CONTACT_EMAIL = "me@adriel.cafe"
    val GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
    val MARKET_URI = Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")

    val TEMP_RECORDING_FILE = App.instance.cacheDir.path + "/new-recording.wav"
    val RECORDING_FOLDER = File(Environment.getExternalStorageDirectory(), "VoxRecorder").apply { mkdirs() }
    val SUPPORTED_FORMATS_WITH_COLORS = mapOf(
            "aac" to color(R.color.turquoise), "mp3" to color(R.color.peter_river),
            "m4a" to color(R.color.amethyst), "wma" to color(R.color.sun_flower),
            "wav" to color(R.color.carrot), "flac" to color(R.color.alizarin))

    val PREF_THEME = "theme"
    val PREF_THEME_DARK_MODE = "theme_dark_mode"
    val PREF_THEME_RECORDER_COLOR = "theme_recorder_color"
    val PREF_RECORDING = "recording"
    val PREF_RECORDING_FORMAT = "recording_format"
    val PREF_RECORDING_SAMPLE_RATE = "recording_sample_rate"
    val PREF_RECORDING_CHANNEL = "recording_channel"
    val PREF_RECORDING_SOURCE = "recording_source"
    val PREF_ABOUT = "about"
    val PREF_ABOUT_HELP_FEEDBACK = "about_help_feedback"
    val PREF_ABOUT_RATE = "about_rate"
    val PREF_ABOUT_SHARE = "about_share"
    val PREF_ABOUT_VERSION = "about_version"

    val MIME_TYPE_TEXT = "text/plain"
    val MIME_TYPE_AUDIO = "audio/*"

}