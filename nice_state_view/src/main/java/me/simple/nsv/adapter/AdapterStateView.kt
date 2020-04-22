package me.simple.nsv.adapter

import androidx.recyclerview.widget.RecyclerView
import me.simple.nsv.IStateView
import me.simple.nsv.NiceStateView

class AdapterStateView<VH : RecyclerView.ViewHolder>(
    builder: NiceStateView.Builder,
    realAdapter: RecyclerView.Adapter<VH>
) : NiceStateView {

    val stateAdapter = StateAdapter(builder, realAdapter)

    override fun showLoading(): IStateView {
        return stateAdapter.showLoading()
    }

    override fun showEmpty(): IStateView {
        return stateAdapter.showEmpty()
    }

    override fun showError(): IStateView {
        return stateAdapter.showError()
    }

    override fun showRetry(): IStateView {
        return stateAdapter.showRetry()
    }

    override fun showContent() {
        stateAdapter.showContent()
    }

    override fun <T> showCustom(clazz: Class<T>): IStateView {
        return stateAdapter.showCustom(clazz)
    }
}