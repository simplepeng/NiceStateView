package demo.simple.nsv

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.simple.nsv.NiceStateView
import me.simple.nsv.sample.NiceEmptyView
import me.simple.nsv.sample.NiceErrorView
import me.simple.nsv.sample.NiceLoadingView
import me.simple.nsv.sample.NiceRetryView

class MainActivity : AppCompatActivity() {

//    private val niceStateView: NiceStateView by lazy {
//        NiceStateView.newBuilder()
//            .registerLoading(NiceLoadingView())
//            .registerEmpty(NiceEmptyView())
//            .registerError(NiceErrorView())
//            .registerRetry(NiceRetryView())
//            .registerCustom(CustomLoginView())
//            .wrapContent(view_content)
//    }

    private val niceStateView: NiceStateView by lazy {
        NiceStateView.newBuilder()
            .registerLoading(R.layout.sample_loading_view)
            .registerEmpty(R.layout.sample_empty_view)
            .registerError(R.layout.sample_error_view)
            .registerRetry(R.layout.sample_retry_view)
            .registerCustom("login", R.layout.layout_login)
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
//            niceStateView.showEmpty()

            niceStateView.showEmpty()
                .setText(R.id.nsvTvEmpty, "这里空空如也~")
                .setImage(R.id.nsvIvEmpty, R.drawable.nsv_empty)
        }

        btn_error.setOnClickListener {
            niceStateView.showError()
        }

        btn_retry.setOnClickListener {
            niceStateView.showRetry().setOnViewClickListener(R.id.nsvIvRetry) {
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

        btn_custom.setOnClickListener {
            niceStateView.showCustom("login")
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
