package cafe.adriel.voxrecorder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cafe.adriel.voxrecorder.R
import cafe.adriel.voxrecorder.presenter.IMainPresenter
import cafe.adriel.voxrecorder.presenter.MainPresenter
import cafe.adriel.voxrecorder.ui.base.BaseFragment
import cafe.adriel.voxrecorder.view.IMainView

class MainFragment : BaseFragment(), IMainView {

    val mainPresenter : IMainPresenter = MainPresenter(this)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater?.inflate(R.layout.fragment_main, container, false)

        return rootView ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun newRecording() {

    }

    override fun playRecording() {

    }

}