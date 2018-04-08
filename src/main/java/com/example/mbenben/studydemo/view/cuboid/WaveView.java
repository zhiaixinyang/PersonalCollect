package com.example.mbenben.studydemo.view.cuboid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 水波纹特效
 *
 * @author Created by MDove on 2018/4/8.
 */
public class WaveView extends View {
    private int mInitialAlpha = 255;
    private float mInitialRadius;   // 初始波纹半径
    private float mMaxRadius;   // 最大波纹半径
    private long mDuration = 2000; // 一个波纹从创建到消失的持续时间
    private int mSpeed = 500;   // 波纹的创建速度，每500ms创建一个
    private float mMaxRadiusRate = 0.85f;
    private boolean mMaxRadiusSet;
    private boolean mIsRunning;
    private long mLastCreateTime;
    private List<Circle> mCircleList = new ArrayList<>();
    private Handler mHandler;

    private Runnable mCreateCircleRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                newCircle();
                mHandler.postDelayed(mCreateCircleRunnable, mSpeed);
            }
        }
    };

    private Interpolator mInterpolator;

    private Paint mPaint;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        mHandler = new Handler(Looper.getMainLooper());
        mInterpolator = new LinearInterpolator();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (!mMaxRadiusSet) {
            mMaxRadius = Math.min(w, h) * mMaxRadiusRate / 2.0f;
        }
    }

    /**
     * 开始
     */
    public void start() {
        if (!mIsRunning) {
            mIsRunning = true;
            mHandler.post(mCreateCircleRunnable);
        }
    }

    /**
     * 缓慢停止
     */
    public void stop() {
        if (mIsRunning) {
            mIsRunning = false;
            mHandler.removeCallbacks(mCreateCircleRunnable);
        }
    }

    /**
     * 立即停止
     */
    public void stopImmediately() {
        if (mIsRunning) {
            mIsRunning = false;
            mCircleList.clear();
            mHandler.removeCallbacks(mCreateCircleRunnable);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Iterator<Circle> iterator = mCircleList.iterator();
        while (iterator.hasNext()) {
            Circle circle = iterator.next();
            float radius = circle.getCurrentRadius();
            if (System.currentTimeMillis() - circle.mCreateTime < mDuration) {
                mPaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
            } else {
                iterator.remove();
            }
        }
        if (mCircleList.size() > 0) {
            postInvalidateDelayed(10);
        }
    }

    public WaveView setMaxRadiusRate(float maxRadiusRate) {
        mMaxRadiusRate = maxRadiusRate;
        return this;
    }

    public WaveView setColor(int color) {
        mPaint.setColor(color);
        return this;
    }

    public WaveView setStyle(Paint.Style style) {
        mPaint.setStyle(style);
        return this;
    }

    public WaveView setInitialRadius(float radius) {
        mInitialRadius = radius;
        return this;
    }

    public WaveView setInitialAlpha(int alpha) {
        mInitialAlpha = alpha;
        return this;
    }

    public WaveView setDuration(long duration) {
        mDuration = duration;
        return this;
    }

    public WaveView setMaxRadius(float maxRadius) {
        mMaxRadius = maxRadius;
        mMaxRadiusSet = true;
        return this;
    }

    public WaveView setSpeed(int speed) {
        mSpeed = speed;
        return this;
    }

    public WaveView setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        if (mInterpolator == null) {
            mInterpolator = new LinearInterpolator();
        }
        return this;
    }

    private void newCircle() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastCreateTime < mSpeed) {
            return;
        }
        Circle circle = new Circle();
        mCircleList.add(circle);
        invalidate();
        mLastCreateTime = currentTime;
    }

    private class Circle {
        private long mCreateTime;

        Circle() {
            mCreateTime = System.currentTimeMillis();
        }

        int getAlpha() {
            float percent = (getCurrentRadius() - mInitialRadius) / (mMaxRadius - mInitialRadius);
            int alpha = (int) (mInitialAlpha - mInterpolator.getInterpolation(percent) * mInitialAlpha);
            return alpha;
        }

        float getCurrentRadius() {
            float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / mDuration;
            return mInitialRadius + mInterpolator.getInterpolation(percent) * (mMaxRadius - mInitialRadius);
        }
    }
}
