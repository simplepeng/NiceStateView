package me.simple.nsv.adapter

import android.database.Observable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import me.simple.nsv.IStateView
import me.simple.nsv.NiceStateView
import java.util.*

@Suppress("UNCHECKED_CAST")
class AdapterStateView<VH : ViewHolder> internal constructor(
    private var builder: NiceStateView.Builder,
    private val realAdapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<ViewHolder>(), NiceStateView {

    var typeState = NiceStateView.STATE_NORMAL

    override fun getItemCount(): Int {
        return if (isTypeState) 1 else realAdapter.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && isTypeState) typeState.hashCode()
        else realAdapter.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return if (position == 0 && isTypeState) super.getItemId(position)
        else realAdapter.getItemId(position)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        if (isTypeState) {
            val stateView: IStateView = getStateView(typeState)
            val inflater = LayoutInflater.from(viewGroup.context)
            val stateItemView: View =
                inflater.inflate(stateView.setLayoutRes(), viewGroup, false)
            return StateViewHolder(stateItemView)
        }
        return realAdapter.onCreateViewHolder(viewGroup, viewType)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        onBindViewHolder(viewHolder, position, emptyList())
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (viewHolder is StateViewHolder) {
            val holder = viewHolder
        } else {
            realAdapter.onBindViewHolder(viewHolder as VH, position, payloads)
        }
    }


    override fun onFailedToRecycleView(holder: ViewHolder): Boolean {
        return if (holder is StateViewHolder) false
        else realAdapter.onFailedToRecycleView(holder as VH)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        if (holder is StateViewHolder) {
            holder.setState(typeState)
            getStateView(typeState).onAttach(holder.itemView)
            return
        }
        realAdapter.onViewAttachedToWindow(holder as VH)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder is StateViewHolder) {
            val typeSate = holder.typeSate
            getStateView(typeSate).onDetach(holder.itemView)
            return
        }
        realAdapter.onViewDetachedFromWindow(holder as VH)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder is StateViewHolder) return
        realAdapter.onViewRecycled(holder as VH)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        setFullSpan(recyclerView)
        if (!isRegistered) {
            realAdapter.registerAdapterDataObserver(mDataObserver)
        }
        realAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (isRegistered) {
            realAdapter.unregisterAdapterDataObserver(mDataObserver)
        }
        realAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    /**
     *
     */
    private fun setFullSpan(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager ?: return
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = getItemViewType(position)
                    return if (isTypeState) layoutManager.spanCount else 1
                }
            }
        }
    }

    /**
     *
     */
    private val isRegistered: Boolean
        private get() {
            var isRegistered = false
            try {
                val clazz: Class<out RecyclerView.Adapter<*>> =
                    RecyclerView.Adapter::class.java
                val field = clazz.getDeclaredField("mObservable")
                field.isAccessible = true
                val observable =
                    field[realAdapter] as Observable<*>
                val observersField =
                    Observable::class.java.getDeclaredField("mObservers")
                observersField.isAccessible = true
                val list =
                    observersField[observable] as ArrayList<Any>
                isRegistered = list.contains(mDataObserver)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return isRegistered
        }

    private val mDataObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            typeState = NiceStateView.STATE_CONTENT
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            typeState = NiceStateView.STATE_CONTENT
            this@AdapterStateView.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(
            positionStart: Int,
            itemCount: Int,
            payload: Any?
        ) {
            typeState = NiceStateView.STATE_CONTENT
            this@AdapterStateView.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            typeState = NiceStateView.STATE_CONTENT
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            typeState = NiceStateView.STATE_CONTENT
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            typeState = NiceStateView.STATE_CONTENT
            this@AdapterStateView.notifyItemRangeChanged(fromPosition, toPosition, itemCount)
        }
    }

    private fun getStateView(type: String): IStateView {
        return builder.stateViewMap[type]
            ?: throw NullPointerException("do you have register this type? type is $type")
    }

    override fun showLoading(): IStateView {
        typeState = NiceStateView.STATE_LOADING
        notifyStateVH()
        return getStateView(typeState)
    }

    override fun showEmpty(): IStateView {
        typeState = NiceStateView.STATE_EMPTY
        notifyStateVH()
        return getStateView(typeState)
    }

    override fun showError(): IStateView {
        typeState = NiceStateView.STATE_ERROR
        notifyStateVH()
        return getStateView(typeState)
    }

    override fun showRetry(): IStateView {
        typeState = NiceStateView.STATE_RETRY
        notifyStateVH()
        return getStateView(typeState)
    }

    override fun showContent() {
        typeState = NiceStateView.STATE_CONTENT
        notifyStateVH()
    }

    override fun <T> showCustom(clazz: Class<T>): IStateView {
        typeState = clazz.name
        notifyStateVH()
        return getStateView(typeState)
    }

    private fun notifyStateVH() {
        notifyDataSetChanged()
    }

    private val isTypeState: Boolean
        get() = builder.stateViewMap.containsKey(typeState)
//        get() = typeState == TYPE_STATE_LOADING
//                || typeState == TYPE_STATE_EMPTY
//                || typeState == TYPE_STATE_ERROR
//                || typeState == TYPE_STATE_RETRY

//    private fun setClick(
//        itemView: View,
//        stateViewHolder: StateViewHolder
//    ) {
//        for (i in 0 until mViewClicks.size()) {
//            val viewId = mViewClicks.keyAt(i)
//            val clickListener = mViewClicks.valueAt(i)
//            val child = itemView.findViewById<View>(viewId)
//            child?.setOnClickListener(clickListener)
//        }
//    }

//    fun setOnItemViewClickListener(
//        viewId: Int,
//        listener: View.OnClickListener
//    ): StateAdapter {
//        mViewClicks.put(viewId, listener)
//        return this
//    }

    companion object {

//        const val TYPE_STATE_NORMAL = -111
//        const val TYPE_STATE_LOADING = 111
//        const val TYPE_STATE_EMPTY = 222
//        const val TYPE_STATE_ERROR = 333
//        const val TYPE_STATE_RETRY = 444
//        const val TYPE_STATE_CONTENT = 555

    }

}