package me.simple.nsv.sample

import android.view.View
import me.simple.nsv.IStateView
import me.simple.nsv.utils.NsvLog
import me.simple.nsv.R

class NiceSampleEmptyView : IStateView() {

    override fun setLayoutRes() = R.layout.sample_empty_view

    override fun onAttach(view: View) {
        super.onAttach(view)
        NsvLog.d("NiceSampleEmptyView ----> onAttach")
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        NsvLog.d("NiceSampleEmptyView ----> onDetach")
    }
}