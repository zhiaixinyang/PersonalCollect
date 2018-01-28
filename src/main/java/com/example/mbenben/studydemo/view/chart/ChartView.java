package com.example.mbenben.studydemo.view.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangyangkai on 2016/12/2.
 */

public class ChartView extends View {

    private int leftColor, rightColor, lineColor;
    private Paint mPaint, mChartPaint, mShadowPaint;
    private int mWidth, mHeight, mStartWidth, mChartWidth, mSize;
    private Rect mBound;
    private List<Integer> list = new ArrayList<>();
    private Rect rect;
    private getNumberListener listener;
    private int number = 1000;


    public void setList(List<Integer> list) {
        this.list = list;
    }

    public void setListener(getNumberListener listener) {
        this.listener = listener;
    }

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们自定义的样式属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ChartView_chartleftColor:
                    // 默认颜色设置为黑色
                    leftColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ChartView_chartrightColor:
                    rightColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ChartView_chartxyColor:
                    lineColor = array.getColor(attr, Color.BLACK);
                    break;

            }
        }
        array.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBound = new Rect();
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        mShadowPaint = new Paint();
        mShadowPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
        mStartWidth = getWidth() / 13;
        mSize = getWidth() / 39;
        mChartWidth = getWidth() / 13 - mSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(lineColor);
        //画坐标轴
        canvas.drawLine(0, mHeight - 100, mWidth, mHeight - 100, mPaint);
        for (int i = 0; i < 12; i++) {
            //画刻度线
            canvas.drawLine(mStartWidth, mHeight - 100, mStartWidth, mHeight - 80, mPaint);
            //画数字
            mPaint.setTextSize(30);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.getTextBounds(String.valueOf(i + 1) + "月", 0, String.valueOf(i).length(), mBound);
            canvas.drawText(String.valueOf(i + 1) + "月", mStartWidth - mBound.width() * 1 / 2, mHeight - 60 + mBound.height() * 1 / 2, mPaint);
            mStartWidth += getWidth() / 13;


        }
        //画柱状图
        for (int i = 0; i < list.size(); i++) {
            int size = mHeight / 150;
            //偶数
            if (i % 2 == 0) {
                mChartPaint.setColor(leftColor);
            } else {
                mChartPaint.setColor(rightColor);
            }
            mChartPaint.setStyle(Paint.Style.FILL);
            //画阴影
            if (i == number * 2 || i == number * 2 + 1) {
                mShadowPaint.setColor(Color.LTGRAY);

            } else {
                mShadowPaint.setColor(Color.WHITE);
            }
            canvas.drawRect(mChartWidth, 0, mChartWidth + mSize, mHeight - 100, mShadowPaint);
            //画柱状图
            canvas.drawRect(mChartWidth, mHeight - 100 - list.get(i) * size, mChartWidth + mSize, mHeight - 100, mChartPaint);// 长方形
            mChartWidth += (i % 2 == 0) ? (getWidth() / 39) : (getWidth() / 13 - mSize);

        }


    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {

        }

    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            mSize = getWidth() / 39;
            mStartWidth = getWidth() / 13;
            mChartWidth = getWidth() / 13 - mSize - 3;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int left = 0;
        int top = 0;
        int right = mWidth / 12;
        int bottom = mHeight - 100;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < 12; i++) {
                    rect = new Rect(left, top, right, bottom);
                    left += mWidth / 12;
                    right += mWidth / 12;
                    if (rect.contains(x, y)) {
                        listener.getNumber(i, x, y);
                        number = i;
                        invalidate();
                    }
                }
                break;
        }
        return true;

    }

    public interface getNumberListener {
        void getNumber(int number, int x, int y);
    }
}
