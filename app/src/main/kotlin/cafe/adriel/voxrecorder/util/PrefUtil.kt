package cafe.adriel.voxrecorder.util

import cafe.adriel.androidaudiorecorder.model.AudioChannel
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate
import cafe.adriel.androidaudiorecorder.model.AudioSource
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R

object PrefUtil {

    fun isAutoStart() = pref().getBoolean(Constant.PREF_RECORDING_AUTO_PLAY, false)

    fun isKeepDisplayOn() = pref().getBoolean(Constant.PREF_RECORDING_KEEP_DISPLAY_ON, true)

    fun getRecorderColor() = pref().getInt(Constant.PREF_THEME_RECORDER_COLOR, color(R.color.turquoise))

    fun getSource() = when(pref().getString(Constant.PREF_RECORDING_SOURCE, "mic")){
        "camcorder" -> AudioSource.CAMCORDER
        else -> AudioSource.MIC
    }

    fun getChannel() = when(pref().getString(Constant.PREF_RECORDING_CHANNEL, "stereo")){
        "mono" -> AudioChannel.MONO
        else -> AudioChannel.STEREO
    }

    fun getSampleRate() = when(pref().getString(Constant.PREF_RECORDING_SAMPLE_RATE, "44100")){
        "8000" -> AudioSampleRate.HZ_8000
        "11025" -> AudioSampleRate.HZ_11025
        "16000" -> AudioSampleRate.HZ_16000
        "22050" -> AudioSampleRate.HZ_22050
        "32000" -> AudioSampleRate.HZ_32000
        "48000" -> AudioSampleRate.HZ_48000
        else -> AudioSampleRate.HZ_44100
    }

}