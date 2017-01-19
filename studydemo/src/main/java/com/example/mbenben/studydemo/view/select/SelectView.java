package com.example.mbenben.studydemo.view.select;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2017/1/3.
 */
public class SelectView extends View {
    private int textColor, lineColor, textSize;
    private int mWidth, mHeight, mStartWidth;
    private Paint mPaint;
    private Rect bigBound, smallBound;
    private int xDown, xMove, xUp;
    private int xScroll;
    private VelocityTracker velocityTracker;
    private getNumberListener listener;


    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //获取我们自定义的样式属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SelectView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.SelectView_selectlineColor:
                    // 默认颜色设置为黑色
                    lineColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SelectView_selecttextColor:
                    textColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.SelectView_selecttextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    textSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }


        array.recycle();
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        bigBound = new Rect();
        smallBound = new Rect();
    }


    public void setmStartWidth(int mStartWidth) {
        this.mStartWidth = mStartWidth;
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
        mStartWidth = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(lineColor);
        //画背景
        canvas.drawLine(0, 0, mWidth, 0, mPaint);
        canvas.drawLine(0, mHeight, mWidth, mHeight, mPaint);

        //画数字
        for (int i = 0; i < 1000; i++) {
            if (i % 5 == 0) {
                mPaint.setColor(textColor);
                canvas.drawLine(mStartWidth, 0, mStartWidth, getHeight() / 3, mPaint);
                mPaint.setTextSize(textSize);
                mPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), bigBound);
                canvas.drawText(String.valueOf(i), mStartWidth - bigBound.width() / 2, getHeight() / 2 + bigBound.height() * 3 / 4, mPaint);
            } else {
                mPaint.setColor(lineColor);
                mPaint.setTextSize(textSize - 15);
                canvas.drawLine(mStartWidth, 0, mStartWidth, getHeight() / 5, mPaint);
                mPaint.getTextBounds(String.valueOf(i), 0, String.valueOf(i).length(), smallBound);
                canvas.drawText(String.valueOf(i), mStartWidth - smallBound.width() / 2, getHeight() / 2 + smallBound.height() * 3 / 4, mPaint);
            }
            mStartWidth += mWidth / 10;
        }
        //画中间刻度线
        mPaint.setColor(textColor);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, getHeight() / 3, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        int x = (int) event.getX();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                xDown = x;
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                mStartWidth = xScroll + (xMove - xDown);
                invalidate();
                int numberScroll = (int) Math.round(Double.valueOf(mStartWidth) / Double.valueOf(mWidth / 10));
                listener.getNumber(Math.abs(numberScroll - 5));
                break;
            case MotionEvent.ACTION_UP:
                xUp = x;
                xScroll = xScroll + (xUp - xDown);
                //处理快速滑动
                velocityTracker.computeCurrentVelocity(1000);
                int scrollX = (int) velocityTracker.getXVelocity();
                xScroll = xScroll + scrollX;
                ValueAnimator walkAnimator = ValueAnimator.ofInt(mStartWidth, xScroll);
                walkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mStartWidth = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                walkAnimator.setDuration(500);
                walkAnimator.start();
                walkAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        //处理惯性滑动
                        int endX = xScroll % (mWidth / 10);
                        if (Math.abs(endX) < mWidth / 20) {
                            xScroll = xScroll - endX;
                            mStartWidth = xScroll;
                            invalidate();
                        } else {
                            xScroll = xScroll + (Math.abs(endX) - mWidth / 10);
                            mStartWidth = xScroll;
                            invalidate();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                int number = (int) Math.round(Double.valueOf(xScroll) / Double.valueOf(mWidth / 10));
                listener.getNumber(Math.abs(number - 5));
                break;
        }
        return true;
    }


    public void setListener(getNumberListener listener) {
        this.listener = listener;
    }


    public interface getNumberListener {
        void getNumber(int number);
    }

}
