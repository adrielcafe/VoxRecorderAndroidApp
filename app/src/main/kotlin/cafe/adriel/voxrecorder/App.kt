package cafe.adriel.voxrecorder

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.support.v7.preference.PreferenceManager
import cafe.adriel.voxrecorder.util.Util
import com.pawegio.kandroid.defaultSharedPreferences
import com.tsengvn.typekit.Typekit

class App: Application() {

    companion object {
        lateinit var instance: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            StrictMode.enableDefaults()
        }
        instance = this
        initPref()
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/Asap-Regular.ttf"))
                .addItalic(Typekit.createFromAsset(this, "fonts/Asap-Italic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/Asap-Bold.ttf"))
                .addBoldItalic(Typekit.createFromAsset(this, "fonts/Asap-BoldItalic.ttf"))
    }

    private fun initPref(){
        PreferenceManager.setDefaultValues(this, R.xml.settings, false)
        // AndroidAudioRecorder only supports WAV format and
        // AndroidAudioConverter doesn't supports x86 ABI
        if (Util.isCpu86()){
            defaultSharedPreferences.edit()
                    .putString(Constant.PREF_RECORDING_FORMAT, "wav")
                    .apply()
        }
    }

}