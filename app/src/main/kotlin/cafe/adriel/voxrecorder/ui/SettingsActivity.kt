package cafe.adriel.voxrecorder.ui

import android.content.ClipDescription
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.NavUtils
import android.support.v4.app.ShareCompat
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.MenuItem
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.ui.base.BaseActivity
import cafe.adriel.voxrecorder.util.recreateWithNightMode
import com.thebluealliance.spectrum.SpectrumPreferenceCompat
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbarView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            Constant.PREF_THEME_NIGHT_MODE -> {
                recreateWithNightMode()
            }
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            findPreference(Constant.PREF_ABOUT_HELP_FEEDBACK)?.setOnPreferenceClickListener {
                helpFeedback()
                true
            }
            findPreference(Constant.PREF_ABOUT_RATE)?.setOnPreferenceClickListener {
                rate()
                true
            }
            findPreference(Constant.PREF_ABOUT_SHARE)?.setOnPreferenceClickListener {
                share()
                true
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings)
        }

        override fun onDisplayPreferenceDialog(preference: Preference?) {
            if (!SpectrumPreferenceCompat.onDisplayPreferenceDialog(preference, this)) {
                super.onDisplayPreferenceDialog(preference)
            }
        }

        fun helpFeedback(){
            val subject = "${getString(R.string.app_name)} - ${getString(R.string.help_feedback)}"
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: ${Constant.CONTACT_EMAIL}"))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            startActivity(Intent.createChooser(intent, getString(R.string.send_me_email)))
        }

        fun rate(){
            val intent = Intent(Intent.ACTION_VIEW, Constant.MARKET_URI)
            startActivity(intent)
        }

        fun share(){
            val text = "${getString(R.string.you_should_give_try_vox)} \n ${Constant.GOOGLE_PLAY_URL}"
            ShareCompat.IntentBuilder.from(activity)
                    .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    .setText(text)
                    .startChooser()
        }

    }

}