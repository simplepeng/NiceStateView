package me.simple.nsv

import android.view.View
import androidx.annotation.LayoutRes

abstract class IStateView {

    var view: View? = null

    /**
     * set layout res id
     */
    @LayoutRes
    abstract fun setLayoutRes(): Int

    /**
     * attach view to state layout
     */
    open fun onAttach(view: View) {
        this.view = view
    }

    /**
     * detach view from state layout
     * can release something animation：like gif，lottie
     */
    open fun onDetach(view: View) {

    }

    /**
     * set child view click listener
     */
    fun setOnViewClickListener(id: Int, onClick: (v: View) -> Unit): IStateView {
        this.view?.findViewById<View>(id)?.setOnClickListener(onClick)
        return this
    }

}