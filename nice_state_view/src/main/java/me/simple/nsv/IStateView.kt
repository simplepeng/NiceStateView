package me.simple.nsv

import android.graphics.Bitmap
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

abstract class IStateView {

    var view: View? = null

    /**
     * set layout res id
     */
    @LayoutRes
    abstract fun setLayoutRes(): Int

    /**
     * attach view to state layout
     */
    open fun onAttach(view: View) {
        this.view = view
    }

    /**
     * detach view from state layout
     * can release something animation：like gif，lottie
     */
    open fun onDetach(view: View) {

    }

    /**
     * set child view click listener
     */
    fun setOnViewClickListener(id: Int, onClick: (v: View) -> Unit): IStateView {
        this.view?.findViewById<View>(id)?.setOnClickListener(onClick)
        return this
    }

    fun setText(id: Int, @StringRes resId: Int): IStateView {
        setText(id, view?.context?.getString(resId))
        return this
    }

    fun setText(id: Int, text: String?): IStateView {
        this.view?.findViewById<TextView>(id)?.text = text
        return this
    }

    fun setTextColor(id: Int, @ColorInt color: Int): IStateView {
        this.view?.findViewById<TextView>(id)?.setTextColor(color)
        return this
    }

    fun setTextSize(id: Int, size: Float): IStateView {
        this.view?.findViewById<TextView>(id)?.textSize = size
        return this
    }

    fun setImage(id: Int, @DrawableRes resId: Int): IStateView {
        this.view?.findViewById<ImageView>(id)?.setImageResource(resId)
        return this
    }

    fun setImage(id: Int, bm: Bitmap): IStateView {
        this.view?.findViewById<ImageView>(id)?.setImageBitmap(bm)
        return this
    }
}