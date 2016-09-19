package cafe.adriel.voxrecorder.view.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.tinsuke.icekick.freezeInstanceState
import com.tinsuke.icekick.unfreezeInstanceState

open class BaseFragment: Fragment() {

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        unfreezeInstanceState(savedInstanceState)
    }

}