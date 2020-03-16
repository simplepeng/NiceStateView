package demo.simple.nsv

import me.simple.nsv.IStateView

class CustomLoginView : IStateView() {
    override fun setLayoutRes(): Int {
        return R.layout.layout_login
    }
}