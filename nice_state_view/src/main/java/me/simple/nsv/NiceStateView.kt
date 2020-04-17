package me.simple.nsv

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NiceStateView private constructor(
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

    fun showLoading(): IStateView {
        return showStateView(STATE_LOADING)
    }

    fun showEmpty(): IStateView {
        return showStateView(STATE_EMPTY)
    }

    fun showError(): IStateView {
        return showStateView(STATE_ERROR)
    }

    fun showRetry(): IStateView {
        return showStateView(STATE_RETRY)
    }

    fun showContent() {
        innerShowContentView()
    }

    fun <T> showCustom(clazz: Class<T>): IStateView {
        return showStateView(clazz.name)
    }

    private fun showStateView(
        key: String
    ): IStateView {
        if (curSateViewKey == key) return getStateView(curSateViewKey)!!

        //detach last view
        val lastIStateView = getStateView(curSateViewKey)
        if (lastIStateView != null) {
            val lastView = viewHolder[lastIStateView.setLayoutRes()]
            if (lastView != null) {
                lastIStateView.onDetach(lastView)
                stateLayout.detachView(lastView)
            }
        }

        //attach current view
        val curIStateView =
            getStateView(key) ?: throw NullPointerException("do you have register $key ?")

        var curView: View? = viewHolder[curIStateView.setLayoutRes()]
        if (curView == null) {
            curView = LayoutInflater.from(contentView.context)
                .inflate(curIStateView.setLayoutRes(), stateLayout, false)
            viewHolder[curIStateView.setLayoutRes()] = curView
        }

        contentView.visibility = View.GONE
        stateLayout.attachView(curView!!)
        curIStateView.onAttach(curView)

        curSateViewKey = key
        return curIStateView
    }

    private fun innerShowContentView() {
        if (curSateViewKey == STATE_CONTENT) return

        val curStateView = getStateView(curSateViewKey)
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

    private fun getStateView(key: String): IStateView? {
        return builder.stateViewMap[key]
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
        fun newBuilder(): Builder {
            return Builder()
        }
    }

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

        fun registerCustom(stateView: IStateView): Builder {
            stateViewMap[stateView.javaClass.name] = stateView
            return this
        }

        fun wrapContent(view: View?): NiceStateView {
            if (view == null) {
                throw NullPointerException("content view can not be null")
            }
            return NiceStateView(this, view)
        }

        fun wrapContent(activity: Activity): NiceStateView {
            val contentView =
                (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
            return wrapContent(contentView)
        }

        fun wrapContent(fragment: Fragment): NiceStateView {
            return wrapContent(fragment.view)
        }
    }

}