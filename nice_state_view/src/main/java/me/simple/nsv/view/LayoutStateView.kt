package me.simple.nsv.view

import android.util.SparseArray
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
    private val viewHolder = SparseArray<View>()

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

    private fun createView(stateView: IStateView): View {
        return LayoutInflater.from(contentView.context)
            .inflate(stateView.setLayoutRes(), stateLayout, false)
    }

    private fun getView(stateView: IStateView): View {
        var view = viewHolder[stateView.setLayoutRes()]
        if (view == null) {
            view = createView(stateView)
            viewHolder.put(stateView.setLayoutRes(), view)
        }
        return view
    }

    private fun showStateView(
        key: String
    ): IStateView {
        if (curSateViewKey == key) return getStateView(curSateViewKey)!!

        //detach last view
        val lastIStateView = getStateView(curSateViewKey)
        if (lastIStateView?.view != null) {
            lastIStateView.onDetach(lastIStateView.view!!)
            stateLayout.detachView(lastIStateView.view!!)
        }

        contentView.visibility = View.GONE

        //attach current view
        val curIStateView = getStateView(key)
            ?: throw NullPointerException("do you have register $key ?")

        val curView: View = getView(curIStateView)
        stateLayout.attachView(curView)
        curIStateView.onAttach(curView)

        curSateViewKey = key

        return curIStateView
    }

    private fun innerShowContentView() {
        if (curSateViewKey == NiceStateView.STATE_CONTENT) return

        val lastStateView = getStateView(curSateViewKey)
        if (lastStateView?.view != null) {
            lastStateView.onDetach(lastStateView.view!!)
            stateLayout.detachView(lastStateView.view!!)
        }

        contentView.visibility = View.VISIBLE
        stateLayout.showContentView()

        curSateViewKey = NiceStateView.STATE_CONTENT
    }

    private fun getStateView(key: String): IStateView? {
        return builder.stateViewMap[key]
    }


}