package demo.simple.nsv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.drakeet.multitype.MultiTypeAdapter
import me.simple.nsv.NiceStateView
import me.simple.nsv.sample.NiceSampleEmptyView
import me.simple.nsv.sample.NiceSampleErrorView
import me.simple.nsv.sample.NiceSampleLoadingView
import me.simple.nsv.sample.NiceSampleRetryView

class RecyclerViewActivity : AppCompatActivity() {

    private val mItems = mutableListOf<Any>()
    private val mMultiTypeAdapter = MultiTypeAdapter(mItems)

//    private val niceStateView: NiceStateView by lazy {
//        NiceStateView.newBuilder()
//            .registerLoading(NiceSampleLoadingView())
//            .registerEmpty(NiceSampleEmptyView())
//            .registerError(NiceSampleErrorView())
//            .registerRetry(NiceSampleRetryView())
//            .wrapContent(mMultiTypeAdapter)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
    }
}