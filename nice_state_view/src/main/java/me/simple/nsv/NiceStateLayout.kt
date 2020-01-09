package me.simple.nsv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class NiceStateLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun setContentView(contentView: View) {
        this.addView(contentView, CONTENT_VIEW_INDEX)
    }

    fun setStateView(iStateView: IStateView, includeView: View) {
        val lastIncludeView: View? = this.getChildAt(STATE_VIEW_INDEX)
        if (lastIncludeView == null) {
            this.addView(includeView, STATE_VIEW_INDEX)
        } else {
            iStateView.onDetach(lastIncludeView)
            this.removeView(lastIncludeView)
            this.addView(includeView, STATE_VIEW_INDEX)
            iStateView.onAttach(includeView)
        }

        includeView.bringToFront()
    }

    fun showContentView() {
        this.removeViewAt(STATE_VIEW_INDEX)
    }

    companion object {
        private const val CONTENT_VIEW_INDEX = 0
        private const val STATE_VIEW_INDEX = 1
    }
}