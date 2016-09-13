package cafe.adriel.voxrecorder.ui


import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.v14.preference.PreferenceFragment
import cafe.adriel.voxrecorder.R

class SettingsActivity : PreferenceActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, SettingsFragment())
                .commit()
    }

    class SettingsFragment : PreferenceFragment() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings)
        }
    }

}