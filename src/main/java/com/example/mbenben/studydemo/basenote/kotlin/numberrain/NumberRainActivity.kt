package com.example.mbenben.studydemo.basenote.kotlin.numberrain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.mbenben.studydemo.R
import com.example.mbenben.studydemo.base.BaseActivity

class NumberRainActivity : BaseActivity() {
    override fun isNeedCustomLayout(): Boolean = false

    companion object {
        const val ACTION_EXTRA: String = "action_extra"

        fun start(context: Context, title: String) {
            var intent = Intent(context, NumberRainActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtra(ACTION_EXTRA, title)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = intent?.getStringExtra(ACTION_EXTRA)
        setContentView(R.layout.activity_number_rain)
    }
}
