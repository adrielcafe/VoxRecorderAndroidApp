package cafe.adriel.voxrecorder.util

import android.os.Build
import android.support.v7.app.AppCompatActivity
import com.pawegio.kandroid.fromApi
import com.pawegio.kandroid.toApi

fun AppCompatActivity.recreateWithNightMode(){
    delegate.setLocalNightMode(Util.getNightMode())
    recreate()
}