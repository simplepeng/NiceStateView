package demo.simple.nsv

import android.view.View
import me.simple.nsv.IStateView

class CustomLoginView : IStateView() {
    override fun setLayoutRes(): Int {
        return R.layout.layout_login
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
    }
}