package com.example.mbenben.studydemo.basenote.lockscreen;

import android.animation.IntEvaluator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author MDove on 2017/8/10.
 */
public class HorizontalSwipeBackLayout extends FrameLayout {

    public static final String TAG = "SwipeBackLayout";
    private Context mCxt;
    protected ViewDragHelper mDragHelper;
    private View mSlideView;
    private OnSlideListener mSlideListener;
    private boolean mSlideEnable = true;
    private OnSwipeBackListener mOnSwipeBackListener;
    private IntEvaluator intEvaluator = new IntEvaluator();

    public HorizontalSwipeBackLayout(@NonNull Context context) {
        this(context, null);
    }

    public HorizontalSwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalSwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCxt = context;
        mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new IllegalStateException("SwipeBackLayout must have only one child.");
        } else {
            mSlideView = getChildAt(0);
            if (mSlideView.getBackground() == null) { // 没有背景色，则设置背景色
                mSlideView.setBackgroundColor(0xffeeeeee);
            }
        }
    }

    public void setSlideEnable(boolean slideEnable) {
        mSlideEnable = slideEnable;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mSlideEnable) {
            return super.onTouchEvent(event);
        }
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mSlideView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mSlideView.getWidth();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int leftBound = mSlideView.getPaddingLeft();
            int rightBound = mSlideView.getWidth();
            int newLeft = Math.min(Math.max(left, leftBound), rightBound);
//            if (mSlideListener != null) {
//                mSlideListener.onSlide(HorizontalSwipeBackLayout.this, child, (newLeft / (float) rightBound));
//            }
            return newLeft;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mSlideView) {
                float slideOffset = left / (float) changedView.getWidth();
                if (mSlideListener != null) {
                    mSlideListener.onSlide(HorizontalSwipeBackLayout.this, changedView, slideOffset);
                } else {
                    int alpha = intEvaluator.evaluate(slideOffset, 255, 0);
                    setBackgroundColor(Color.argb(alpha, 0, 0, 0));
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left = mSlideView.getLeft();
            int rang = mSlideView.getWidth() / 3;
            int target = mSlideView.getWidth();
            if (left > rang) {
                mDragHelper.smoothSlideViewTo(mSlideView, target, 0);
            } else {
                mDragHelper.smoothSlideViewTo(mSlideView, 0, 0);
            }
            ViewCompat.postInvalidateOnAnimation(HorizontalSwipeBackLayout.this);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_IDLE) {
                int left = mSlideView.getLeft();
                if (left >= mSlideView.getWidth()) {
                    onAnimateEnd(true);
                } else {
                    onAnimateEnd(false);
                }
            }
        }
    }

    private void onAnimateEnd(boolean back) {
        if (back) {
            if (mOnSwipeBackListener != null) {
                mOnSwipeBackListener.onFinish();
            }
        }
    }

    private String parseState(int state) {
        switch (state) {
            case ViewDragHelper.STATE_IDLE:
                return "IDLE";
            case ViewDragHelper.STATE_DRAGGING:
                return "DRAGGING";
            case ViewDragHelper.STATE_SETTLING:
                return "SETTLING";
        }
        return "NONE";
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        mSlideListener = onSlideListener;
    }

    public void setOnWipeBackListener(OnSwipeBackListener swipeBackListener) {
        mOnSwipeBackListener = swipeBackListener;
    }

    public interface OnSlideListener {
        /**
         * @param swipeBackLayout
         * @param view            The child view that was moved
         * @param slideOffset     The new offset of this sliding pane within its range, from 0-1
         */
        void onSlide(HorizontalSwipeBackLayout swipeBackLayout, View view, float slideOffset);
    }

    public interface OnSwipeBackListener {
        void onFinish();
    }

    public static class SwipeBackFinishActivityListener implements OnSwipeBackListener {
        private Activity activity;

        public SwipeBackFinishActivityListener(@NonNull Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onFinish() {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }

    }
}
