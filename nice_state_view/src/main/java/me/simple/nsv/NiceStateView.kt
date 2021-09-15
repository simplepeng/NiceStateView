package me.simple.nsv

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import me.simple.nsv.sample.NiceWrapperView
import me.simple.nsv.view.LayoutStateView
import java.lang.IllegalArgumentException

interface NiceStateView {

    fun showLoading(): IStateView

    fun showEmpty(): IStateView

    fun showError(): IStateView

    fun showRetry(): IStateView

    fun showContent()

    fun showCustom(key: String): IStateView

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

        fun registerCustom(key: String, stateView: IStateView): Builder {
            if (stateViewMap.containsKey(key)) {
                throw IllegalArgumentException("don't use $key")
            }

            stateViewMap[key] = stateView
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

        fun registerCustom(key: String, layoutRes: Int): Builder {
            val stateView = NiceWrapperView(layoutRes)
            stateViewMap[key] = stateView
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

        fun getStateView(key: String): IStateView {
            return stateViewMap[key] ?: throw NullPointerException("do you have register $key ?")
        }
    }
}