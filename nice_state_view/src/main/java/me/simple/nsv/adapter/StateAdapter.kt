package me.simple.nsv.adapter

import android.database.Observable
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.*

class StateAdapter<VH : ViewHolder> private constructor(adapter: RecyclerView.Adapter<VH>) :
    RecyclerView.Adapter<ViewHolder>() {

    private val realAdapter: RecyclerView.Adapter<VH> = adapter
    private var typeState = TYPE_STATE_NORMAL
//    private val mStateViewMap: SparseArray<StateView> = SparseArray<StateView>()
//    private val mViewClicks = SparseArray<View.OnClickListener>()

    override fun getItemCount(): Int {
        return if (isTypeState) {
            1
        } else realAdapter.itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && isTypeState) typeState else realAdapter.getItemViewType(
            position
        )
    }

    override fun getItemId(position: Int): Long {
        return if (position == 0 && isTypeState) super.getItemId(position) else realAdapter.getItemId(
            position
        )
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
//        if (isTypeState) {
//            val stateView: StateView = getStateView(mTypeState)
//            val inflater =
//                LayoutInflater.from(viewGroup.context)
//            val stateItemView: View =
//                inflater.inflate(stateView.setLayoutRes(), viewGroup, false)
//            val stateViewHolder = StateViewHolder(stateItemView)
//            stateView.onCreate(stateItemView)
//            setClick(stateItemView, stateViewHolder)
//            return stateViewHolder
//        }
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
//        if (holder is StateViewHolder) {
//            val stateViewHolder = holder
//            stateViewHolder.setState(mTypeState)
//            getStateView(mTypeState).onAttachedToWindow(stateViewHolder)
//            return
//        }
        realAdapter.onViewAttachedToWindow(holder as VH)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        if (holder is StateViewHolder) {
            val stateViewHolder = holder
            val typeSate = stateViewHolder.typeSate
//            getStateView(typeSate).onDetachedFromWindow(stateViewHolder)
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
            val gm = layoutManager
            gm.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = getItemViewType(position)
                    return if (isTypeState) gm.spanCount else 1
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
            typeState = TYPE_STATE_CONTENT
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            typeState = TYPE_STATE_CONTENT
            this@StateAdapter.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(
            positionStart: Int,
            itemCount: Int,
            payload: Any?
        ) {
            typeState = TYPE_STATE_CONTENT
            this@StateAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            typeState = TYPE_STATE_CONTENT
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            typeState = TYPE_STATE_CONTENT
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            typeState = TYPE_STATE_CONTENT
            this@StateAdapter.notifyItemRangeChanged(fromPosition, toPosition, itemCount)
        }
    }

    /**
     * 注册TYPE
     */
//    fun register(stateView: StateView): StateAdapter {
//        if (stateView is StateEmptyView) {
//            mStateViewMap.put(TYPE_STATE_EMPTY, stateView)
//        } else if (stateView is StateLoadingView) {
//            mStateViewMap.put(TYPE_STATE_LOADING, stateView)
//        } else if (stateView is StateErrorView) {
//            mStateViewMap.put(TYPE_STATE_ERROR, stateView)
//        } else if (stateView is StateRetryView) {
//            mStateViewMap.put(TYPE_STATE_RETRY, stateView)
//        }
//        return this
//    }

//    private fun getStateView(type: Int): StateView {
//        return mStateViewMap[type]
//            ?: throw NullPointerException(
//                "do you have register this type? type is" + getTypeName(
//                    mTypeState
//                )
//            )
//    }

    private fun getTypeName(type: Int): String {
        var typeName = ""
        when (type) {
            TYPE_STATE_EMPTY -> typeName = "EMPTY"
            TYPE_STATE_LOADING -> typeName = "LOADING"
            TYPE_STATE_ERROR -> typeName = "ERROR"
            TYPE_STATE_RETRY -> typeName = "RETRY"
        }
        return typeName
    }

    fun showLoading() {
        typeState = TYPE_STATE_LOADING
        notifyStateVH()
    }

    fun showEmpty() {
        typeState = TYPE_STATE_EMPTY
        notifyStateVH()
    }

    fun showError() {
        typeState = TYPE_STATE_ERROR
        notifyStateVH()
    }

    fun showRetry() {
        typeState = TYPE_STATE_RETRY
        notifyStateVH()
    }

    fun showContent() {
        typeState = TYPE_STATE_CONTENT
        notifyDataSetChanged()
    }

    private fun notifyStateVH() {
        notifyDataSetChanged()
    }

    private val isTypeState: Boolean
        private get() = typeState == TYPE_STATE_LOADING || typeState == TYPE_STATE_EMPTY || typeState == TYPE_STATE_ERROR || typeState == TYPE_STATE_RETRY

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

        const val TYPE_STATE_NORMAL = -111
        const val TYPE_STATE_LOADING = 111
        const val TYPE_STATE_EMPTY = 222
        const val TYPE_STATE_ERROR = 333
        const val TYPE_STATE_RETRY = 444
        const val TYPE_STATE_CONTENT = 555

        fun <VH : ViewHolder> wrap(adapter: RecyclerView.Adapter<VH>): StateAdapter<VH> {
            return StateAdapter(adapter)
        }
    }

}