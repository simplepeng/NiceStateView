package me.simple.nsv

import android.view.View
import androidx.annotation.LayoutRes

abstract class IStateView {

    @LayoutRes
    abstract fun setLayoutRes(): Int

    open fun onAttach(view: View) {

    }

    open fun onDetach(view: View) {

    }
}