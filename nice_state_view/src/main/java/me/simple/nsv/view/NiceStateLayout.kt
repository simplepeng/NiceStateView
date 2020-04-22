package me.simple.nsv.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

internal class NiceStateLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    fun setContentView(contentView: View) {
        val lp = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.addView(contentView,
            CONTENT_VIEW_INDEX, lp)
    }

    fun showContentView() {
        this.removeViewAt(STATE_VIEW_INDEX)
//        this.requestLayout()
    }

    fun attachView(curView: View) {
        val lastView: View? = this.getChildAt(STATE_VIEW_INDEX)
        if (lastView == null) {
            this.addView(curView,
                STATE_VIEW_INDEX
            )
        } else {
            this.removeView(lastView)
            this.addView(curView,
                STATE_VIEW_INDEX
            )
        }

        curView.bringToFront()
    }

    fun detachView(lastView: View) {
        this.removeView(lastView)
    }

    companion object {
        private const val CONTENT_VIEW_INDEX = 0
        private const val STATE_VIEW_INDEX = 1
    }
}