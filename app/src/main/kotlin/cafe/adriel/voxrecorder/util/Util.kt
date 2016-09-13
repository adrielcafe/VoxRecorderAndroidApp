package cafe.adriel.voxrecorder.util

import android.support.v7.app.AppCompatDelegate
import cafe.adriel.voxrecorder.App
import cafe.adriel.voxrecorder.Constant
import com.pawegio.kandroid.defaultSharedPreferences

object Util {

    fun getCurrentNightMode() : Int {
        val isNightMode = App.instance.defaultSharedPreferences
                .getBoolean(Constant.PREF_THEME_NIGHT_MODE, false)
        return if(isNightMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
    }

}