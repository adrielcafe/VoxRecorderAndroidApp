package cafe.adriel.voxrecorder.view.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.RecordingAddedEvent
import cafe.adriel.voxrecorder.util.Util
import cafe.adriel.voxrecorder.util.setFontIcon
import cafe.adriel.voxrecorder.view.ui.base.BaseActivity
import com.eightbitlab.rxbus.Bus
import com.github.jksiezni.permissive.Permissive
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.pawegio.kandroid.IntentFor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity() {
    private val REQUEST_NEW_RECORDING = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(vToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupFab()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // TODO
        Permissive.Request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withRationale({ activity, allowablePermissions, messenger ->
//                    Snackbar.make(lRoot, R.string., Snackbar.LENGTH_LONG)
//                            .setAction(R.string., {
//                                messenger.cancelRequest()
//                            })
//                            .show()
                })
                .whenPermissionsGranted({

                })
                .whenPermissionsRefused({

                })
                .execute(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        super.onSharedPreferenceChanged(sharedPreferences, key)
        when(key) {
            Constant.PREF_THEME_RECORDER_COLOR -> {
                setupFab()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu?.run {
            findItem(R.id.upgrade_pro)?.setFontIcon(GoogleMaterial.Icon.gmd_shop)
            findItem(R.id.filter)?.setFontIcon(GoogleMaterial.Icon.gmd_sort)
            findItem(R.id.settings)?.setFontIcon(GoogleMaterial.Icon.gmd_tune)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.upgrade_pro -> {
                // TODO
                return true
            }
            R.id.filter -> {
                // TODO
                return true
            }
            R.id.settings -> {
                startActivity(IntentFor<SettingsActivity>(this))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_NEW_RECORDING){
                Bus.send(RecordingAddedEvent(Recording(Constant.TEMP_RECORDING_FILE)))
            }
        }
    }

    fun setupFab(){
        vFab.run {
            backgroundTintList = ColorStateList.valueOf(Util.getRecorderColor())
            imageTintList = ColorStateList.valueOf(
                    if(Util.isRecorderColorBright()) Color.BLACK else Color.WHITE)
            setOnClickListener { newRecording() }
        }
    }

    // TODO
    fun newRecording(){
        AndroidAudioRecorder.with(this)
                .setColor(Util.getRecorderColor())
                .setFilePath(Constant.TEMP_RECORDING_FILE)
                .setRequestCode(REQUEST_NEW_RECORDING)
                .record()
    }

}