package cafe.adriel.voxrecorder.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cafe.adriel.voxrecorder.R
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.pawegio.kandroid.IntentFor
import kotlinx.android.synthetic.main.activity_crash.*

class CrashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)
        vRestartApp.setOnClickListener {
            CustomActivityOnCrash.restartApplicationWithIntent(
                    this,
                    IntentFor<MainActivity>(this),
                    CustomActivityOnCrash.getEventListenerFromIntent(intent))
        }
    }

}