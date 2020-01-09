package me.simple.nsv.sample

import android.view.View
import me.simple.nsv.IStateView
import me.simple.nsv.NsvLog
import me.simple.nsv.R

class NiceSampleLoadingView : IStateView() {

    override fun setLayoutRes() = R.layout.sample_loading_view

    override fun onAttach(view: View) {
        super.onAttach(view)
        NsvLog.d("NiceSampleLoadingView ----> onAttach")
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        NsvLog.d("NiceSampleLoadingView ----> onDetach")
    }

}