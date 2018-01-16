package com.example.mbenben.studydemo.anim.path;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by MDove on 18/1/16.
 */

public class RightMarkView extends View {
    private static final String TAG = RightMarkView.class.getSimpleName();
    private Paint mPaint;
    private PathMeasure mPathMeasure;

    // 圆环路径
    private Path mCirclePath;

    // 截取的路径
    private Path mDstPath;

    // Path 长度
    private float mPathLength;

    // 动画估值
    private float mAnimatorValue;

    // 圆环是否已经加载过
    private boolean isHasCircle = false;

    // View 是个正方形，宽高中小的一个值,根据小的值来定位绘制
    private int mRealSize;

    // 颜色
    private int mStartColor = Color.RED;
    private int mEndColor = Color.YELLOW;

    // 画笔宽度
    private float mStrokeWidth = 8f;

    // 动画
    private ValueAnimator mValueAnimator;

    // 默认大小
    private static final int DEFAULT_SIZE = 150;

    public RightMarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        initPaint();

        // 初始化动画
        initAnimator();
        // 利用 post 获取 View 的宽高
        // post 内任务，会在第2次执行 onMeasure() 方法后执行
        post(new Runnable() {
            @Override
            public void run() {
                // 初始化圆环 Path
                initCirclePath();
                // 初始化线性渐变
                // 由于要使用mRelativeLength ，放 post 内
                initShader();
            }
        });
    }

    /**
     * 开启动画
     */
    public void start() {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mStartColor);
        mValueAnimator.start();
    }

    /**
     * 设置颜色
     */
    public void setColor(@ColorInt int startColor, @ColorInt int endColor) {
        this.mStartColor = startColor;
        this.mEndColor = endColor;
    }

    /**
     * 设置画笔粗细
     */
    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    /**
     * 线性渐变
     */
    private void initShader() {
        // 使用线性渐变
        LinearGradient shader =
                new LinearGradient(0, 0, mRealSize, mRealSize,
                        mStartColor, mEndColor, Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
    }

    /**
     * 画笔
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * 绘制路径
     */
    private void initCirclePath() {
        // 获取View 的宽
        mRealSize = getWidth();

        // 添加圆环路径
        mCirclePath = new Path();
        float x = mRealSize / 2;
        float y = mRealSize / 2;
        float radius = x / 3 * 2;
        mCirclePath.addCircle(x, y, radius, Path.Direction.CW);

        /**
         * PathMeasure
         *     PathMeasure 都相当于做了一个事情，就是把 Path “拉直”，
         *     然后给了我们一个接口(getLength)告诉我们path的总长度，
         *     然后我们想要知道具体某一点的坐标，只需要用相对的distance去取即可，
         *     这样就省去了自己用函数模拟path，然后计算获取点坐标的过程；
         */
        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mCirclePath, false);

        // 此时为圆的周长
        mPathLength = mPathMeasure.getLength();

        // Path dst 用来存储截取的Path片段
        mDstPath = new Path();
    }

    /**
     * 动画
     */
    private void initAnimator() {
        // 圆环动画
        mValueAnimator = ValueAnimator.ofFloat(0, 1);

        // 动画过程
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        // 动画时间
        mValueAnimator.setDuration(1000);

        // 插值器
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // 圆环结束后，开启对号的动画
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isHasCircle = true;
                initRightMarkPath();
                mValueAnimator.start();
            }
        });
    }

    /**
     * 关联对号 Path
     */
    private void initRightMarkPath() {
        Path path = new Path();
        // 对号起点
        float startX = (float) (0.3 * mRealSize);
        float startY = (float) (0.5 * mRealSize);
        path.moveTo(startX, startY);

        // 对号拐角点
        float cornerX = (float) (0.43 * mRealSize);
        float cornerY = (float) (0.66 * mRealSize);
        path.lineTo(cornerX, cornerY);

        // 对号终点
        float endX = (float) (0.75 * mRealSize);
        float endY = (float) (0.4 * mRealSize);
        path.lineTo(endX, endY);

        // 重新关联Path
        mPathMeasure.setPath(path, false);

        // 此时为对号 Path 的长度
        mPathLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDstPath == null) return;
        // 绘制已经记录过的圆圈 Path
        if (isHasCircle) canvas.drawPath(mCirclePath, mPaint);

        // 刷新当前截取 Path
        mDstPath.reset();

        // 避免硬件加速的Bug
        mDstPath.lineTo(0, 0);

        // 截取片段
        float stop = mPathLength * mAnimatorValue;
        mPathMeasure.getSegment(0, stop, mDstPath, true);

        // 绘制截取的片段
        canvas.drawPath(mDstPath, mPaint);
    }

    /**
     * 当View从屏幕消失时，关闭可能在执行的动画，以免可能出现内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        boolean isNeedCancel =
                (mValueAnimator != null && mValueAnimator.isRunning());
        if (isNeedCancel) {
            mValueAnimator.cancel();
        }
    }

    /**
     * 测量，强制将 View 设置为正方形
     * 当宽和高有一个为 warp_content 时，就将宽高都定为 150px
     * 当宽或者高有一个小于 150 px 时，都设置为 150px
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 取宽高中小的，强制设置成为正方形
        int realSize = Math.min(wSpecSize, hSpecSize);

        // 宽高模式是否有一个为 AT_MOST
        boolean isAnyOneAtMost =
                (wSpecMode == MeasureSpec.AT_MOST || hSpecMode == MeasureSpec.AT_MOST);

        if (isAnyOneAtMost) {
            setMeasuredDimension(DEFAULT_SIZE, DEFAULT_SIZE);
        } else {
            // 将宽高中小的值 realSize 与 150px 比较，取大的值
            realSize = Math.max(realSize, DEFAULT_SIZE);
            setMeasuredDimension(realSize, realSize);
        }
    }
}