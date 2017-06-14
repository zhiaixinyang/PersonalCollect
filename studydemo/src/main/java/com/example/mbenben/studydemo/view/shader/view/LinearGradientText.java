package com.example.mbenben.studydemo.view.shader.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 霓虹文字
 */

public class LinearGradientText extends TextView {
    public LinearGradientText(Context context) {
        this(context, null);
    }

    public LinearGradientText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private Paint mPaint;
    private Shader mShader;
    private Matrix matrix;
    private int mVx;

    private void init() {
        mPaint = getPaint();  //获取控件本身的paint
        matrix = new Matrix();

    }

    private void initAnimtor(int width) {
        ValueAnimator animator = ValueAnimator.ofInt(0, width * 3);  //我们设置value的值为0-getMeasureWidth的3 倍
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mVx = (Integer) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);   //重新播放
        animator.setRepeatCount(-1);                    //无限循环
        animator.setDuration(2000);
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //我们设置一个LgShader，从左边[距离文字显示2倍宽度的距离]
        mShader = new LinearGradient(-2 * w, 0, -w, 0, new int[]{getCurrentTextColor(), Color.RED, Color.YELLOW, Color.BLUE, getCurrentTextColor(),}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);

        initAnimtor(w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        matrix.preTranslate(mVx, 0);
        mShader.setLocalMatrix(matrix);


    }
}
