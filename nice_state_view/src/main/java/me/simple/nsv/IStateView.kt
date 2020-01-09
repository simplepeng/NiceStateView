package me.simple.nsv

import android.view.View
import androidx.annotation.LayoutRes

abstract class IStateView {

    protected var view: View? = null

    @LayoutRes
    abstract fun setLayoutRes(): Int

    open fun onAttach(view: View) {
        this.view = view
    }

    open fun onDetach(view: View) {

    }

    fun setOnViewClickListener(id: Int, onClick: (v: View) -> Unit): IStateView {
        this.view?.findViewById<View>(id)?.setOnClickListener(onClick)
        return this
    }
}