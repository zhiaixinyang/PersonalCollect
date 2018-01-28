package com.example.mbenben.studydemo.view.weibosport;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MBENBEN on 2017/1/3.
 */

public class WeiBoSportView extends View {


    private String text;
    private int textColor;
    private int textSize;
    private int outCircleColor;
    private int inCircleColor;
    //绘制时控制文本绘制的范围
    private Paint mPaint, circlePaint;

    private Rect mBound;
    private RectF circleRect;
    private float mCurrentAngle;
    private float mStartSweepValue;
    private int mCurrentPercent, mTargetPercent;

    public WeiBoSportView(Context context) {
        this(context, null);
    }

    public WeiBoSportView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeiBoSportView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //获取我们自定义的样式属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WeiBoSportView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.WeiBoSportView_weibotitleColor:
                    // 默认颜色设置为黑色
                    textColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.WeiBoSportView_weibotitleSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    textSize = array.getDimensionPixelSize(attr, DpUtils.sp2px(20));
                    break;
                case R.styleable.WeiBoSportView_weibooutCircleColor:
                    // 默认颜色设置为黑色
                    outCircleColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.WeiBoSportView_weiboinCircleColor:
                    // 默认颜色设置为黑色
                    inCircleColor = array.getColor(attr, Color.BLACK);
                    break;
            }

        }
        array.recycle();
        init(context);
    }

    //初始化
    private void init(Context context) {
        //创建画笔
        mPaint = new Paint();
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        //设置圆弧的宽度
        circlePaint.setStrokeWidth(DpUtils.dp2px(10));
        //设置圆弧的颜色
        circlePaint.setColor(inCircleColor);
        //设置是否抗锯齿
        mPaint.setAntiAlias(true);
        //圆环开始角度 -90° 正北方向
        mStartSweepValue = -90;
        //当前角度
        mCurrentAngle = 0;
        //当前百分比
        mCurrentPercent = 0;
        mBound = new Rect();
        //设置外圆的颜色
        mPaint.setColor(outCircleColor);
        //设置外圆为空心
        mPaint.setStyle(Paint.Style.STROKE);

        //设置字体颜色
        mPaint.setColor(textColor);
        //设置字体大小
        mPaint.setTextSize(textSize);
        mPaint.setStrokeWidth(DpUtils.dp2px(2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画外圆
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaint);
        text = String.valueOf(mCurrentPercent);
        /**
         * 此方法的官方解释：
         * 返回边界（由调用者分配）包含所有字符的最小矩形，隐含起点为（0,0）。
         *      @param text要测量并返回其边界的字符串
         *      @param start要测量的字符串中第一个字符的索引
         *      @param end 1超过字符串度量中的最后一个字符
         *      @param bounds返回所有文本的合并边界。 必须由调用者分配。
         * 说白了，就是设置个方法能够获取到text的width和height
         * 这个值被设置再mBound里。没有这句话我们不好确定text这个字符串的宽度
         * 这样我们在drawText的时候就可以根据mBound对text进行居中设置
         */
        mPaint.getTextBounds(text, 0, text.length(), mBound);
        //绘制字体
        canvas.drawText(text, getWidth() / 2 - mBound.width() / 2, getWidth() / 2 + mBound.height() / 2, mPaint);

        //设置字体大小
        mPaint.setTextSize(textSize/2 );
        //绘制字体
        canvas.drawText("分", getWidth() * 3/ 5, getWidth() / 3, mPaint);

        mPaint.setTextSize(textSize);


        //圆弧范围,比外圆小20px
        circleRect = new RectF(20, 20, getWidth() - 20, getWidth() - 20);
        /**
         * 绘制圆弧
         * 官方解释对参数的解释：
         *      @param oval用于定义弧的形状和大小的椭圆的边界
         *      @param startAngle弧开始的起始角（以度为单位）
         *      @param sweepAngle顺时针测量的扫描角度（度）
         *      @param useCenter如果为true：就画成一个扇形；false就是弧线
         *      @param paint用于绘制弧线的油漆
         */
        canvas.drawArc(circleRect, mStartSweepValue, mCurrentAngle, false, circlePaint);
        //判断当前百分比是否小于设置目标的百分比
        if (mCurrentPercent < mTargetPercent) {
            //当前百分比+1
            mCurrentPercent += 1;
            //当前角度+360
            mCurrentAngle += 3.6;

            //每100ms重画一次
            postInvalidateDelayed(100);
        }
    }


    public void setmTargetPercent(int mTargetPercent) {
        this.mTargetPercent = mTargetPercent;
    }


    public void setNumber(int size) {
        mCurrentPercent = 0;
        mTargetPercent = size;
        mCurrentAngle = 0;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {

            //如果布局里面没有设置固定值,这里取字体的宽度
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }
        setMeasuredDimension(width, height);
    }
}