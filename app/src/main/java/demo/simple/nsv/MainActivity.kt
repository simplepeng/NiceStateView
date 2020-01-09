package demo.simple.nsv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.nsv.NiceStateView
import me.simple.nsv.sample.NiceSampleEmptyView
import me.simple.nsv.sample.NiceSampleErrorView
import me.simple.nsv.sample.NiceSampleLoadingView
import me.simple.nsv.sample.NiceSampleRetryView

class MainActivity : AppCompatActivity() {

    private val niceStateView: NiceStateView by lazy {
        NiceStateView.builder()
            .registerLoading(NiceSampleLoadingView())
            .registerEmpty(NiceSampleEmptyView())
            .registerError(NiceSampleErrorView())
            .registerRetry(NiceSampleRetryView())
            .wrapContent(view_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        niceStateView.showLoading().setOnViewClickListener(R.id.pb_loading) {
            toast("showLoading")
        }

        btn_loading.setOnClickListener {
            niceStateView.showLoading().setOnViewClickListener(R.id.pb_loading) {
                toast("showLoading")
            }
        }

        btn_empty.setOnClickListener {
            niceStateView.showEmpty().setOnViewClickListener(R.id.iv_empty) {
                toast("showEmpty")
            }
        }

        btn_error.setOnClickListener {
            niceStateView.showError().setOnViewClickListener(R.id.iv_error) {
                toast("showError")
            }
        }

        btn_retry.setOnClickListener {
            niceStateView.showRetry().setOnViewClickListener(R.id.iv_retry) {
                niceStateView.showLoading()
                view_content.postDelayed({
                    niceStateView.showContent()
                }, 2000)
            }.setOnViewClickListener(R.id.view_retry) {
                toast("view_retry click")
            }
        }

        btn_content.setOnClickListener {
            niceStateView.showContent()
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
