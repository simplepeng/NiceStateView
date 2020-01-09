package me.simple.nsv.sample

import android.view.View
import me.simple.nsv.IStateView
import me.simple.nsv.NsvLog
import me.simple.nsv.R

class NiceSampleRetryView : IStateView() {

    override fun setLayoutRes() = R.layout.sample_retry_view

    override fun onAttach(view: View) {
        super.onAttach(view)
        NsvLog.d("NiceSampleRetryView ----> onAttach")
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        NsvLog.d("NiceSampleRetryView ----> onDetach")
    }
}