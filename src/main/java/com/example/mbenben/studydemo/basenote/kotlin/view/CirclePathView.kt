package com.github.shadowsocks.widget

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.example.mbenben.studydemo.R
import com.example.mbenben.studydemo.layout.recyclerview.sticky.util.DensityUtil

/**
 * @author Created by MDove on 2018/4/11.
 */
class CirclePathView @JvmOverloads constructor(context: Context?, attributeSet: AttributeSet? = null, defStyleInt: Int = 0)
    : View(context, attributeSet, defStyleInt) {

    @ColorInt
    private var bgColor: Int = Color.TRANSPARENT
    @ColorInt
    private var progressColor: Int = Color.BLUE

    private var strokeWidth: Float = 10f
    private var startAngle = -90f
    private var sweepAngle = 0f

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0

    private val pRectF = RectF()

    private var bgPaint: Paint
    private var progressPaint: Paint

    private var incrementAngele = 0f
    private var animatorSet: AnimatorSet? = null
    private var startAnimator = false

    //默认的动画时间
    private val DEFAULT_DURATION = 1000L
    private val DEFAULT_MAX_ANGLE = -300f
    private val DEFAULT_MIN_ANGLE = -19f

    init {
        val typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CirclePathView)
        progressColor = typedArray.getColor(R.styleable.CirclePathView_cpv_progress_color, Color.BLUE)
        bgColor = typedArray.getColor(R.styleable.CirclePathView_cpv_bg_color, Color.TRANSPARENT)
        strokeWidth = typedArray.getFloat(R.styleable.CirclePathView_cpv_stroke_width, DensityUtil.dip2px(context, 40f).toFloat())
        typedArray.recycle()

        progressPaint = Paint()
        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.STROKE
        progressPaint.strokeWidth = strokeWidth
        progressPaint.isAntiAlias = true

        bgPaint = Paint()
        bgPaint.color = bgColor
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        viewWidth = measuredWidth
        viewHeight = measuredHeight
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        viewWidth = w
        viewHeight = h

        pRectF.set(strokeWidth + paddingLeft, strokeWidth + paddingTop,
                width.toFloat() - strokeWidth - paddingRight.toFloat(), height.toFloat() - strokeWidth - paddingBottom.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val radius: Float = Math.min(viewWidth.toFloat(), viewHeight.toFloat()) / 2
            canvas.drawCircle(viewWidth.toFloat() / 2, viewHeight.toFloat() / 2, radius - strokeWidth, bgPaint)

            canvas.drawArc(pRectF, startAngle + incrementAngele, sweepAngle, false, progressPaint)
        }

        if (startAnimator) {
            if (animatorSet == null || !(animatorSet?.isRunning as Boolean)) {
                startAnimation()
            }
        }
    }

    fun start() {
        startAngle = -90f
        sweepAngle = 0f
        incrementAngele = 0f

        if (animatorSet != null && animatorSet?.isRunning as Boolean) {
            animatorSet?.cancel()
        }

        startAnimator = true
        invalidate()
    }

    fun stop() {
        startAnimator = false
        if (animatorSet != null && animatorSet?.isRunning as Boolean) {
            animatorSet?.cancel()
        }
    }

    private fun startAnimation() {
        if (animatorSet != null && animatorSet?.isRunning as Boolean) {
            animatorSet?.cancel()
        }

        animatorSet = AnimatorSet()

        val set = cycleAnimator()
        animatorSet?.play(set)
        animatorSet?.addListener(object : AnimatorListener {
            private var isCancel = false
            override fun onAnimationStart(animation: Animator) = Unit
            override fun onAnimationRepeat(animation: Animator) = Unit
            override fun onAnimationEnd(animation: Animator) {
                if (!isCancel) {
                    startAnimation()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
                isCancel = true
            }
        })

        animatorSet?.start()
    }


    /**
     * 循环的动画
     */
    private fun cycleAnimator(): AnimatorSet {

        //从小圈到大圈
        val holdAnimator1 = ValueAnimator.ofFloat(incrementAngele + DEFAULT_MIN_ANGLE, incrementAngele + 100f)
        holdAnimator1.addUpdateListener { animation -> incrementAngele = animation.animatedValue as Float }
        holdAnimator1.duration = DEFAULT_DURATION
        holdAnimator1.interpolator = LinearInterpolator()

        val expandAnimator = ValueAnimator.ofFloat(DEFAULT_MIN_ANGLE, DEFAULT_MAX_ANGLE)
        expandAnimator.addUpdateListener { animation ->
            sweepAngle = animation.animatedValue as Float
            incrementAngele -= sweepAngle
            invalidate()
        }
        expandAnimator.duration = DEFAULT_DURATION
        expandAnimator.interpolator = DecelerateInterpolator(2f)


        //从大圈到小圈
        val holdAnimator = ValueAnimator.ofFloat(startAngle, startAngle + 100f)
        holdAnimator.addUpdateListener { animation -> startAngle = animation.animatedValue as Float }

        holdAnimator.duration = DEFAULT_DURATION
        holdAnimator.interpolator = LinearInterpolator()

        val narrowAnimator = ValueAnimator.ofFloat(DEFAULT_MAX_ANGLE, DEFAULT_MIN_ANGLE)
        narrowAnimator.addUpdateListener { animation ->
            sweepAngle = animation.animatedValue as Float
            invalidate()
        }

        narrowAnimator.duration = DEFAULT_DURATION
        narrowAnimator.interpolator = DecelerateInterpolator(2f)

        val set = AnimatorSet()
        set.play(holdAnimator1).with(expandAnimator)
        set.play(holdAnimator).with(narrowAnimator).after(holdAnimator1)
        return set
    }
}