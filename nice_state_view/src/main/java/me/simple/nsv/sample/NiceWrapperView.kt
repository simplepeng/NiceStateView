package me.simple.nsv.sample

import me.simple.nsv.IStateView

internal class NiceWrapperView(private val layoutRes: Int) : IStateView() {

    override fun setLayoutRes() = layoutRes

}