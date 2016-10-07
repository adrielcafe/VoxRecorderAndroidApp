package cafe.adriel.voxrecorder

import android.app.Application
import android.content.Context
import android.os.StrictMode
import cafe.adriel.androidaudioconverter.AndroidAudioConverter
import cafe.adriel.androidaudioconverter.callback.ILoadCallback
import cafe.adriel.androidaudioconverter.model.AudioFormat
import cafe.adriel.voxrecorder.util.Util
import cafe.adriel.voxrecorder.util.pref
import com.tsengvn.typekit.Typekit

class App: Application() {

    companion object {
        lateinit var instance: Context
            private set
    }

    override fun onCreate() {
        if(BuildConfig.DEBUG){
            StrictMode.enableDefaults()
        }
        super.onCreate()
        instance = this
        initPreferences()
        initCustomFonts()
        initAudioConverter()
    }

    private fun initPreferences(){
        // AndroidAudioRecorder only supports WAV format and
        // AndroidAudioConverter doesn't supports x86 ABI
        if (Util.isCpu86()) {
            pref().edit()
                    .putString(Constant.PREF_RECORDING_FORMAT, AudioFormat.WAV.format)
                    .apply()
        }
    }

    private fun initCustomFonts(){
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/Asap-Regular.ttf"))
                .addItalic(Typekit.createFromAsset(this, "fonts/Asap-Italic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/Asap-Bold.ttf"))
                .addBoldItalic(Typekit.createFromAsset(this, "fonts/Asap-BoldItalic.ttf"))
    }

    private fun initAudioConverter(){
        AndroidAudioConverter.load(this, object : ILoadCallback {
            override fun onSuccess() {

            }
            override fun onFailure(error: Exception) {
                error.printStackTrace()
            }
        })
    }

}