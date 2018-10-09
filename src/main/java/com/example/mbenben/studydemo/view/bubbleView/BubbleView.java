package com.example.mbenben.studydemo.view.bubbleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 原项目blog：https://juejin.im/post/5b950e8df265da0a91452740
 *
 * 作用：演示气泡功能
 * 作者：GcsSloop
 */
public class BubbleView extends View {

    private int mBubbleMaxRadius = 30;          // 气泡最大半径 px
    private int mBubbleMinRadius = 5;           // 气泡最小半径 px
    private int mBubbleMaxSize = 30;            // 气泡数量
    private int mBubbleRefreshTime = 20;        // 刷新间隔
    private int mBubbleMaxSpeedY = 5;           // 气泡速度
    private int mBubbleAlpha = 128;             // 气泡画笔

    private float mBottleWidth;                 // 瓶子宽度
    private float mBottleHeight;                // 瓶子高度
    private float mBottleRadius;                // 瓶子底部转角半径
    private float mBottleBorder;                // 瓶子边缘宽度
    private float mBottleCapRadius;             // 瓶子顶部转角半径
    private float mWaterHeight;                 // 水的高度

    private RectF mContentRectF;                // 实际可用内容区域
    private RectF mWaterRectF;                  // 水占用的区域

    private Path mBottlePath;                   // 外部瓶子
    private Path mWaterPath;                    // 水

    private Paint mBottlePaint;                 // 瓶子画笔
    private Paint mWaterPaint;                  // 水画笔
    private Paint mBubblePaint;                 // 气泡画笔

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWaterRectF = new RectF();

        mBottleWidth = dp2px(130);
        mBottleHeight = dp2px(260);
        mBottleBorder = dp2px(8);
        mBottleRadius = dp2px(15);
        mBottleCapRadius = dp2px(5);

        mWaterHeight = dp2px(240);

        mBottlePath = new Path();
        mWaterPath = new Path();

        mBottlePaint = new Paint();
        mBottlePaint.setAntiAlias(true);
        mBottlePaint.setStyle(Paint.Style.STROKE);
        mBottlePaint.setStrokeCap(Paint.Cap.ROUND);
        mBottlePaint.setColor(Color.WHITE);
        mBottlePaint.setStrokeWidth(mBottleBorder);

        mWaterPaint = new Paint();
        mWaterPaint.setAntiAlias(true);

        initBubble();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mContentRectF = new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom());

        float bl = mContentRectF.centerX() - mBottleWidth / 2;
        float bt = mContentRectF.centerY() - mBottleHeight / 2;
        float br = mContentRectF.centerX() + mBottleWidth / 2;
        float bb = mContentRectF.centerY() + mBottleHeight / 2;
        mBottlePath.reset();
        mBottlePath.moveTo(bl - mBottleCapRadius, bt - mBottleCapRadius);
        mBottlePath.quadTo(bl, bt - mBottleCapRadius, bl, bt);
        mBottlePath.lineTo(bl, bb - mBottleRadius);
        mBottlePath.quadTo(bl, bb, bl + mBottleRadius, bb);
        mBottlePath.lineTo(br - mBottleRadius, bb);
        mBottlePath.quadTo(br, bb, br, bb - mBottleRadius);
        mBottlePath.lineTo(br, bt);
        mBottlePath.quadTo(br, bt - mBottleCapRadius, br + mBottleCapRadius, bt - mBottleCapRadius);


        mWaterPath.reset();
        mWaterPath.moveTo(bl, bb - mWaterHeight);
        mWaterPath.lineTo(bl, bb - mBottleRadius);
        mWaterPath.quadTo(bl, bb, bl + mBottleRadius, bb);
        mWaterPath.lineTo(br - mBottleRadius, bb);
        mWaterPath.quadTo(br, bb, br, bb - mBottleRadius);
        mWaterPath.lineTo(br, bb - mWaterHeight);
        mWaterPath.close();

        mWaterRectF.set(bl, bb - mWaterHeight, br, bb);

        LinearGradient gradient = new LinearGradient(mWaterRectF.centerX(), mWaterRectF.top,
                mWaterRectF.centerX(), mWaterRectF.bottom, 0xFF4286f4, 0xFF373B44, Shader.TileMode.CLAMP);
        mWaterPaint.setShader(gradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mWaterPath, mWaterPaint);
        canvas.drawPath(mBottlePath, mBottlePaint);
        drawBubble(canvas);
    }

    //--- 气泡效果 ---------------------------------------------------------------------------------

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startBubbleSync();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopBubbleSync();
    }


    private class Bubble {
        int radius;     // 气泡半径
        float speedY;   // 上升速度
        float speedX;   // 平移速度
        float x;        // 气泡x坐标
        float y;        // 气泡y坐标
    }

    private ArrayList<Bubble> mBubbles = new ArrayList<>();

    private Random random = new Random();
    private Thread mBubbleThread;

    // 初始化气泡
    private void initBubble() {
        mBubblePaint = new Paint();
        mBubblePaint.setColor(Color.WHITE);
        mBubblePaint.setAlpha(mBubbleAlpha);
    }

    // 开始气泡线程
    private void startBubbleSync() {
        stopBubbleSync();
        mBubbleThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(mBubbleRefreshTime);
                        tryCreateBubble();
                        refreshBubbles();
                        postInvalidate();
                    } catch (InterruptedException e) {
                        System.out.println("Bubble线程结束");
                        break;
                    }
                }
            }
        };
        mBubbleThread.start();
    }

    // 停止气泡线程
    private void stopBubbleSync() {
        if (null == mBubbleThread) return;
        mBubbleThread.interrupt();
        mBubbleThread = null;
    }

    // 绘制气泡
    private void drawBubble(Canvas canvas) {
        List<Bubble> list = new ArrayList<>(mBubbles);
        for (Bubble bubble : list) {
            if (null == bubble) continue;
            canvas.drawCircle(bubble.x, bubble.y,
                    bubble.radius, mBubblePaint);
        }
    }

    // 尝试创建气泡
    private void tryCreateBubble() {
        if (null == mContentRectF) return;
        if (mBubbles.size() >= mBubbleMaxSize) {
            return;
        }
        if (random.nextFloat() < 0.95) {
            return;
        }
        Bubble bubble = new Bubble();
        int radius = random.nextInt(mBubbleMaxRadius - mBubbleMinRadius);
        radius += mBubbleMinRadius;
        float speedY = random.nextFloat() * mBubbleMaxSpeedY;
        while (speedY < 1) {
            speedY = random.nextFloat() * mBubbleMaxSpeedY;
        }
        bubble.radius = radius;
        bubble.speedY = speedY;
        bubble.x = mWaterRectF.centerX();
        bubble.y = mWaterRectF.bottom - radius - mBottleBorder / 2;
        float speedX = random.nextFloat() - 0.5f;
        while (speedX == 0) {
            speedX = random.nextFloat() - 0.5f;
        }
        bubble.speedX = speedX * 2;
        mBubbles.add(bubble);
    }

    // 刷新气泡位置，对于超出区域的气泡进行移除
    private void refreshBubbles() {
        List<Bubble> list = new ArrayList<>(mBubbles);
        for (Bubble bubble : list) {
            if (bubble.y - bubble.speedY <= mWaterRectF.top + bubble.radius) {
                mBubbles.remove(bubble);
            } else {
                int i = mBubbles.indexOf(bubble);
                if (bubble.x + bubble.speedX <= mWaterRectF.left + bubble.radius + mBottleBorder / 2) {
                    bubble.x = mWaterRectF.left + bubble.radius + mBottleBorder / 2;
                } else if (bubble.x + bubble.speedX >= mWaterRectF.right - bubble.radius - mBottleBorder / 2) {
                    bubble.x = mWaterRectF.right - bubble.radius - mBottleBorder / 2;
                } else {
                    bubble.x = bubble.x + bubble.speedX;
                }
                bubble.y = bubble.y - bubble.speedY;
                mBubbles.set(i, bubble);
            }
        }
    }

    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
