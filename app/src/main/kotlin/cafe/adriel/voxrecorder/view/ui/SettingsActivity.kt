package cafe.adriel.voxrecorder.view.ui

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.view.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(vToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

}