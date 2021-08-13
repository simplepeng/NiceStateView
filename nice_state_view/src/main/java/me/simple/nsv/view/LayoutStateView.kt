package me.simple.nsv.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.simple.nsv.IStateView
import me.simple.nsv.NiceStateView

class LayoutStateView internal constructor(
    private var builder: NiceStateView.Builder,
    private var contentView: View
) : NiceStateView {

    private var curSateViewKey = NiceStateView.STATE_CONTENT
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
        innerShowContentView()
    }

    override fun showCustom(key: String): IStateView {
        return showStateView(key)
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
        if (curSateViewKey == NiceStateView.STATE_CONTENT) return

        val curStateView = getStateView(curSateViewKey)
        if (curStateView != null) {
            val detachView = viewHolder[curStateView.setLayoutRes()]
            if (detachView != null) {
                curStateView.onDetach(detachView)
            }
        }

        contentView.visibility = View.VISIBLE
        stateLayout.showContentView()
        curSateViewKey = NiceStateView.STATE_CONTENT
    }

    private fun getStateView(key: String): IStateView? {
        return builder.stateViewMap[key]
    }


}