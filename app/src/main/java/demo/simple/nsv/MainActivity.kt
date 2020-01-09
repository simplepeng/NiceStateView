package demo.simple.nsv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.nsv.NiceStateView
import me.simple.nsv.sample.NiceSampleEmptyView
import me.simple.nsv.sample.NiceSampleLoadingView

class MainActivity : AppCompatActivity() {

    private val niceStateView: NiceStateView by lazy {
        NiceStateView.builder()
            .registerLoading(NiceSampleLoadingView())
            .registerEmpty(NiceSampleEmptyView())
            .wrapContent(view_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        niceStateView.showLoading()

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
            niceStateView.showContent()
        }
    }
}
