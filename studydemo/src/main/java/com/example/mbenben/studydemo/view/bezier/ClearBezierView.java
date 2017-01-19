package com.example.mbenben.studydemo.view.bezier;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2017/1/1.
 *
 * 原作者项目GitHub：
 */

public class ClearBezierView extends View{
    private int color, mWidth, mHeight, xSize, ySize;
    private Paint paint, txtPaint;
    private Path path, arcPath;
    private int xWidth, yHeight, arcHeight;
    private AnimatorSet animSet;
    private boolean isSuccess;
    private Rect mRect;
    private String text = "火箭发射成功!!!";


    public ClearBezierView(Context context) {
        this(context, null);
    }

    public ClearBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //获取我们自定义的样式属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyBesselView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MyBesselView_besseltitleColor:
                    // 默认颜色设置为黑色
                    color = array.getColor(attr, Color.BLACK);
                    break;

            }

        }
        array.recycle();
        init();

    }


    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        path = new Path();
        arcPath = new Path();
        animSet = new AnimatorSet();
        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        mRect = new Rect();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //确定小火箭控制点的范围
        if (xWidth < xSize) {
            xWidth = xSize;
        }
        if (xWidth > mWidth * 9 / 10) {
            xWidth = mWidth * 9 / 10;
        }
        if (yHeight > mHeight * 8 / 10) {
            yHeight = mHeight * 8 / 10;
        }
        if (yHeight > mHeight * 7 / 10 && xWidth < mWidth * 4 / 10) {
            yHeight = mHeight * 7 / 10;
        }
        if (yHeight > mHeight * 7 / 10 && xWidth > mWidth * 6 / 10) {
            yHeight = mHeight * 7 / 10;
        }
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        path.reset();
        //绘制小火箭
        path.moveTo(xWidth - xSize * 1 / 2, yHeight - ySize * 3 / 5);
        path.lineTo(xWidth, yHeight - ySize);
        path.lineTo(xWidth + xSize * 1 / 2, yHeight - ySize * 3 / 5);
        path.moveTo(xWidth - xSize * 1 / 4, yHeight - ySize * 4 / 5);
        path.lineTo(xWidth - xSize * 1 / 4, yHeight);
        path.lineTo(xWidth + xSize * 1 / 4, yHeight);
        path.lineTo(xWidth + xSize * 1 / 4, yHeight - ySize * 4 / 5);
        canvas.drawPath(path, paint);

        //绘制发射台
        paint.setStrokeWidth(10);
        arcPath.reset();
        arcPath.moveTo(mWidth * 1 / 10, mHeight * 7 / 10);
        if (yHeight > mHeight * 7 / 10 && xWidth > mWidth * 4 / 10 && xWidth < mWidth * 6 / 10) {
            arcHeight = yHeight + yHeight - mHeight * 7 / 10;
        } else {
            arcHeight = mHeight * 7 / 10;
        }
        arcPath.quadTo(mWidth * 5 / 10, arcHeight, mWidth * 9 / 10, mHeight * 7 / 10);
        canvas.drawPath(arcPath, paint);

        //绘制成功后的文字
        if (isSuccess && yHeight < 0) {
            txtPaint.setTextSize(80);
            txtPaint.setColor(color);
            txtPaint.getTextBounds(text, 0, text.length(), mRect);
            canvas.drawText(text, mWidth * 1 / 2 - mRect.width() / 2, mHeight * 1 / 2 + mRect.height() * 1 / 2, txtPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isSuccess = false;
                break;
            case MotionEvent.ACTION_MOVE:
                xWidth = x;
                yHeight = y;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (yHeight > mHeight * 7 / 10 && xWidth > mWidth * 4 / 10 && xWidth < mWidth * 6 / 10) {
                    startAnim();
                }
                break;
        }
        return true;
    }


    private void startAnim() {
        //动画实现
        ValueAnimator animator = ValueAnimator.ofInt(yHeight, -ySize);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yHeight = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animSet.setDuration(1200);
        animSet.play(animator);
        animSet.start();
        isSuccess = true;

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
            //如果布局里面没有设置固定值,这里取布局的宽度的1/2
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的3/4
            height = heightSize * 1 / 2;
        }
        mWidth = width;
        mHeight = height;
        xSize = mWidth * 1 / 10;
        ySize = mHeight * 1 / 8;
        xWidth = xSize;
        yHeight = ySize;
        arcHeight = mHeight * 7 / 10;
        setMeasuredDimension(width, height);
    }

}
