package me.simple.nsv.sample

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import me.simple.nsv.IStateView
import me.simple.nsv.R

class NiceRetryView(
    @DrawableRes private val imageId: Int = R.drawable.nsv_retry,
    @StringRes private val textId: Int = R.string.nsv_text_retry
) : IStateView() {

    override fun setLayoutRes() = R.layout.sample_retry_view

    override fun onAttach(view: View) {
        super.onAttach(view)
        view.findViewById<ImageView>(R.id.nsvIvRetry).setImageResource(imageId)
        view.findViewById<TextView>(R.id.nsvTvRetry).setText(textId)
    }
}