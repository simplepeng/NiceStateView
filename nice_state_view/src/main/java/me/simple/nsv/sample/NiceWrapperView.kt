package me.simple.nsv.sample

import me.simple.nsv.IStateView

/**
 * 包装View，可以直接使用layout
 */
internal class NiceWrapperView(private val layoutRes: Int) : IStateView() {

    override fun setLayoutRes() = layoutRes

}