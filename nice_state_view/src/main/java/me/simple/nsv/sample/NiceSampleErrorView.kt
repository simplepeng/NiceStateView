package me.simple.nsv.sample

import android.view.View
import me.simple.nsv.IStateView
import me.simple.nsv.NsvLog
import me.simple.nsv.R

class NiceSampleErrorView : IStateView() {

    override fun setLayoutRes() = R.layout.sample_error_view

    override fun onAttach(view: View) {
        super.onAttach(view)
        NsvLog.d("NiceSampleErrorView ----> onAttach")
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        NsvLog.d("NiceSampleErrorView ----> onDetach")
    }
}