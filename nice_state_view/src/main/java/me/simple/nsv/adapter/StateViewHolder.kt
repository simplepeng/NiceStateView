package me.simple.nsv.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StateViewHolder(itemView: View) : ViewHolder(itemView) {
    var typeSate = 0
        private set

    fun setState(typeState: Int) {
        typeSate = typeState
    }

    init {
        val params = itemView.layoutParams
        if (params is StaggeredGridLayoutManager.LayoutParams) {
            params.isFullSpan = true
        }
    }
}