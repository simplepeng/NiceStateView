package demo.simple.nsv

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_layout.*
import kotlinx.android.synthetic.main.activity_main.btn_content
import kotlinx.android.synthetic.main.activity_main.btn_custom
import kotlinx.android.synthetic.main.activity_main.btn_empty
import kotlinx.android.synthetic.main.activity_main.btn_error
import kotlinx.android.synthetic.main.activity_main.btn_loading
import kotlinx.android.synthetic.main.activity_main.btn_retry

class LayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        btn_loading.setOnClickListener {
            stateLayout.showLoading().setOnViewClickListener(R.id.pb_loading) {
                toast("showLoading")
            }
        }

        btn_empty.setOnClickListener {
//            niceStateView.showEmpty()

            stateLayout.showEmpty()
                .setText(R.id.nsvTvEmpty, "这里空空如也~")
                .setImage(R.id.nsvIvEmpty, R.drawable.nsv_empty)
        }

        btn_error.setOnClickListener {
            stateLayout.showError()
        }

        btn_retry.setOnClickListener {
            stateLayout.showRetry().setOnViewClickListener(R.id.nsvIvRetry) {
                stateLayout.showLoading()
                view_content.postDelayed({
                    stateLayout.showContent()
                }, 2000)
            }.setOnViewClickListener(R.id.view_retry) {
                toast("view_retry click")
            }
        }

        btn_content.setOnClickListener {
            stateLayout.showContent()
        }

        btn_custom.setOnClickListener {
            stateLayout.showCustom("login")
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}