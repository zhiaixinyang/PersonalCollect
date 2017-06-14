package com.example.mbenben.studydemo.view.shader.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * LinearGradient介绍
 */
public class LinearGradientDemo extends View {

    public LinearGradientDemo(Context context) {
        this(context, null);
    }

    public LinearGradientDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init();
    }

    private Paint mSdPaint;
    private Paint mPaint;

    private Shader mLgShader;
    private Rect rect;

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);

        mSdPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLgShader = new LinearGradient(200, 200, 200, 300, new int[]{
                0xFFFF0000,     //红
                0xFFFF7F00,     //橙
                0xFFFFFF00,     //黄
                0xFF00FF00,     //绿
                0xFF00FFFF,     //青
                0xFF0000FF,     //蓝
                0xFF8B00FF      //紫
        }, null, Shader.TileMode.REPEAT);//position为null则均匀分配
        mSdPaint.setShader(mLgShader);
        rect = new Rect(0, 0, 300, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect, mSdPaint);

        canvas.drawLine(0,100,300,100,mPaint);  //画2条分割线，方便理解
        canvas.drawLine(0,200,300,200,mPaint);

    }
}
