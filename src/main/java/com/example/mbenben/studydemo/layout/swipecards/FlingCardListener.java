package com.example.mbenben.studydemo.layout.swipecards;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by MBENBEN on 2017/1/2.
 *
 * 原作者项目GitHub：https://github.com/Diolor/Swipecards
 */

public class FlingCardListener implements View.OnTouchListener {

    private static final String TAG = FlingCardListener.class.getSimpleName();
    private static final int INVALID_POINTER_ID = -1;

    private final float objectX;
    private final float objectY;
    private final int objectH;
    private final int objectW;
    private final int parentWidth;
    private final FlingListener mFlingListener;
    private final Object dataObject;
    private final float halfWidth;
    private float BASE_ROTATION_DEGREES;

    private float aPosX;
    private float aPosY;
    private float aDownTouchX;
    private float aDownTouchY;

    // 活动指针是当前正在移动我们的对象的指针。
    private int mActivePointerId = INVALID_POINTER_ID;
    private View frame = null;


    private final int TOUCH_ABOVE = 0;
    private final int TOUCH_BELOW = 1;
    private int touchPosition;
    private final Object obj = new Object();
    private boolean isAnimationRunning = false;
    private float MAX_COS = (float) Math.cos(Math.toRadians(45));


    public FlingCardListener(View frame, Object itemAtPosition, FlingListener flingListener) {
        this(frame, itemAtPosition, 15f, flingListener);
    }

    public FlingCardListener(View frame, Object itemAtPosition, float rotation_degrees, FlingListener flingListener) {
        super();
        this.frame = frame;
        this.objectX = frame.getX();
        this.objectY = frame.getY();
        this.objectH = frame.getHeight();
        this.objectW = frame.getWidth();
        this.halfWidth = objectW / 2f;
        this.dataObject = itemAtPosition;
        this.parentWidth = ((ViewGroup) frame.getParent()).getWidth();
        this.BASE_ROTATION_DEGREES = rotation_degrees;
        this.mFlingListener = flingListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //确定唯一的坐标点，避免多指操作带来的问题
                mActivePointerId = event.getPointerId(0);
                float x = 0;
                float y = 0;
                boolean success = false;
                try {
                    x = event.getX(mActivePointerId);
                    y = event.getY(mActivePointerId);
                    success = true;
                } catch (IllegalArgumentException e) {
                    Log.w(TAG, "Exception in onTouch(view, event) : " + mActivePointerId, e);
                }
                if (success) {
                    //记住我们按下时的坐标
                    aDownTouchX = x;
                    aDownTouchY = y;
                    //设置View的初始坐标是传入frame的X、Y值
                    if (aPosX == 0) {
                        aPosX = frame.getX();
                    }
                    if (aPosY == 0) {
                        aPosY = frame.getY();
                    }
                    //记录按压位置状态
                    if (y < objectH / 2) {
                        touchPosition = TOUCH_ABOVE;
                    } else {
                        touchPosition = TOUCH_BELOW;
                    }
                }

                view.getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                mActivePointerId = INVALID_POINTER_ID;
                resetCardViewOnStack();
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex = (event.getAction() &
                        MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                final int pointerIndexMove = event.findPointerIndex(mActivePointerId);
                final float xMove = event.getX(pointerIndexMove);
                final float yMove = event.getY(pointerIndexMove);

                final float dx = xMove - aDownTouchX;
                final float dy = yMove - aDownTouchY;

                aPosX += dx;
                aPosY += dy;

                /**
                 * objectX= frame.getX()
                 * 此处为计算移动距离
                  */
                float distobjectX = aPosX - objectX;
                /**
                 * 如果我们仔细看Card的拖动效果可以发现，
                 * 有一个小小的旋转效果如果我们仔细看Card的拖动效果可以发现，有一个小小的旋转效果
                 *
                 * 此处是计算旋转角度自定义的公式，当拖动X轴距离和parentWidth相等时，
                 * 那么最大旋转的效果就是：BASE_ROTATION_DEGREES * 2.f
                 */
                float rotation = BASE_ROTATION_DEGREES * 2.f * distobjectX / parentWidth;
                if (touchPosition == TOUCH_BELOW) {
                    rotation = -rotation;
                }

                //设置Card的位置，旋转等
                frame.setX(aPosX);
                frame.setY(aPosY);
                frame.setRotation(rotation);
                //onScroll方法调用
                mFlingListener.onScroll(getScrollProgressPercent());
                break;

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                view.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
        }

        return true;
    }

    private float getScrollProgressPercent() {
        if (movedBeyondLeftBorder()) {
            return -1f;
        } else if (movedBeyondRightBorder()) {
            return 1f;
        } else {
            float zeroToOneValue = (aPosX + halfWidth - leftBorder()) / (rightBorder() - leftBorder());
            return zeroToOneValue * 2f - 1f;
        }
    }

    private boolean resetCardViewOnStack() {
        //如果超出左边界限
        if (movedBeyondLeftBorder()) {
            // 执行左划出效果
            onSelected(true, getExitPoint(-objectW), 100);
            mFlingListener.onScroll(-1.0f);
        } else if (movedBeyondRightBorder()) {
            // 执行右划出效果
            onSelected(false, getExitPoint(parentWidth), 100);
            mFlingListener.onScroll(1.0f);
        } else {
            float abslMoveDistance = Math.abs(aPosX - objectX);
            aPosX = 0;
            aPosY = 0;
            aDownTouchX = 0;
            aDownTouchY = 0;
            frame.animate()
                    .setDuration(200)
                    .setInterpolator(new OvershootInterpolator(1.5f))
                    .x(objectX)
                    .y(objectY)
                    .rotation(0);
            mFlingListener.onScroll(0.0f);
            if (abslMoveDistance < 4.0) {
                mFlingListener.onClick(dataObject);
            }
        }
        return false;
    }

    private boolean movedBeyondLeftBorder() {
        // leftBorder()：返回的是parentWidth / 4.f
        return aPosX + halfWidth < leftBorder();
    }

    private boolean movedBeyondRightBorder() {
        // rightBorder()：3 * parentWidth / 4.f
        return aPosX + halfWidth > rightBorder();
    }


    public float leftBorder() {
        return parentWidth / 4.f;
    }

    public float rightBorder() {
        return 3 * parentWidth / 4.f;
    }


    public void onSelected(final boolean isLeft,float exitY, long duration) {
        isAnimationRunning = true;
        float exitX;
        if (isLeft) {
            /**
             * 计算离开点的X坐标（左移动为减，右移动为加）
             * getRotationWidthOffset()：
             *         objectW / MAX_COS - objectW（计算旋转的宽度偏移量）
             *         MAX_COS=cos45
             */
            exitX = -objectW - getRotationWidthOffset();
        } else {
            exitX = parentWidth + getRotationWidthOffset();
        }

        //弹出Card的代码实现
        this.frame.animate()
                .setDuration(duration)
                .setInterpolator(new AccelerateInterpolator())
                .x(exitX)
                .y(exitY)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isLeft) {
                            //左边移出回调
                            mFlingListener.onCardExited();
                            mFlingListener.leftExit(dataObject);
                        } else {
                            //右边移出回调
                            mFlingListener.onCardExited();
                            mFlingListener.rightExit(dataObject);
                        }
                        isAnimationRunning = false;
                    }
                })
                .rotation(getExitRotation(isLeft));
    }


    /**
     * Starts a default left exit animation.
     */
    public void selectLeft() {
        if (!isAnimationRunning)
            onSelected(true, objectY, 200);
    }

    /**
     * Starts a default right exit animation.
     */
    public void selectRight() {
        if (!isAnimationRunning)
            onSelected(false, objectY, 200);
    }


    private float getExitPoint(int exitXPoint) {
        float[] x = new float[2];
        x[0] = objectX;
        x[1] = aPosX;

        float[] y = new float[2];
        y[0] = objectY;
        y[1] = aPosY;

        LinearRegression regression = new LinearRegression(x, y);

        //这个返回值是一个线性方程： y = ax+b
        return (float) regression.slope() * exitXPoint + (float) regression.intercept();
    }

    private float getExitRotation(boolean isLeft) {
        float rotation = BASE_ROTATION_DEGREES * 2.f * (parentWidth - objectX) / parentWidth;
        if (touchPosition == TOUCH_BELOW) {
            rotation = -rotation;
        }
        if (isLeft) {
            rotation = -rotation;
        }
        return rotation;
    }


    /**
     * When the object rotates it's width becomes bigger.
     * The maximum width is at 45 degrees.
     * <p/>
     * The below method calculates the width offset of the rotation.
     */
    private float getRotationWidthOffset() {
        return objectW / MAX_COS - objectW;
    }


    public void setRotationDegrees(float degrees) {
        this.BASE_ROTATION_DEGREES = degrees;
    }

    public boolean isTouching() {
        return this.mActivePointerId != INVALID_POINTER_ID;
    }

    public PointF getLastPoint() {
        return new PointF(this.aPosX, this.aPosY);
    }

    protected interface FlingListener {
        void onCardExited();

        void leftExit(Object dataObject);

        void rightExit(Object dataObject);

        void onClick(Object dataObject);

        void onScroll(float scrollProgressPercent);
    }

}