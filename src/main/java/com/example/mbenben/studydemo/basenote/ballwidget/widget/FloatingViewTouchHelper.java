package com.example.mbenben.studydemo.basenote.ballwidget.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.example.mbenben.studydemo.utils.StatusBarUtils;

/**
 * @author MDove on 2018/4/19.
 */
public class FloatingViewTouchHelper implements View.OnTouchListener {

    private final View mView;
    private final BoundsChecker mBoundsChecker;
    private final WindowManager mWindowManager;

    private final Context mCxt;
    private int mScreenWidth;
    private int mScreenHeight;

    private AbsStickyEdgeAnimator mStickyEdgeAnimator;
    private AbsFlingAnimator mFlingAnimator;
    private TouchCallback mCallback;

    private Float mLastRawX, mLastRawY;
    private boolean mTouchCanceled;
    private GestureListener mGestureListener;
    private GestureDetector mGestureDetector;
    private boolean mFlingEnable;
    private float mViewWidth;
    private float mViewHeight;
    private boolean mStaticMode = false;//静态模式下 不能拖动，不响应 edge动画
    private int mOriention = Configuration.ORIENTATION_PORTRAIT;

    public FloatingViewTouchHelper(@NonNull View view, @NonNull BoundsChecker boundsChecker) {
        this(view, boundsChecker, null, null);
    }

    public FloatingViewTouchHelper(@NonNull View view, @NonNull BoundsChecker boundsChecker, @Nullable AbsStickyEdgeAnimator stickyEdgeAnimator, @Nullable AbsFlingAnimator flingAnimator) {
        mView = view;
        mBoundsChecker = boundsChecker;
        mCxt = view.getContext();

        mWindowManager = (WindowManager) mCxt.getSystemService(Context.WINDOW_SERVICE);
        mView.setOnTouchListener(this);
        mScreenWidth = mCxt.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mCxt.getResources().getDisplayMetrics().heightPixels - StatusBarUtils.getStatusBarHeight(mCxt);

        if (stickyEdgeAnimator == null) {
            mStickyEdgeAnimator = new DefaultStickyEdgeAnimator(null);
        } else {
            mStickyEdgeAnimator = stickyEdgeAnimator;
        }
        if (flingAnimator == null) {
            mFlingAnimator = new DefaultFlingAnimator(null);
        } else {
            mFlingAnimator = flingAnimator;
        }

        mGestureListener = new GestureListener();
        mGestureDetector = new GestureDetector(mCxt, mGestureListener);
        mGestureDetector.setIsLongpressEnabled(false);
        mOriention = mCxt.getResources().getConfiguration().orientation;
    }

    public FloatingViewTouchHelper longPressEnable(boolean enable) {
        mGestureDetector.setIsLongpressEnabled(enable);
        return this;
    }

    public FloatingViewTouchHelper screenWidth(int screenWidth) {
        mScreenWidth = screenWidth;
        return this;
    }

    public FloatingViewTouchHelper screenHeight(int screenHeight) {
        mScreenHeight = screenHeight;
        return this;
    }

    public FloatingViewTouchHelper FloatingViewSize(float width, float height) {
        mViewWidth = width;
        mViewHeight = height;
        return this;
    }

    public FloatingViewTouchHelper setTouchCallback(TouchCallback touchCallback) {
        mCallback = touchCallback;
        mStickyEdgeAnimator.setTouchCallback(mCallback);
        mFlingAnimator.setTouchCallback(mCallback);
        return this;
    }

    public FloatingViewTouchHelper alwaysStickyEdge(boolean alwaysStickyEdge) {
        mStickyEdgeAnimator.setAlwaysStickyEdge(alwaysStickyEdge);
        return this;
    }

    public FloatingViewTouchHelper flingEnable(boolean flingEnable) {
        mFlingEnable = flingEnable;
        return this;
    }

    public void setIsStaticMode(boolean staticMode) {
        mStaticMode = staticMode;
    }

    /**
     * @param oldOrientation {@link Configuration#ORIENTATION_PORTRAIT}
     *                       or {@link Configuration#ORIENTATION_LANDSCAPE}
     * @param newOrientation same to oldOrientation
     */
    public void onConfigurationChanged(int oldOrientation, int newOrientation) {
        if (mOriention != newOrientation) {
            mOriention = newOrientation;
        }
    }

    @Override
    public final boolean onTouch(View v, MotionEvent event) {
        boolean consumed = (!mTouchCanceled || event.getAction() == MotionEvent.ACTION_UP) && mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mTouchCanceled = false;
            mGestureListener.onDown(event);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!mTouchCanceled) {
                mGestureListener.onUpEvent(event);
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (!mTouchCanceled) {
                mGestureListener.onMove(event);
            }
        } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            mGestureListener.onTouchOutsideEvent(event);
            mTouchCanceled = false;
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            mTouchCanceled = true;
        }
        return consumed;
    }

    private class DefaultStickyEdgeAnimator extends AbsStickyEdgeAnimator {

        private static final long DEFAULT_ANIM_DURATION = 400;
        private final PropertyValuesHolder dxHolder;
        private final PropertyValuesHolder dyHolder;
        private final ValueAnimator edgeAnimator;
        private final Interpolator interpolator;
        private WindowManager.LayoutParams params;

        public DefaultStickyEdgeAnimator(TouchCallback touchCallback) {
            super(touchCallback);
            interpolator = new OvershootInterpolator();
            dxHolder = PropertyValuesHolder.ofInt("x", 0, 0);
            dyHolder = PropertyValuesHolder.ofInt("y", 0, 0);
            dxHolder.setEvaluator(new IntEvaluator());
            dyHolder.setEvaluator(new IntEvaluator());
            edgeAnimator = ValueAnimator.ofPropertyValuesHolder(dxHolder, dyHolder);
            edgeAnimator.setInterpolator(interpolator);
            edgeAnimator.setDuration(DEFAULT_ANIM_DURATION);
            edgeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int x = (int) animation.getAnimatedValue("x");
                    int y = (int) animation.getAnimatedValue("y");
                    onAnimMovingUpdate(x - params.x, y - params.y);
                    params.x = x;
                    params.y = y;
                    try {
                        mWindowManager.updateViewLayout(mView, params);
                    } catch (IllegalArgumentException e) {
                        // view not attached to window
                        animation.cancel();
                    }
                }
            });
            edgeAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    onAnimMovingCancel();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    onAnimMovingEnd();
                }
            });
        }

        @Override
        void animate() {
            if (edgeAnimator.isRunning()) {
                return;
            }
            if (mView == null) {
                return;
            }
            if (dxHolder == null || dyHolder == null) {
                return;
            }
            if (params == null) {
                params = (WindowManager.LayoutParams) mView.getLayoutParams();
            }
            if (mOriention == Configuration.ORIENTATION_LANDSCAPE) {
                // 横屏情况下固定在屏幕左边
                int x = 0;
                int y = (int) (mScreenHeight / 2 - mViewWidth / 2);
                dxHolder.setIntValues(params.x, x);
                dyHolder.setIntValues(params.y, y);
                edgeAnimator.start();
                return;
            }
            boolean update = false;
            params = (WindowManager.LayoutParams) mView.getLayoutParams();
            BoundsChecker boundsChecker = mBoundsChecker;
            int x = 0;
            int y = params.y;
            int viewWidth = mView.getWidth();
            if (viewWidth == 0) {
                viewWidth = (int) mViewWidth;
            }
            int viewHeight = mView.getHeight();
            if (viewHeight == 0) {
                viewHeight = (int) mViewHeight;
            }
            if (mAlwaysStickyEdge) {//to edge
                float cx = params.x + viewWidth / 2f;
                float cy = params.y + viewHeight / 2f;

                if (cx < mScreenWidth / 2f) {
                    x = (int) boundsChecker.stickyLeftSide(mScreenWidth);
                } else {
                    x = (int) boundsChecker.stickyRightSide(mScreenWidth) - viewWidth;
                }

                int top = (int) boundsChecker.stickyTopSide(mScreenHeight);
                int bottom = (int) boundsChecker.stickyBottomSide(mScreenHeight);
                if (params.y + viewHeight > bottom || params.y < top) {
                    if (cy < mScreenHeight / 2f) {
                        y = top;
                    } else {
                        y = bottom - viewHeight;
                    }
                }
                update = true;
            } else {
                //check edge
                int left = (int) boundsChecker.stickyLeftSide(mScreenWidth);
                int right = (int) boundsChecker.stickyRightSide(mScreenWidth);
                if (params.x < left) {
                    x = left;
                    update = true;
                } else if (params.x + viewWidth > right) {
                    x = right - viewWidth;
                    update = true;
                }
                int top = (int) boundsChecker.stickyTopSide(mScreenHeight);
                int bottom = (int) boundsChecker.stickyBottomSide(mScreenHeight);
                if (params.y < top) {
                    y = top;
                    update = true;
                } else if (params.y + viewHeight > bottom) {
                    y = bottom - viewHeight;
                    update = true;
                }
            }

            if (update) {
                dxHolder.setIntValues(params.x, x);
                dyHolder.setIntValues(params.y, y);
                edgeAnimator.start();
            }
        }

        @Override
        boolean isAnimating() {
            return edgeAnimator.isRunning();
        }

        @Override
        public void onAnimMovingCancel() {
            //ignore
        }
    }

    public void adjustFirstPosition() {
        if (mStickyEdgeAnimator != null) {
            mStickyEdgeAnimator.animate();
        }
    }

    private class DefaultFlingAnimator extends AbsFlingAnimator {

        private static final long DEFAULT_ANIM_DURATION = 200;
        private final ValueAnimator flingGestureAnimator;
        private final PropertyValuesHolder dxHolder;
        private final PropertyValuesHolder dyHolder;
        private final Interpolator interpolator;
        private WindowManager.LayoutParams params;

        public DefaultFlingAnimator(TouchCallback touchCallback) {
            super(touchCallback);
            interpolator = new DecelerateInterpolator();
            dxHolder = PropertyValuesHolder.ofFloat("x", 0, 0);
            dyHolder = PropertyValuesHolder.ofFloat("y", 0, 0);
            dxHolder.setEvaluator(new FloatEvaluator());
            dyHolder.setEvaluator(new FloatEvaluator());
            flingGestureAnimator = ValueAnimator.ofPropertyValuesHolder(dxHolder, dyHolder);
            flingGestureAnimator.setInterpolator(interpolator);
            flingGestureAnimator.setDuration(DEFAULT_ANIM_DURATION);
            flingGestureAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float newX = (float) animation.getAnimatedValue("x");
                    float newY = (float) animation.getAnimatedValue("y");
                    onAnimMovingUpdate(newX - params.x, newY - params.y);
                    params.x = (int) newX;
                    params.y = (int) newY;
                    try {
                        mWindowManager.updateViewLayout(mView, params);
                    } catch (IllegalArgumentException e) {
                        animation.cancel();
                    }
                }
            });

            flingGestureAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onAnimMovingEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    onAnimMovingCancel();
                }
            });
        }

        @Override
        void animate(float velocityX, float velocityY) {
            if (isAnimating()) {
                return;
            }
            params = (WindowManager.LayoutParams) mView.getLayoutParams();
            float dx = velocityX / 1000f * DEFAULT_ANIM_DURATION;
            float dy = velocityY / 1000f * DEFAULT_ANIM_DURATION;
            final float newX, newY;
            BoundsChecker boundsChecker = mBoundsChecker;
            //顶部和底部不用考虑状态栏和底部导航栏，fling动画结束后还会校准
            if (dx + params.x > mScreenWidth / 2f) {
                //右边显示一半
                newX = boundsChecker.stickyRightSide(mScreenWidth) - Math.min(mView.getWidth(), mView.getHeight()) / 2f;
            } else if (dx + params.x < 0) {
                //左边显示一半
                newX = boundsChecker.stickyLeftSide(mScreenWidth) - Math.min(mView.getWidth(), mView.getHeight()) / 2f;
            } else {
                newX = dx + params.x;
            }
            if (dy + params.y > mScreenHeight) {
                //底部显示一半
                newY = boundsChecker.stickyBottomSide(mScreenHeight) - Math.min(mView.getWidth(), mView.getHeight()) / 2;
            } else if (dy + params.y < 0) {
                //顶部显示一半
                newY = boundsChecker.stickyTopSide(mScreenHeight) - Math.min(mView.getWidth(), mView.getHeight()) / 2;
            } else {
                newY = params.y + dy;
            }
            dxHolder.setFloatValues(params.x, newX);
            dyHolder.setFloatValues(params.y, newY);
            flingGestureAnimator.start();
        }

        @Override
        boolean isAnimating() {
            return flingGestureAnimator.isRunning();
        }

        @Override
        public void onAnimMovingEnd() {
            mStickyEdgeAnimator.animate();
        }

        @Override
        public void onAnimMovingCancel() {
            mStickyEdgeAnimator.animate();
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private int prevX, prevY;
        private float velX, velY;
        private long lastEventTime;

        @Override
        public boolean onDown(MotionEvent e) {
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mView.getLayoutParams();
            prevX = params.x;
            prevY = params.y;
            boolean result = !mStickyEdgeAnimator.isAnimating();
            if (result) {
                if (mCallback != null) {
                    mCallback.onTouched(e.getX(), e.getY());
                }
            }
            return result;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mCallback != null) {
                mCallback.onClick(e.getX(), e.getY());
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mStaticMode) {
                return true;
            }
            float diffX = e2.getRawX() - e1.getRawX();
            float diffY = e2.getRawY() - e1.getRawY();
            float l = prevX + diffX;
            float t = prevY + diffY;
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mView.getLayoutParams();
            params.x = (int) l;
            params.y = (int) t;
            try {
                mWindowManager.updateViewLayout(mView, params);
            } catch (IllegalArgumentException e) {
                // view not attached to window
            }
            if (mCallback != null) {
                mCallback.onMoved(distanceX, distanceY);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (mCallback != null) {
                mCallback.onLongClick(e.getX(), e.getY());
            }
            long downTime = SystemClock.uptimeMillis();
            long eventTime = SystemClock.uptimeMillis() + 100;
            float x = 0.0f;
            float y = 0.0f;
            int metaState = 0;
            MotionEvent event = MotionEvent.obtain(
                    downTime,
                    eventTime,
                    MotionEvent.ACTION_CANCEL,
                    x,
                    y,
                    metaState
            );
            mView.dispatchTouchEvent(event);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mFlingEnable && !mStaticMode) {
                mFlingAnimator.animate(velX, velY);
            }
            return true;
        }

        private void onMove(MotionEvent e2) {
            if (mLastRawX != null && mLastRawY != null) {
                long diff = e2.getEventTime() - lastEventTime;
                float dt = diff == 0 ? 0 : 1000f / diff;
                float newVelX = (e2.getRawX() - mLastRawX) * dt;
                float newVelY = (e2.getRawY() - mLastRawY) * dt;
                velX = smooth(velX, newVelX, 0.2f);
                velY = smooth(velY, newVelY, 0.2f);
            }
            mLastRawX = e2.getRawX();
            mLastRawY = e2.getRawY();
            lastEventTime = e2.getEventTime();
        }

        private void onUpEvent(MotionEvent e) {
            if (mCallback != null) {
                mCallback.onReleased(e.getX(), e.getY());
            }
            mLastRawX = null;
            mLastRawY = null;
            lastEventTime = 0;
            velX = velY = 0;
            if (!mFlingAnimator.isAnimating() && !mStaticMode) {
                mStickyEdgeAnimator.animate();
            }
        }

        private void onTouchOutsideEvent(MotionEvent e) {
            if (mCallback != null) {
                mCallback.onTouchOutside();
            }
        }
    }

    private float smooth(float prevValue, float newValue, float a) {
        return a * newValue + (1 - a) * prevValue;
    }

    interface TouchCallback {

        void onClick(float x, float y);


        void onLongClick(float x, float y);

        void onTouchOutside();

        /**
         * 手指 触摸到 view，这时候还没有移动事件
         */
        void onTouched(float x, float y);

        /**
         * move 事件
         */
        void onMoved(float diffX, float diffY);

        /**
         * 手指松开
         */
        void onReleased(float x, float y);

        /**
         * Called when sticky edge animation completed.
         */
        void onAnimationCompleted();
    }

    static class SimpleTouchCallback implements TouchCallback {
        @Override
        public void onClick(float x, float y) {

        }

        @Override
        public void onLongClick(float x, float y) {

        }

        @Override
        public void onTouchOutside() {

        }

        @Override
        public void onTouched(float x, float y) {

        }

        @Override
        public void onMoved(float diffX, float diffY) {

        }

        @Override
        public void onReleased(float x, float y) {

        }

        @Override
        public void onAnimationCompleted() {

        }
    }

    interface BoundsChecker {

        /**
         * 获取最左边界
         *
         * @param screenWidth 屏幕宽
         * @return
         */
        float stickyLeftSide(float screenWidth);

        float stickyRightSide(float screenWidth);

        float stickyTopSide(float screenHeight);

        float stickyBottomSide(float screenHeight);
    }

    abstract static class BoundsCheckerWithOffset implements BoundsChecker {
        private int offsetX, offsetY;

        public BoundsCheckerWithOffset(int offsetX, int offsetY) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }

        @Override
        public final float stickyLeftSide(float screenWidth) {
            return stickyLeftSideImpl(screenWidth) + offsetX;
        }

        @Override
        public final float stickyRightSide(float screenWidth) {
            return stickyRightSideImpl(screenWidth) - offsetX;
        }

        @Override
        public final float stickyTopSide(float screenHeight) {
            return stickyTopSideImpl(screenHeight) + offsetY;
        }

        @Override
        public final float stickyBottomSide(float screenHeight) {
            return stickyBottomSideImpl(screenHeight) - offsetY;
        }

        protected abstract float stickyLeftSideImpl(float screenWidth);

        protected abstract float stickyRightSideImpl(float screenWidth);

        protected abstract float stickyTopSideImpl(float screenHeight);

        protected abstract float stickyBottomSideImpl(float screenHeight);
    }

    interface OnMovingAnimatorUpdater {
        /**
         * 当 view 位置更新时，务必要调用该方法更新
         *
         * @param diffX
         * @param diffY
         */
        void onAnimMovingUpdate(float diffX, float diffY);

        void onAnimMovingEnd();

        void onAnimMovingCancel();
    }

    abstract class AbsStickyEdgeAnimator implements OnMovingAnimatorUpdater {

        private TouchCallback mCallback;
        protected boolean mAlwaysStickyEdge = false;

        public AbsStickyEdgeAnimator(TouchCallback touchCallback) {
            mCallback = touchCallback;
        }

        public void setAlwaysStickyEdge(boolean alwaysStickyEdge) {
            this.mAlwaysStickyEdge = alwaysStickyEdge;
        }

        public void setTouchCallback(TouchCallback callback) {
            mCallback = callback;
        }

        abstract void animate();

        abstract boolean isAnimating();

        @Override
        public void onAnimMovingUpdate(float diffX, float diffY) {
            if (mCallback != null) {
                mCallback.onMoved(diffX, diffY);
            }
        }

        @Override
        public void onAnimMovingEnd() {
            if (mCallback != null) {
                mCallback.onAnimationCompleted();
            }
        }
    }

    abstract class AbsFlingAnimator implements OnMovingAnimatorUpdater {
        private TouchCallback mCallback;

        public AbsFlingAnimator(TouchCallback touchCallback) {
            mCallback = touchCallback;
        }

        public void setTouchCallback(TouchCallback callback) {
            mCallback = callback;
        }

        abstract void animate(float velocityX, float velocityY);

        abstract boolean isAnimating();

        @Override
        public void onAnimMovingUpdate(float diffX, float diffY) {
            if (mCallback != null) {
                mCallback.onMoved(diffX, diffY);
            }
        }
    }
}
