package cafe.adriel.voxrecorder.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.view.LayoutInflaterCompat
import android.support.v7.app.AppCompatActivity
import cafe.adriel.voxrecorder.util.recreateWithNightMode
import com.mikepenz.iconics.context.IconicsLayoutInflater
import com.tinsuke.icekick.freezeInstanceState
import com.tinsuke.icekick.unfreezeInstanceState
import com.tsengvn.typekit.TypekitContextWrapper

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory(layoutInflater, IconicsLayoutInflater(delegate))
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            recreateWithNightMode()
        }
        unfreezeInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

}