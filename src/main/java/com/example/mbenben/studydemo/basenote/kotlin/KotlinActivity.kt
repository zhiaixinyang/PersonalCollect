package com.example.mbenben.studydemo.basenote.kotlin

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.widget.RelativeLayout
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
    lateinit var layoutMain: RelativeLayout
    @ColorInt
    private val redGradientStartColor = Color.parseColor("#FFB245")
    @ColorInt
    private val redGradientEndColor = Color.parseColor("#F4422E")
    @ColorInt
    private val yellowGradientStartColor = Color.parseColor("#FFF245")
    @ColorInt
    private val yellowGradientEndColor = Color.parseColor("#FF9E00")
    @ColorInt
    private val greenGradientStartColor = Color.parseColor("#45FF97")
    @ColorInt
    private val greenGradientEndColor = Color.parseColor("#2ED1F4")
    @ColorInt
    private val blueGradientStartColor = Color.parseColor("#00E1FF")
    @ColorInt
    private val blueGradientEndColor = Color.parseColor("#0070FF")

    private val argbEvaluator = ArgbEvaluator()
    private var animatorSetForConnect: AnimatorSet? = null

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

        layoutMain = findViewById(R.id.layout_main) as RelativeLayout
        animBgColorFromRedToBlue()

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

    private fun animBgColorFromRedToBlue() {
        val animator1 = ValueAnimator.ofInt(1, 100)
        animator1.addUpdateListener { animation ->
            val fraction = animation.animatedFraction

            val background = layoutMain.background
            if (background != null && background is GradientDrawable) {
                background.colors = calculateGradientColor(fraction, redGradientStartColor, yellowGradientStartColor,
                        redGradientEndColor, yellowGradientEndColor)
            }
        }

        val animator2 = ValueAnimator.ofInt(1, 100)
        animator2.addUpdateListener { animation ->
            val fraction = animation.animatedFraction

            val background = layoutMain.background
            if (background != null && background is GradientDrawable) {
                background.colors = calculateGradientColor(fraction, yellowGradientStartColor, greenGradientStartColor,
                        yellowGradientEndColor, greenGradientEndColor)
            }
        }

        val animator3 = ValueAnimator.ofInt(1, 100)
        animator3.addUpdateListener { animation ->
            val fraction = animation.animatedFraction

            val background = layoutMain.background
            if (background != null && background is GradientDrawable) {
                background.colors = calculateGradientColor(fraction, greenGradientStartColor, blueGradientStartColor,
                        greenGradientEndColor, blueGradientEndColor)
            }
        }

        animatorSetForConnect = AnimatorSet()
        animatorSetForConnect?.let {
            it.playSequentially(animator1, animator2, animator3)
            it.duration = 834 //3个动画合计差不多2500ms
            it.start()
        }
    }

    private fun calculateGradientColor(fraction: Float, fromStartColor: Int, toStartColor: Int, fromEndColor: Int, toEndColor: Int): IntArray {
        val startColor = argbEvaluator.evaluate(fraction, fromStartColor, toStartColor) as Int
        val endColor = argbEvaluator.evaluate(fraction, fromEndColor, toEndColor) as Int
        return intArrayOf(startColor, endColor)
    }
}
