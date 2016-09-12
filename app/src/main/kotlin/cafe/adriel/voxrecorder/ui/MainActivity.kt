package cafe.adriel.voxrecorder.ui

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.presenter.IMainPresenter
import cafe.adriel.voxrecorder.presenter.MainPresenter
import cafe.adriel.voxrecorder.ui.base.BaseActivity
import cafe.adriel.voxrecorder.view.IMainView
import com.tinsuke.icekick.freezeInstanceState
import com.tinsuke.icekick.state
import com.tinsuke.icekick.unfreezeInstanceState
import com.tsengvn.typekit.TypekitContextWrapper

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}