package cafe.adriel.voxrecorder.util

import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.recreateWithNightMode(){
    delegate.setLocalNightMode(Util.getCurrentNightMode())
    recreate()
}