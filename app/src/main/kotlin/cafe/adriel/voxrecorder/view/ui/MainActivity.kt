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
import cafe.adriel.voxrecorder.model.entity.LoadRecordingsEvent
import cafe.adriel.voxrecorder.model.entity.Recording
import cafe.adriel.voxrecorder.model.entity.SaveRecordingEvent
import cafe.adriel.voxrecorder.util.PrefUtil
import cafe.adriel.voxrecorder.util.Util
import cafe.adriel.voxrecorder.util.setFontIcon
import cafe.adriel.voxrecorder.util.string
import cafe.adriel.voxrecorder.view.ui.base.BaseActivity
import com.eightbitlab.rxbus.Bus
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.toast
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity() {
    private val REQUEST_NEW_RECORDING = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(vToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        MobileAds.initialize(this, string(R.string.admob_app_id))
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if(!recreating) {
            setupFab()
            setupAd()
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            Bus.send(LoadRecordingsEvent())
                        } else {
                            toast(string(R.string.missing_permission))
                        }
                    }
        }
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
//            findItem(R.id.upgrade_pro)?.setFontIcon(GoogleMaterial.Icon.gmd_shop)
//            findItem(R.id.search)?.setFontIcon(GoogleMaterial.Icon.gmd_search)
//            findItem(R.id.filter)?.setFontIcon(GoogleMaterial.Icon.gmd_sort)
            findItem(R.id.settings)?.setFontIcon(GoogleMaterial.Icon.gmd_tune)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
//            R.id.upgrade_pro -> {
//                // TODO
//                return true
//            }
//            R.id.search -> {
//                // TODO
//                return true
//            }
//            R.id.filter -> {
//                // TODO
//                return true
//            }
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
                Bus.send(SaveRecordingEvent(Recording(Constant.TEMP_RECORDING_FILE)))
            }
        }
    }

    fun setupFab(){
        vFab.run {
            backgroundTintList = ColorStateList.valueOf(PrefUtil.getRecorderColor())
            imageTintList = ColorStateList.valueOf(
                    if(Util.isRecorderColorBright()) Color.BLACK else Color.WHITE)
            setOnClickListener { newRecording() }
        }
    }

    fun setupAd(){
        val adRequest = AdRequest.Builder()
                // @adrielcafe Moto X
                .addTestDevice("571D3D1BA9B823441D4838AE32E59BA1")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
        vAd.loadAd(adRequest)
    }

    fun newRecording(){
        RxPermissions.getInstance(this)
                .request(Manifest.permission.RECORD_AUDIO)
                .subscribe { granted ->
                    if(granted){
                        AndroidAudioRecorder.with(this)
                            .setRequestCode(REQUEST_NEW_RECORDING)
                            .setFilePath(Constant.TEMP_RECORDING_FILE)
                            .setColor(PrefUtil.getRecorderColor())
                            .setAutoStart(PrefUtil.isAutoStart())
                            .setKeepDisplayOn(PrefUtil.isKeepDisplayOn())
                            .setSource(PrefUtil.getSource())
                            .setChannel(PrefUtil.getChannel())
                            .setSampleRate(PrefUtil.getSampleRate())
                            .record()
                    } else {
                        toast(string(R.string.missing_permission))
                    }
                }
    }

}