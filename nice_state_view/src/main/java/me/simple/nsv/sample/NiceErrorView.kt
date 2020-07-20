package me.simple.nsv.sample

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import me.simple.nsv.IStateView
import me.simple.nsv.R

class NiceErrorView(
    @DrawableRes private val imageId: Int = R.drawable.nsv_error,
    @StringRes private val textId: Int = R.string.nsv_text_error
) : IStateView() {

    override fun setLayoutRes() = R.layout.sample_error_view

    override fun onAttach(view: View) {
        super.onAttach(view)
        view.findViewById<ImageView>(R.id.nsvIvError).setImageResource(imageId)
        view.findViewById<TextView>(R.id.nsvTvError).setText(textId)
    }

}