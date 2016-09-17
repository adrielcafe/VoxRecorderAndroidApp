package cafe.adriel.voxrecorder

import android.app.Application
import android.os.Build
import cafe.adriel.voxrecorder.util.Util
import com.pawegio.kandroid.defaultSharedPreferences
import com.pawegio.kandroid.e
import com.tsengvn.typekit.Typekit

class App : Application() {

    companion object {
        lateinit var instance : App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/MerriweatherSans-Regular.ttf"))
                .addItalic(Typekit.createFromAsset(this, "fonts/MerriweatherSans-Italic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/MerriweatherSans-Bold.ttf"))
                .addBoldItalic(Typekit.createFromAsset(this, "fonts/MerriweatherSans-BoldItalic.ttf"))
        setDefaultAudioFormat()
    }

    fun setDefaultAudioFormat(){
        if (Util.isAbi86()){
            defaultSharedPreferences.edit()
                    .putString(Constant.PREF_RECORDING_FORMAT, "wav")
                    .apply()
        }
    }

}