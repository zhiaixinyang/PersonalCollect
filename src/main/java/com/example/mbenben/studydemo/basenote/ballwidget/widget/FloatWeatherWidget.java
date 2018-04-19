package com.example.mbenben.studydemo.basenote.ballwidget.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.basenote.ballwidget.anim.AnimUtils;
import com.example.mbenben.studydemo.basenote.ballwidget.widget.config.WidgetBallSp;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.util.DensityUtil;
import com.example.mbenben.studydemo.utils.NavigationBarUtils;
import com.example.mbenben.studydemo.utils.StatusBarUtils;

/**
 * @author MDove on 2018/4/19.
 */
public class FloatWeatherWidget {
    public static final String TAG = "FloatWeatherWidget";
    private static final long VIBRATION_DURATION = 100;
    private Context mCxt;
    private final RemoveWidgetView mRemoveWidgetView;
    private final WindowManager mWindowManager;
    private final Vibrator mVibrator;
    private final Handler mHandler;
    private final View mFloatView;
    private final Point mScreenSize;

    private boolean mReleased;
    private final RectF mRemoveBounds;
    private final Point mHiddenRemoveWidgetPos;
    private final Point mVisibleRemoveWidgetPos;
    private boolean mShown;
    private OnWidgetStateChangedListener mOnWidgetStateChangedListener;
    private final FloatingViewTouchHelper mTouchHelper;

    private boolean mStaticMode;
    private boolean mRemoveWidgetShown;
    private final float mFloatingWidgetWidth, mFloatingWidgetHeight;
    private final float mRadius;

    public FloatWeatherWidget(@NonNull Context context, @NonNull View floatView, @Nullable Configuration configuration) {
        this.mCxt = context;
        this.mFloatView = floatView;

        this.mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mScreenSize = new Point();
        this.mWindowManager.getDefaultDisplay().getSize(mScreenSize);
        this.mScreenSize.y -= StatusBarUtils.getStatusBarHeight(context) /*+ (NavigationBarUtils.hasNavigationBar(context) ? NavigationBarUtils.getNavigationBarHeight(context) : 0)*/;

        if (configuration == null) {
            configuration = prepareConfiguration();
        }
        this.mRemoveWidgetView = new RemoveWidgetView(mCxt, configuration);
        if (floatView instanceof IFloatView) {
            ((IFloatView) floatView).setConfiguration(configuration);
        }

        this.mRemoveBounds = new RectF();
        this.mHiddenRemoveWidgetPos = new Point();
        this.mVisibleRemoveWidgetPos = new Point();

        mFloatingWidgetWidth = configuration.widgetWidth();
        mFloatingWidgetHeight = mFloatingWidgetWidth;
        mRadius = mFloatingWidgetWidth / 2f;

        mTouchHelper = new FloatingViewTouchHelper(mFloatView, newBoundsChecker(0, 0))
                .screenWidth(mScreenSize.x)
                .screenHeight(mScreenSize.y)
                .FloatingViewSize(configuration.widgetWidth(), configuration.widgetWidth())
                .longPressEnable(false)
                .alwaysStickyEdge(true)
                .flingEnable(true)
                .setTouchCallback(new FloatingViewTouchCallback());
        mTouchHelper.setTouchCallback(new FloatingViewTouchCallback());
    }

    private Configuration prepareConfiguration() {
        return new Configuration.Builder()
                .setCrossColor(ContextCompat.getColor(mCxt, R.color.cross_default))
                .setCrossOverlappedColor(ContextCompat.getColor(mCxt, R.color.cross_overlapped))
                .setWidth(mCxt.getResources().getDimensionPixelSize(R.dimen.ball_widget_width))
                .setRadius(DensityUtil.dip2px(mCxt, 30))
                .setCrossStrokeWidth(DensityUtil.dip2px(mCxt, 4))
                .setShadowColor(Color.parseColor("#50000000"))
                .setShadowDx(DensityUtil.dip2px(mCxt, 1))
                .setShadowDy(DensityUtil.dip2px(mCxt, 1))
                .setShadowRadius(DensityUtil.dip2px(mCxt, 5))
                .build();
    }

    /**
     * @param oldOrientation {@link android.content.res.Configuration#ORIENTATION_PORTRAIT}
     *                       or {@link android.content.res.Configuration#ORIENTATION_LANDSCAPE}
     * @param newOrientation same to oldOrientation
     */
    public void onConfigurationChanged(int oldOrientation, int newOrientation) {
        mWindowManager.getDefaultDisplay().getSize(mScreenSize);
        this.mScreenSize.y -= StatusBarUtils.getStatusBarHeight(mCxt);
        mTouchHelper.screenWidth(mScreenSize.x)
                .screenHeight(mScreenSize.y);
        mTouchHelper.onConfigurationChanged(oldOrientation, newOrientation);
        int positionX, positionY;
        if (newOrientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            positionX = WidgetBallSp.getLandscapePositionX();
            positionY = WidgetBallSp.getLandscapePositionY();
            if (positionX == -1 || positionY == -1) {
                positionX = 0;
                positionY = (int) (mScreenSize.y / 2 - mFloatingWidgetHeight / 2);
            }
            animMoveFloatViewTo(positionX, positionY);
        } else {
            positionX = WidgetBallSp.getPositionX();
            positionY = WidgetBallSp.getPositionY();
            animMoveFloatViewTo(positionX, positionY);
            if (positionX == -1 || positionY == -1) {
                mTouchHelper.adjustFirstPosition();
            }
        }
    }

    /**
     * 在指定位置显示widget
     *
     * @param x
     * @param y
     */
    public void show(int x, int y) {
        if (mShown) {
            return;
        }
        if (x < 0 || x >= mScreenSize.x) {
            x = mScreenSize.x;
        }
        if (y < 0 || y >= mScreenSize.y) {
            y = mScreenSize.y / 2;
        }
        mShown = true;
        WidgetBallSp.setShown(true);
        int removeViewX = (int) (mScreenSize.x / 2f - mRemoveWidgetView.getOverlappedRadius());
        int removeViewHiddenY = (int) (mScreenSize.y + mFloatingWidgetHeight + NavigationBarUtils.getNavigationBarHeight(mCxt));
        int removeViewVisibleY = (int) (mScreenSize.y - mRadius - (NavigationBarUtils.hasNavigationBar(mCxt) ? 0 : mFloatingWidgetHeight));
        mHiddenRemoveWidgetPos.set(removeViewX, removeViewHiddenY);
        mVisibleRemoveWidgetPos.set(removeViewX, removeViewVisibleY);

        try {
            show(mRemoveWidgetView, mHiddenRemoveWidgetPos.x, mHiddenRemoveWidgetPos.y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        show(mFloatView, x, y);
        mTouchHelper.adjustFirstPosition();
        if (mOnWidgetStateChangedListener != null) {
            mOnWidgetStateChangedListener.onWidgetStateChanged(State.ADDED);
        }
    }

    private void show(View view, int left, int top) {
        try {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,//show in lock
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.START | Gravity.TOP;
            params.x = left;
            params.y = top;

            mWindowManager.addView(view, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hide() {
        hideInternal(true);
    }

    private void hideInternal(boolean byPublic) {
        if (!mShown) {
            return;
        }
        mShown = false;
        WidgetBallSp.setShown(false);
        mReleased = true;
        try {
            mWindowManager.removeView(mFloatView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (byPublic) {
            try {
                mWindowManager.removeView(mRemoveWidgetView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //内部滑动关闭
        }
        if (mOnWidgetStateChangedListener != null) {
            mOnWidgetStateChangedListener.onWidgetStateChanged(State.REMOVED);
        }
        mStaticMode = false;
    }

    public boolean isShown() {
        return mShown;
    }

    public void animMoveFloatViewTo(int x, int y) {
        if (!mShown) {
            show(x, y);
        } else {
            final WindowManager.LayoutParams params = (WindowManager.LayoutParams) mFloatView.getLayoutParams();
            PropertyValuesHolder dxHolder = PropertyValuesHolder.ofInt("x", 0, 0);
            PropertyValuesHolder dyHolder = PropertyValuesHolder.ofInt("y", 0, 0);
            dxHolder.setEvaluator(new IntEvaluator());
            dyHolder.setEvaluator(new IntEvaluator());
            ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(dxHolder, dyHolder);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setDuration(350);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int x = (int) animation.getAnimatedValue("x");
                    int y = (int) animation.getAnimatedValue("y");
                    params.x = x;
                    params.y = y;
                    try {
                        mWindowManager.updateViewLayout(mFloatView, params);
                    } catch (IllegalArgumentException e) {
                        // view not attached to window
                        animation.cancel();
                    }
                }
            });
            dxHolder.setIntValues(params.x, x);
            dyHolder.setIntValues(params.y, y);
            valueAnimator.start();
        }

    }

    private class FloatingViewTouchCallback extends FloatingViewTouchHelper.SimpleTouchCallback {

        private boolean readyToRemove;
        private static final long REMOVE_BTN_ANIM_DURATION = 200;
        private final ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
        private int animatedRemBtnYPos;

        public FloatingViewTouchCallback() {
            animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (!mRemoveWidgetShown) {
                        return;
                    }
                    animatedRemBtnYPos = (int) ((float) animation.getAnimatedValue());
                    updateRemoveBtnPosition();
                }
            };
        }

        private void updateRemoveBtnPosition() {
            if (mRemoveWidgetShown) {
                WindowManager.LayoutParams floatViewParams = (WindowManager.LayoutParams) mFloatView.getLayoutParams();
                WindowManager.LayoutParams removeBtnParams = (WindowManager.LayoutParams) mRemoveWidgetView.getLayoutParams();

                double tgAlpha = (mScreenSize.x / 2. - floatViewParams.x) / (mVisibleRemoveWidgetPos.y - floatViewParams.y);
                double rotationDegrees = 360 - Math.toDegrees(Math.atan(tgAlpha));

                float distance = (float) Math.sqrt(Math.pow(animatedRemBtnYPos - floatViewParams.y, 2) +
                        Math.pow(mVisibleRemoveWidgetPos.x - mHiddenRemoveWidgetPos.x, 2));
                float maxDistance = (float) Math.sqrt(Math.pow(mScreenSize.x, 2) + Math.pow(mScreenSize.y, 2));
                distance /= maxDistance;

                if (animatedRemBtnYPos == -1) {
                    animatedRemBtnYPos = mVisibleRemoveWidgetPos.y;
                }

                removeBtnParams.x = (int) AnimUtils.rotateX(
                        mVisibleRemoveWidgetPos.x, animatedRemBtnYPos - mRadius * distance,
                        mHiddenRemoveWidgetPos.x, animatedRemBtnYPos, (float) rotationDegrees);
                removeBtnParams.y = (int) AnimUtils.rotateY(
                        mVisibleRemoveWidgetPos.x, animatedRemBtnYPos - mRadius * distance,
                        mHiddenRemoveWidgetPos.x, animatedRemBtnYPos, (float) rotationDegrees);

                try {
                    mWindowManager.updateViewLayout(mRemoveWidgetView, removeBtnParams);
                } catch (IllegalArgumentException e) {
                    // view not attached to window
                }
            }
        }

        @Override
        public void onClick(float x, float y) {
            if (mStaticMode) {
                return;
            }
            if (mOnWidgetStateChangedListener != null) {
                mOnWidgetStateChangedListener.onWidgetClicked();
            }
        }

        @Override
        public void onTouched(float x, float y) {
            super.onTouched(x, y);
            mReleased = false;
            if (mStaticMode) {
                return;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!mReleased) {
                        //动画显示删除 叉子
                        mRemoveWidgetShown = true;
                        ValueAnimator animator = ValueAnimator.ofFloat(mHiddenRemoveWidgetPos.y, mVisibleRemoveWidgetPos.y);
                        animator.setDuration(REMOVE_BTN_ANIM_DURATION);
                        animator.addUpdateListener(animatorUpdateListener);
                        animator.start();
                    }
                }
            }, ViewConfiguration.getLongPressTimeout());
        }

        @Override
        public void onMoved(float diffX, float diffY) {
            super.onMoved(diffX, diffY);
            if (mStaticMode) {
                return;
            }
            boolean curReadyToRemove = isReadyToRemove();
            if (curReadyToRemove != readyToRemove) {
                readyToRemove = curReadyToRemove;
                mRemoveWidgetView.setOverlapped(readyToRemove);
                if (readyToRemove && mVibrator.hasVibrator()) {
                    mVibrator.vibrate(VIBRATION_DURATION);
                }
            }
            updateRemoveBtnPosition();
        }

        @Override
        public void onReleased(float x, float y) {
            super.onReleased(x, y);
            mReleased = true;
            if (mStaticMode) {
                return;
            }
            if (mRemoveWidgetShown) {
                ValueAnimator animator = ValueAnimator.ofFloat(mVisibleRemoveWidgetPos.y, mHiddenRemoveWidgetPos.y);
                animator.setDuration(REMOVE_BTN_ANIM_DURATION);
                animator.addUpdateListener(animatorUpdateListener);
                animator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRemoveWidgetShown = false;
                        if (!mShown) {
                            try {
                                mWindowManager.removeView(mRemoveWidgetView);
                            } catch (IllegalArgumentException e) {
                                // view not attached to window
                            }
                        }
                    }
                });
                animator.start();
            }

            if (isReadyToRemove()) {
                hideInternal(false);
            } else {
                if (mOnWidgetStateChangedListener != null) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mFloatView.getLayoutParams();
                    mOnWidgetStateChangedListener.onWidgetPositionChanged(params.x, params.y);
                }
            }
        }

        @Override
        public void onAnimationCompleted() {
            super.onAnimationCompleted();
            if (mOnWidgetStateChangedListener != null && !readyToRemove) {
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) mFloatView.getLayoutParams();
                mOnWidgetStateChangedListener.onWidgetPositionChanged(params.x, params.y);
            }
        }

        private boolean isReadyToRemove() {
            WindowManager.LayoutParams removeParams = (WindowManager.LayoutParams) mRemoveWidgetView.getLayoutParams();
            mRemoveBounds.set(removeParams.x, removeParams.y, removeParams.x + mRemoveWidgetView.getWidth(), removeParams.y + mRemoveWidgetView.getWidth());
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mFloatView.getLayoutParams();
            float cx = params.x + mFloatingWidgetWidth / 2;
            float cy = params.y + mFloatingWidgetHeight / 2;
            return mRemoveBounds.contains(cx, cy);
        }
    }

    public void setOnWidgetStateChangedListener(OnWidgetStateChangedListener changedListener) {
        mOnWidgetStateChangedListener = changedListener;
    }

    private FloatingViewTouchHelper.BoundsChecker newBoundsChecker(int offsetX, int offsetY) {
        return new FloatingViewBoundsCheckerImpl(offsetX, offsetY);
    }

    private static final class FloatingViewBoundsCheckerImpl extends FloatingViewTouchHelper.BoundsCheckerWithOffset {

        public FloatingViewBoundsCheckerImpl(int offsetX, int offsetY) {
            super(offsetX, offsetY);
        }

        @Override
        protected float stickyLeftSideImpl(float screenWidth) {
            return 0;
        }

        @Override
        protected float stickyRightSideImpl(float screenWidth) {
            return screenWidth;
        }

        @Override
        protected float stickyTopSideImpl(float screenHeight) {
            return 0;
        }

        @Override
        protected float stickyBottomSideImpl(float screenHeight) {
            return screenHeight;
        }
    }

    public interface OnWidgetStateChangedListener {

        /**
         * 回调 widget 当前状态
         *
         * @param state
         */
        void onWidgetStateChanged(@NonNull State state);

        /**
         * 回调 当前widget的位置
         *
         * @param x x
         * @param y y
         */
        void onWidgetPositionChanged(int x, int y);

        void onWidgetClicked();
    }

    public enum State {
        ADDED,
        REMOVED
    }
}
