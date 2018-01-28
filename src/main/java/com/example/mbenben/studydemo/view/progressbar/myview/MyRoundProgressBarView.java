package com.example.mbenben.studydemo.view.progressbar.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MBENBEN on 2017/1/23.
 */

public class MyRoundProgressBarView extends MyHorinzontalProgressBarView {
    private int radius = DpUtils.dp2px(50);
    private int maxPaintWidth;
    //直径
    private int diameter;


    public MyRoundProgressBarView(Context context) {
        this(context, null);
    }

    public MyRoundProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRoundProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        reachHeight = (int) (unReachHeight * 2.5f);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRoundProgressBarView);
        radius = (int) typedArray.getDimension(
                R.styleable.MyRoundProgressBarView_myprogress_round_radius, radius);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        maxPaintWidth = Math.max(reachHeight, unReachHeight);
        diameter = radius * 2 + getPaddingLeft() + getPaddingRight() + maxPaintWidth;

        int width = resolveSize(diameter, widthMeasureSpec);
        int height = resolveSize(diameter, heightMeasureSpec);
        //圆形控件最终的要选择一个宽高中最小的那个
        int realWidth = Math.min(width, height);

        radius = (realWidth - getPaddingRight() - getPaddingLeft() - maxPaintWidth) / 2;

        setMeasuredDimension(realWidth, realWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = paint.measureText(text);
        float textHeight = paint.descent() - paint.ascent();

        canvas.save();
        canvas.translate(getPaddingLeft() + maxPaintWidth / 2, getPaddingTop() + maxPaintWidth / 2);
        paint.setStyle(Paint.Style.STROKE);

        //画非进度条
        paint.setColor(unReachColor);
        paint.setStrokeWidth(unReachHeight);
        canvas.drawCircle(radius, radius, radius, paint);

        //画进度条
        paint.setColor(reachColor);
        paint.setStrokeWidth(reachHeight);
        float angle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, radius * 2, radius * 2), 0, angle, false, paint);

        //画文本
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, radius - textWidth / 2, radius + textHeight/2, paint);

        canvas.restore();
    }
}
