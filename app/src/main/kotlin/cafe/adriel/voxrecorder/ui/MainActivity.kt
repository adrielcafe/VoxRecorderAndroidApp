package cafe.adriel.voxrecorder.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuItem
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.ui.base.BaseActivity
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.pawegio.kandroid.IntentFor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarView)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        fabView.setImageDrawable(IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_mic_none)
                .color(Color.WHITE)
                .sizeDp(48))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        menu?.findItem(R.id.settings)?.icon = IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_tune)
                .color(Color.WHITE)
                .sizeDp(24)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            R.id.settings -> {
                startActivity(IntentFor<SettingsActivity>(this))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}