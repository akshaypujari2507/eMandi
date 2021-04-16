package com.emandi.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emandi.R
import com.emandi.utils.Util
import kotlinx.android.synthetic.main.activity_order_complete.*

class OrderCompleteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_complete)

        initView()
    }

    fun initView() {
        btn_view_order.setOnClickListener() {
            finish()
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        tv_go_home.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Util.SCREEN_KEY, "order_complete_screen")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}