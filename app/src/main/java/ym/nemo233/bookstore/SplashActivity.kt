package ym.nemo233.bookstore

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import ym.nemo233.bookstore.basic.MyApp

class SplashActivity : Activity() {

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                this@SplashActivity.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler.sendEmptyMessage(3)
        Thread {
            var countdown = 2
            while (--countdown >= 0) {
                SystemClock.sleep(1000)
                mHandler.sendEmptyMessage(countdown)
            }
        }.start()
    }

    override fun onBackPressed() {
        //
    }
}
