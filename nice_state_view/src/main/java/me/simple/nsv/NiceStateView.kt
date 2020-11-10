package me.simple.nsv

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import me.simple.nsv.adapter.AdapterStateView
import me.simple.nsv.sample.NiceWrapperView
import me.simple.nsv.view.LayoutStateView

interface NiceStateView {

    fun showLoading(): IStateView

    fun showEmpty(): IStateView

    fun showError(): IStateView

    fun showRetry(): IStateView

    fun showContent()

    fun <T> showCustom(clazz: Class<T>): IStateView

    companion object {

        const val STATE_EMPTY = "state_empty"
        const val STATE_LOADING = "state_loading"
        const val STATE_ERROR = "state_error"
        const val STATE_RETRY = "state_retry"
        const val STATE_CONTENT = "state_content"
        const val STATE_NORMAL = "state_normal"

        /**
         *
         */
        fun newBuilder(): Builder {
            return Builder()
        }
    }

    class Builder {

        val stateViewMap = mutableMapOf<String, IStateView>()

        /**
         * 注册IStateView，可以回调onAttach，onDetach函数
         */
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

        /**
         * 直接注册layoutRes
         */
        fun registerEmpty(layoutRes: Int): Builder {
            stateViewMap[STATE_EMPTY] = NiceWrapperView(layoutRes)
            return this
        }

        fun registerError(layoutRes: Int): Builder {
            stateViewMap[STATE_ERROR] = NiceWrapperView(layoutRes)
            return this
        }

        fun registerRetry(layoutRes: Int): Builder {
            stateViewMap[STATE_RETRY] = NiceWrapperView(layoutRes)
            return this
        }

        fun registerLoading(layoutRes: Int): Builder {
            stateViewMap[STATE_LOADING] = NiceWrapperView(layoutRes)
            return this
        }

        fun registerCustom(layoutRes: Int): Builder {
            val stateView = NiceWrapperView(layoutRes)
            stateViewMap[stateView.javaClass.name] = stateView
            return this
        }

        /**
         * 包装content View
         */
        fun wrapContent(view: View?): LayoutStateView {
            if (view == null) {
                throw NullPointerException("content view can not be null")
            }
            return LayoutStateView(this, view)
        }

        /**
         * 包装Activity的content View
         */
        fun wrapContent(activity: Activity): LayoutStateView {
            val contentView =
                    (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
            return wrapContent(contentView)
        }

        /**
         * Fragment的View没有parent
         */
//        fun wrapContent(fragment: Fragment): LayoutStateView {
//            return wrapContent(fragment.view)
//        }

        fun <VH : RecyclerView.ViewHolder> wrapContent(adapter: RecyclerView.Adapter<VH>): AdapterStateView<VH> {
            return AdapterStateView(this, adapter)
        }
    }
}