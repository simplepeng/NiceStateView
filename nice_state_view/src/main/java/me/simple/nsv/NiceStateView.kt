package me.simple.nsv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.NullPointerException

class NiceStateView constructor(
    private var builder: Builder,
    private var contentView: View
) {

    private var curSateViewKey = STATE_CONTENT
    private var parentViewGroup: ViewGroup? = contentView.parent as ViewGroup?
    private val stateLayout = NiceStateLayout(contentView.context)
    private val viewHolder = mutableMapOf<Int, View>()

    init {
        if (parentViewGroup == null) {
            throw NullPointerException("content view parent is null")
        }

        val parent = parentViewGroup!!
        val index = parent.indexOfChild(contentView)
        parent.removeView(contentView)

        val params = contentView.layoutParams
        parent.addView(stateLayout, index, params)

        stateLayout.setContentView(contentView)
    }

    fun showLoading() {
        showStateView(STATE_LOADING)
    }

    fun showEmpty() {
        showStateView(STATE_EMPTY)
    }

    fun showError() {
        showStateView(STATE_ERROR)
    }

    fun showRetry() {
        showStateView(STATE_RETRY)
    }

    fun showContent() {
        innerShowContentView()
    }

    private fun showStateView(key: String) {
        if (curSateViewKey == key) return
        curSateViewKey = key

        val stateView = getStateView(curSateViewKey)
        val layoutId = stateView.setLayoutRes()

        var includeView: View? = viewHolder[layoutId]
        if (includeView == null) {
            includeView =
                LayoutInflater.from(contentView.context).inflate(layoutId, stateLayout, false)
        }

        contentView.visibility = View.GONE
        stateLayout.setStateView(stateView, includeView!!)
    }

    private fun innerShowContentView() {
        val curStateView = builder.stateViewMap[curSateViewKey]
        if (curStateView != null) {
            val detachView = viewHolder[curStateView.setLayoutRes()]
            if (detachView != null) {
                curStateView.onDetach(detachView)
            }
        }

        contentView.visibility = View.VISIBLE
        stateLayout.showContentView()
        curSateViewKey = STATE_CONTENT
    }

    private fun getStateView(key: String): IStateView {
        return builder.stateViewMap[key]
            ?: throw NullPointerException("do you have register $key ?")
    }

    companion object {
        const val STATE_EMPTY = "state_empty"
        const val STATE_LOADING = "state_loading"
        const val STATE_ERROR = "state_error"
        const val STATE_RETRY = "state_retry"
        const val STATE_CONTENT = "state_content"

        /**
         *
         */
        class Builder {

            val stateViewMap = mutableMapOf<String, IStateView>()

            fun registerEmpty(stateView: IStateView): Builder {
                stateViewMap[STATE_EMPTY] = stateView
                return this
            }

            fun registerError(stateView: IStateView): Builder {
                stateViewMap[STATE_ERROR] = stateView
                return this
            }

            fun registerRetry(stateView: IStateView): Builder {
                stateViewMap[STATE_RETRY] = stateView
                return this
            }

            fun registerLoading(stateView: IStateView): Builder {
                stateViewMap[STATE_LOADING] = stateView
                return this
            }

            fun wrapContent(view: View?): NiceStateView {
                if (view == null) {
                    throw NullPointerException("content view can not be null")
                }
                return NiceStateView(this, view)
            }
        }

        /**
         *
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}