package cafe.adriel.voxrecorder.ui.base

import android.app.Fragment
import android.os.Bundle
import com.tinsuke.icekick.freezeInstanceState
import com.tinsuke.icekick.unfreezeInstanceState

open class BaseFragment : Fragment() {

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        unfreezeInstanceState(savedInstanceState)
    }

}