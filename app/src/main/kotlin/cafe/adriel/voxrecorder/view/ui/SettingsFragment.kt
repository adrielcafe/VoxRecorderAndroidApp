package cafe.adriel. voxrecorder.view.ui

import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.preference.PreferenceFragmentCompat
import cafe.adriel.voxrecorder.Constant
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.util.Util
import cafe.adriel.voxrecorder.util.string
import cafe.adriel.voxrecorder.view.ISettingsView

class SettingsFragment: PreferenceFragmentCompat(), ISettingsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Util.isCpu86()) {
            findPreference(Constant.PREF_RECORDING_FORMAT).run {
                isEnabled = false
                setSummary(R.string.audio_file_format_unsupported)
            }
        }
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

    override fun sendFeedback(){
        val subject = "${string(R.string.app_name)} - ${string(R.string.help_feedback)}"
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: ${Constant.CONTACT_EMAIL}"))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(Intent.createChooser(intent, string(R.string.send_me_email)))
    }

    override fun rateApp(){
        val intent = Intent(Intent.ACTION_VIEW, Constant.MARKET_URI)
        startActivity(intent)
    }

    override fun shareApp(){
        val text = "${string(R.string.you_should_give_try_vox)} \n ${Constant.GOOGLE_PLAY_URL}"
        ShareCompat.IntentBuilder.from(activity)
                .setType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                .setText(text)
                .startChooser()
    }

}