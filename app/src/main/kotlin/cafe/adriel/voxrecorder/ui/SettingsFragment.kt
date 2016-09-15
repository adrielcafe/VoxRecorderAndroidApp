package cafe.adriel.voxrecorder.ui

import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.view.ISettingsView
import com.thebluealliance.spectrum.SpectrumPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat(), ISettingsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPreference(Constant.PREF_ABOUT_HELP_FEEDBACK)?.setOnPreferenceClickListener {
            sendFeedback()
            true
        }
        findPreference(Constant.PREF_ABOUT_RATE)?.setOnPreferenceClickListener {
            rateApp()
            true
        }
        findPreference(Constant.PREF_ABOUT_SHARE)?.setOnPreferenceClickListener {
            shareApp()
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

    override fun sendFeedback(){
        val subject = "${getString(R.string.app_name)} - ${getString(R.string.help_feedback)}"
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: ${Constant.CONTACT_EMAIL}"))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(Intent.createChooser(intent, getString(R.string.send_me_email)))
    }

    override fun rateApp(){
        val intent = Intent(Intent.ACTION_VIEW, Constant.MARKET_URI)
        startActivity(intent)
    }

    override fun shareApp(){
        val text = "${getString(R.string.you_should_give_try_vox)} \n ${Constant.GOOGLE_PLAY_URL}"
        ShareCompat.IntentBuilder.from(activity)
                .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                .setText(text)
                .startChooser()
    }

}