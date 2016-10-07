package cafe.adriel.voxrecorder.util

import android.os.Build
import android.support.v7.app.AppCompatDelegate
import cafe.adriel.voxrecorder.Constant
import com.pawegio.kandroid.fromApi
import com.pawegio.kandroid.toApi
import khronos.Dates
import khronos.toString

object Util {

    fun isDarkTheme() =
            pref().getBoolean(Constant.PREF_THEME_DARK_MODE, false)

    fun getThemeMode() =
            if(isDarkTheme()) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

    fun isRecorderColorBright() =
            cafe.adriel.androidaudiorecorder.Util.isBrightColor(PrefUtil.getRecorderColor())

    fun isSupportedFormat(filePath: String) =
            Constant.SUPPORTED_FORMATS.any { filePath.endsWith(it, true) }

    fun isCpu86(): Boolean {
        var abi86 = false
        fromApi(Build.VERSION_CODES.LOLLIPOP){
            abi86 = Build.SUPPORTED_ABIS.any { it.contains("86") }
        }
        toApi(Build.VERSION_CODES.LOLLIPOP){
            abi86 = Build.CPU_ABI.contains("86") || Build.CPU_ABI2.contains("86")
        }
        return abi86
    }

    fun getFileNameWithDate() = Dates.now.let { "${it.toString("yyyy-MM-dd")}_${it.time}" }

}