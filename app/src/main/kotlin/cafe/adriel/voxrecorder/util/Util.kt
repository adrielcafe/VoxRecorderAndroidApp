package cafe.adriel.voxrecorder.util

import android.os.Build
import android.support.v7.app.AppCompatDelegate
import cafe.adriel.voxrecorder.App
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import com.pawegio.kandroid.defaultSharedPreferences
import com.pawegio.kandroid.fromApi
import com.pawegio.kandroid.toApi

object Util {

    fun getThemeMode() : Int {
        val isNightMode = App.instance.defaultSharedPreferences
                .getBoolean(Constant.PREF_THEME_DARK_MODE, false)
        return if(isNightMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
    }

    fun getRecorderColor() = App.instance.defaultSharedPreferences
            .getInt(Constant.PREF_THEME_RECORDER_COLOR, App.instance.getColor(R.color.amethyst))

    fun isRecorderColorBright() = cafe.adriel.androidaudiorecorder.Util
            .isBrightColor(getRecorderColor())

    fun isAbi86() : Boolean {
        var abi86 = false
        fromApi(21){
            abi86 = Build.SUPPORTED_ABIS.any { it.contains("86") }
        }
        toApi(21){
            abi86 = Build.CPU_ABI.contains("86") || Build.CPU_ABI2.contains("86")
        }
        return abi86
    }
}