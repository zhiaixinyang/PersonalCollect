package com.example.mbenben.studydemo.basenote.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import blog.com.blogstudy.kotlin.note.inteface.BaseKotlinInterface
import com.example.mbenben.studydemo.R
import com.example.mbenben.studydemo.base.BaseActivity
import com.example.mbenben.studydemo.basenote.kotlin.note.KotlinNote
import com.example.mbenben.studydemo.utils.log.LogUtils
import com.github.shadowsocks.widget.CirclePathView

/**
 * Created by MDove on 2018/4/8.
 */
class KotlinActivity : BaseActivity() {
    lateinit var cpv: CirclePathView
    override fun isNeedCustomLayout(): Boolean = false

    companion object {
        val ACTION_EXTRA = "action_extra"

        fun start(context: Context, title: String) {
            val intent = Intent(context, KotlinActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            intent.putExtra(ACTION_EXTRA, title)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = intent.getStringExtra(ACTION_EXTRA)
        setContentView(R.layout.activity_kotlin_note)

        //Kotlin初始化接口
        val kotlin = KotlinNote()
        kotlin.addListener(object : BaseKotlinInterface<String> {
            override fun create(): String = kotlin.name
        })

        //监听事件
        findViewById(R.id.tv_title).setOnClickListener {
        }

        cpv = findViewById(R.id.cpv) as CirclePathView
        cpv.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        cpv.stop()
    }
}
