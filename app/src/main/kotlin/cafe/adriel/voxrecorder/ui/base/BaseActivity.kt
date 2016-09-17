package cafe.adriel.voxrecorder.ui.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.ui.SettingsActivity
import cafe.adriel.voxrecorder.util.recreateWithThemeMode
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.tinsuke.icekick.freezeInstanceState
import com.tinsuke.icekick.state
import com.tinsuke.icekick.unfreezeInstanceState
import com.tsengvn.typekit.TypekitContextWrapper

open class BaseActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    var shouldRecreateOnFocus by state(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(layoutInflater, IconicsLayoutInflater(delegate))
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            recreateWithThemeMode()
        }
        unfreezeInstanceState(savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            Constant.PREF_THEME_DARK_MODE -> {
                if(this is SettingsActivity){
                    recreateWithThemeMode()
                } else {
                    shouldRecreateOnFocus = true
                }
            }
            Constant.PREF_THEME_RECORDER_COLOR -> {
                if(this !is SettingsActivity){
                    shouldRecreateOnFocus = true
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus && shouldRecreateOnFocus){
            shouldRecreateOnFocus = false
            recreateWithThemeMode()
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}