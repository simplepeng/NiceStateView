package me.simple.nsv.sample

import android.view.View
import me.simple.nsv.IStateView
import me.simple.nsv.NsvLog
import me.simple.nsv.R

class NiceSampleLoadingView : IStateView() {

    /**
     * 设置填充的布局文件
     */
    override fun setLayoutRes() = R.layout.sample_loading_view

    /**
     * 初始化一些耗时的动画资源
     */
    override fun onAttach(view: View) {
        super.onAttach(view)
        NsvLog.d("NiceSampleLoadingView ----> onAttach")
    }

    /**
     * 释放一些耗时的动画资源，避免内存泄露
     */
    override fun onDetach(view: View) {
        super.onDetach(view)
        NsvLog.d("NiceSampleLoadingView ----> onDetach")
    }

}