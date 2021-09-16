package me.simple.nsv.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import me.simple.nsv.IStateView
import me.simple.nsv.NiceStateView
import me.simple.nsv.R
import me.simple.nsv.sample.NiceWrapperView

/**
 *  做状态切换的Layout
 */
class NiceStateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs), NiceStateView {

    private val builder = NiceStateView.newBuilder()
    private var contentView: View? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.NiceStateLayout)?.apply {
            initAttrs(this)
        }?.recycle()
    }

    private fun initAttrs(ta: TypedArray) {
        if (ta.hasValue(R.styleable.NiceStateLayout_empty_layout_res)) {
            val emptyLayoutRes = ta.getResourceId(R.styleable.NiceStateLayout_empty_layout_res, -1)
            builder.registerEmpty(emptyLayoutRes)
        }
        if (ta.hasValue(R.styleable.NiceStateLayout_loading_layout_res)) {
            val emptyLayoutRes =
                ta.getResourceId(R.styleable.NiceStateLayout_loading_layout_res, -1)
            builder.registerLoading(emptyLayoutRes)
        }
        if (ta.hasValue(R.styleable.NiceStateLayout_error_layout_res)) {
            val emptyLayoutRes = ta.getResourceId(R.styleable.NiceStateLayout_error_layout_res, -1)
            builder.registerError(emptyLayoutRes)
        }
        if (ta.hasValue(R.styleable.NiceStateLayout_retry_layout_res)) {
            val emptyLayoutRes = ta.getResourceId(R.styleable.NiceStateLayout_retry_layout_res, -1)
            builder.registerRetry(emptyLayoutRes)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount < 1) return
        contentView = getChildAt(0)
    }

    internal fun setContentView(contentView: View) {
        val lp = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.addView(contentView, CONTENT_VIEW_INDEX, lp)
    }

    internal fun showContentView() {
        this.removeViewAt(STATE_VIEW_INDEX)
//        this.requestLayout()
    }

    fun attachView(curView: View) {
        val lastView: View? = this.getChildAt(STATE_VIEW_INDEX)
        if (lastView == null) {
            this.addView(curView, STATE_VIEW_INDEX)
        } else {
            this.removeView(lastView)
            this.addView(curView, STATE_VIEW_INDEX)
        }

        curView.bringToFront()
    }

    fun detachView(lastView: View) {
        this.removeView(lastView)
    }

    private fun createView(stateView: IStateView): View {
        val curView = LayoutInflater.from(context).inflate(stateView.setLayoutRes(), this, false)
        stateView.view = curView
        return curView
    }

    private fun showStateView(key: String): IStateView {
        val stateView = builder.getStateView(key)
        contentView?.visibility = View.GONE
        attachView(createView(stateView))
        return stateView
    }

    override fun showLoading(): IStateView {
        return showStateView(NiceStateView.STATE_LOADING)
    }

    override fun showEmpty(): IStateView {
        return showStateView(NiceStateView.STATE_EMPTY)
    }

    override fun showError(): IStateView {
        return showStateView(NiceStateView.STATE_ERROR)
    }

    override fun showRetry(): IStateView {
        return showStateView(NiceStateView.STATE_RETRY)
    }

    override fun showContent() {
        contentView?.visibility = View.VISIBLE
        showContentView()
    }

    fun registerCustom(key: String, layoutRes: Int) {
        val stateView = NiceWrapperView(layoutRes)
        registerCustom(key, stateView)
    }

    fun registerCustom(key: String, stateView: IStateView) {
        builder.registerCustom(key, stateView)
    }

    override fun showCustom(key: String): IStateView {
        return builder.getStateView(key)
    }

    companion object {
        private const val CONTENT_VIEW_INDEX = 0
        private const val STATE_VIEW_INDEX = 1
    }
}