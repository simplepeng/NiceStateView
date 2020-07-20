package demo.simple.nsv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.activity_recyclerview.*
import me.simple.nsv.NiceStateView
import me.simple.nsv.sample.NiceEmptyView
import me.simple.nsv.sample.NiceErrorView
import me.simple.nsv.sample.NiceLoadingView
import me.simple.nsv.sample.NiceRetryView

class RecyclerViewActivity : AppCompatActivity() {

    private val mItems = mutableListOf<String>()

    //    private val mAdapter = MultiTypeAdapter(mItems)
    private val mAdapter = RealAdapter(mItems)

    private val niceStateView by lazy {
        NiceStateView.newBuilder()
            .registerLoading(NiceLoadingView())
            .registerEmpty(NiceEmptyView())
            .registerError(NiceErrorView())
            .registerRetry(NiceRetryView())
            .registerCustom(CustomLoginView())
            .wrapContent(mAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
//            layoutManager = GridLayoutManager(this@RecyclerViewActivity, 2)
            adapter = niceStateView.stateAdapter
        }

        btn_loading.setOnClickListener {
            niceStateView.showLoading()
        }

        btn_empty.setOnClickListener {
            niceStateView.showEmpty()
        }

        btn_error.setOnClickListener {
            niceStateView.showError()
        }

        btn_retry.setOnClickListener {
            niceStateView.showRetry()
        }

        btn_content.setOnClickListener {
            initData()
//            niceStateView.showContent()
        }

        btn_custom.setOnClickListener {
            niceStateView.showCustom(CustomLoginView::class.java)
        }

//        btn_loading.performClick()
    }

    private fun initData() {
        mItems.clear()
        for (i in 0..11) {
            mItems.add(i.toString())
        }
//        mAdapter.notifyDataSetChanged()
        niceStateView.showContent()
    }
}

internal class RealAdapter(private val items: MutableList<String>) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tvItem.text = items[holder.adapterPosition]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

internal class VH(itemView: View) : ViewHolder(itemView) {
    var tvItem: TextView = itemView.findViewById(R.id.tv_item)
}
