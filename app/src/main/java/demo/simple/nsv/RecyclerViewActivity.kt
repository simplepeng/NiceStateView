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
import me.drakeet.multitype.MultiTypeAdapter
import me.simple.nsv.NiceStateView
import me.simple.nsv.sample.NiceSampleEmptyView
import me.simple.nsv.sample.NiceSampleErrorView
import me.simple.nsv.sample.NiceSampleLoadingView
import me.simple.nsv.sample.NiceSampleRetryView

class RecyclerViewActivity : AppCompatActivity() {

    private val mItems = mutableListOf<String>()

    //    private val mAdapter = MultiTypeAdapter(mItems)
    private val mAdapter = RealAdapter(mItems)

    private val niceStateView: NiceStateView by lazy {
        NiceStateView.newBuilder()
            .registerLoading(NiceSampleLoadingView())
            .registerEmpty(NiceSampleEmptyView())
            .registerError(NiceSampleErrorView())
            .registerRetry(NiceSampleRetryView())
            .wrapContent(mAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
            adapter = mAdapter
        }

        for (i in 0..40) {
            mItems.add(i.toString())
        }
        mAdapter.notifyDataSetChanged()
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
