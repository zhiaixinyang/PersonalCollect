package com.example.mbenben.studydemo.basenote.lockscreen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SwipeBackLayout extends FrameLayout {

    private static final String TAG = "SwipeBackLayout";
    
    private ViewDragHelper viewDragHelper;
    private View contentView;

    private SwipeBackListener listener;

    public interface SwipeBackListener {

        void onFinish();

    }

    public static class SwipeBackFinishActivityListener implements SwipeBackListener {

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

    public void setSwipeBackListener(SwipeBackListener listener) {
        this.listener = listener;
    }

    public SwipeBackLayout(Context context) {
        super(context);
        init();
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void viewReleased(View releasedChild) {
        if (contentView.equals(releasedChild)) {
            if (contentView.getLeft() >= getWidth() / 3) {
                viewDragHelper.settleCapturedViewAt(getWidth(), getTop());
            } else {
                viewDragHelper.settleCapturedViewAt(0, getTop());
            }
            invalidate();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 只捕获第一个子布局
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return contentView.equals(child);
            }

            /**
             * 记录子布局位置变化
             */
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//                Log.d(TAG, "onViewPositionChanged: " + contentView.equals(changedView));
                if (contentView.equals(changedView)) {
                    if (contentView.getLeft() == getWidth()) {
                        if (listener != null) {
                            listener.onFinish();
                        }
                    }
//                     设置背景色变化
                    setBackgroundColor(
                        Color.argb((int) ((getWidth() - left) / (float) getWidth() * 160), 0, 0, 0));
                }
            }

            /**
             * 当手指松开之后，处理子布局，如果当前距离大于等于1/3界面宽度，则触发关闭
             * TODO 一定得重写computeScroll()方法，不然没有效果
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                viewReleased(releasedChild);
            }

            /**
             * 处理水平拖动
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                Log.d(TAG, "clampViewPositionHorizontal: " + contentView.equals(child));
                if (contentView.equals(child)) {
                    return Math.min(Math.max(left, 0), getWidth()); // left值在0到屏幕宽度之间
                } else {
                    return super.clampViewPositionHorizontal(child, left, dx);
                }
            }

        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 保证只有一个子布局
        if (getChildCount() != 1) {
            throw new IllegalStateException("SwipeBackLayout must have only one child.");
        } else {
            contentView = getChildAt(0);
            if (contentView.getBackground() == null) { // 没有背景色，则设置背景色
                contentView.setBackgroundColor(0xffeeeeee);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        viewDragHelper.processTouchEvent(ev);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // TODO 一定要做这个操作，否则onViewReleased不起作用
        if (viewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

}
