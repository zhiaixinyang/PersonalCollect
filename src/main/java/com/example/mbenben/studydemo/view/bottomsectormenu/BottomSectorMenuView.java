package com.example.mbenben.studydemo.view.bottomsectormenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/10/9.
 *
 * 原项目blog：https://www.jianshu.com/p/4518da430a01
 */

public class BottomSectorMenuView extends ViewGroup {

    private final static int STATUS_MENU_OPENED = 0x000000001;
    private final static int STATUS_MENU_CLOSED = 0x000000002;
    private int mCurrentStatus = STATUS_MENU_CLOSED;
    // Duplicate 的锚定 View
    private ImageView mAnchorView;
    // 画笔
    private Paint mPaint;
    // 坐标
    private Point mCenter;
    private Point mAnchorStart;
    // 每个ItemView之间的角度差
    private double mAngle;
    // 半径
    private float mMenuItemRadius; // ItemView 到圆心的半径
    private float mBackgroundRadius;// 背景圆的半径
    // 动画执行比率
    private float mBackgroundSpreadPercent;// 当前背景动画执行百分比
    private float mMenuItemSpreadPercent;// 当前Item动画执行的百分比
    // 动画
    private AnimatorSet mMenuCloseAnimator;
    private AnimatorSet mMenuOpenAnimator;
    // 动画时长
    private long mMenuCloseDuration;
    private long mMenuOpenDuration;
    // 按钮旋转角度
    private float mAnchorRotationAngle;
    // 监听器
    private OnMenuClosedListener mListener;

    private BottomSectorMenuView(Context context) {
        this(context, null);
    }

    private BottomSectorMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private BottomSectorMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.WHITE);
        // 初始化参数
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        // 获取圆心坐标
        mCenter = new Point(width / 2, height);
        // 获取半径
        mMenuItemRadius = width / 2;
        mBackgroundRadius = (int) Math.sqrt(Math.pow(width / 2, 2.0) + Math.pow(width / 2, 2.0));
        setWillNotDraw(false);
    }

    /********************************* 以下是向外界暴露的方法*************************************/

    // 打开菜单
    public void openMenu() {
        if (mCurrentStatus == STATUS_MENU_OPENED) return;
        if (mMenuOpenAnimator != null && mMenuOpenAnimator.isRunning()) return;
        showMenuOpenAnimator();
    }

    // 关闭菜单
    public void closeMenu(OnMenuClosedListener listener) {
        if (mCurrentStatus == STATUS_MENU_CLOSED) return;
        if (mMenuCloseAnimator != null && mMenuCloseAnimator.isRunning()) return;
        showMenuCloseAnimator(listener);
    }

    // 菜案是否开启
    public boolean isOpen() {
        return mCurrentStatus == STATUS_MENU_OPENED;
    }

    public interface OnMenuClosedListener {
        void onMenuClosed();
    }

    /********************************* 以上是向外界暴露的方法*************************************/

    private void initData(View anchorView, OnMenuClosedListener listener) {
        mListener = listener;
        // 获取锚定View在屏幕内的坐标
        int[] rawPosition = new int[2];
        anchorView.getLocationOnScreen(rawPosition);
        int left = rawPosition[0];
        int top = rawPosition[1];
        mAnchorStart = new Point(left, top);
        // 制造一个与原先一样的View放在原来的位置
        mAnchorView = new ImageView(getContext());
        mAnchorView.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mAnchorView.setImageBitmap(getBitmapFromView(anchorView));
        // 设置点击返回的效果
        mAnchorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(null);
            }
        });
        addView(mAnchorView, 0);
    }

    private void addMenuItems(List<View> views) {
        for (View view : views) {
            addView(view);
        }
        mAngle = Math.PI / (views.size() + 1);
    }

    private void setToggleDuration(long open, long close) {
        mMenuOpenDuration = open;
        mMenuCloseDuration = close;
    }

    private void setAnchorRotationAngle(float angle) {
        mAnchorRotationAngle = angle;
    }

    private void showMenuOpenAnimator() {
        // 锚点动画
        ObjectAnimator anchorAnim = ObjectAnimator.ofFloat(mAnchorView, "rotation", 0f, mAnchorRotationAngle);
        anchorAnim.setInterpolator(new OvershootInterpolator(3f));
        // 背景动画
        ValueAnimator brgSpreadAnim = ValueAnimator.ofFloat(0f, 1f);
        brgSpreadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBackgroundSpreadPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        // item的位置动画
        ValueAnimator itemSpreadAnim = ValueAnimator.ofFloat(0f, 1f);
        itemSpreadAnim.setInterpolator(new OvershootInterpolator(2f));
        itemSpreadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMenuItemSpreadPercent = (float) animation.getAnimatedValue();
                requestLayout();
            }
        });
        // 动画集合
        mMenuOpenAnimator = new AnimatorSet();
        mMenuOpenAnimator.setDuration(mMenuOpenDuration == 0 ? 800 : mMenuOpenDuration);
        mMenuOpenAnimator.play(brgSpreadAnim).before(itemSpreadAnim);
        mMenuOpenAnimator.play(anchorAnim).with(brgSpreadAnim);
        mMenuOpenAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                setAlpha(1f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentStatus = STATUS_MENU_OPENED;
            }
        });
        mMenuOpenAnimator.start();
    }

    private void showMenuCloseAnimator(final OnMenuClosedListener listener) {
        // Item动画
        ValueAnimator itemViewAnim = ValueAnimator.ofFloat(1f, 0f)
                .setDuration(mMenuCloseDuration == 0 ? 300 : mMenuCloseDuration / 2);
        itemViewAnim.setInterpolator(new AnticipateInterpolator(2f));
        itemViewAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMenuItemSpreadPercent = (float) animation.getAnimatedValue();
                requestLayout();
            }
        });

        // 背景动画
        ValueAnimator backgroundAnim = ValueAnimator.ofFloat(1f, 0f)
                .setDuration(mMenuCloseDuration == 0 ? 300 : mMenuCloseDuration / 2);
        backgroundAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBackgroundSpreadPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        // 这里设置了该View整体透明度的变化, 防止消失的背景不在锚点处, 显示效果突兀
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
                .setDuration(mMenuCloseDuration == 0 ? 300 : mMenuCloseDuration / 2);

        // 锚点动画
        ObjectAnimator anchorAnim = ObjectAnimator.ofFloat(mAnchorView, "rotation", mAnchorRotationAngle, 0f)
                .setDuration(mMenuCloseDuration == 0 ? 300 : mMenuCloseDuration / 2);
        anchorAnim.setInterpolator(new OvershootInterpolator(3f));

        // 动画集合
        mMenuCloseAnimator = new AnimatorSet();
        mMenuCloseAnimator.play(itemViewAnim).before(backgroundAnim);
        mMenuCloseAnimator.play(alphaAnim).with(backgroundAnim);
        mMenuCloseAnimator.play(anchorAnim).with(backgroundAnim);
        mMenuCloseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentStatus = STATUS_MENU_CLOSED;
                // Convert回调
                mListener.onMenuClosed();
                // 用户回调
                if (listener != null) {
                    listener.onMenuClosed();
                }
            }
        });
        mMenuCloseAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == 0) {
                child.layout(
                        mAnchorStart.x,
                        mAnchorStart.y,
                        mAnchorStart.x + child.getMeasuredWidth(),
                        mAnchorStart.y + child.getMeasuredHeight()
                );
                continue;
            }
            // 动态的计算角度
            double curAngle = Math.PI - mAngle * i;
            int childCenterX = (int) (mCenter.x + mMenuItemRadius * mMenuItemSpreadPercent * Math.cos(curAngle));
            int childCenterY = (int) (mCenter.y - mMenuItemRadius * mMenuItemSpreadPercent * Math.sin(curAngle));
            child.layout(
                    childCenterX - child.getMeasuredWidth() / 2,
                    childCenterY - child.getMeasuredHeight() / 2,
                    childCenterX + child.getMeasuredWidth() / 2,
                    childCenterY + child.getMeasuredHeight() / 2
            );
            // 动态的去设置ItemView的透明度
            child.setAlpha(mMenuItemSpreadPercent);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAnchorView == null)
            return;
        if (mBackgroundSpreadPercent > 0) {
            canvas.drawCircle(mCenter.x, mCenter.y, mBackgroundRadius * mBackgroundSpreadPercent, mPaint);
        }
        super.onDraw(canvas);
    }

    private Bitmap getBitmapFromView(View view) {
        // 创建缓存
        view.buildDrawingCache();
        // 获取缓存
        return view.getDrawingCache();
    }

    /**
     * 外界用于构建菜单的类
     */
    public static class Converter implements OnMenuClosedListener {

        private Context mContext;
        private View mAnchorView;
        private WindowManager mWindowManager;
        private ContainerView mWindowContainer;
        private BottomSectorMenuView mBottomMenuView;
        private WindowManager.LayoutParams mParams;
        private List<View> mViews = new ArrayList<>();
        private long mMenuOpenDuration;
        private long mMenuCloseDuration;
        private float mAnchorRotationAngle;

        public Converter(View view) {
            mAnchorView = view;
            mContext = mAnchorView.getContext();
            if (!(mContext instanceof Activity)) {
                throw new IllegalArgumentException("请确保当前的View依附的是Activity");
            }
        }

        public Converter setToggleDuration(long open, long close) {
            mMenuOpenDuration = open;
            mMenuCloseDuration = close;
            return this;
        }

        public Converter setAnchorRotationAngle(float angle) {
            mAnchorRotationAngle = angle;
            return this;
        }

        public Converter addMenuItem(@DrawableRes int iconRes, CharSequence text, final View.OnClickListener listener) {
            // 初始化Item的容器
            LinearLayout itemContainer = new LinearLayout(mContext);
            itemContainer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT
                    , LayoutParams.WRAP_CONTENT));
            itemContainer.setOrientation(LinearLayout.VERTICAL);
            itemContainer.setGravity(Gravity.CENTER);
            // 初始化图片
            ImageView imageView = new ImageView(mContext);
            int maxRadius = mContext.getResources().getDisplayMetrics().widthPixels / 2;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(maxRadius / 5, maxRadius / 5));
            imageView.setImageResource(iconRes);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View view) {
                    mBottomMenuView.closeMenu(new OnMenuClosedListener() {
                        @Override
                        @SuppressLint("NewApi")
                        public void onMenuClosed() {
                            listener.onClick(view);
                            if (mWindowContainer.isAttachedToWindow()) {
                                mWindowManager.removeView(mWindowContainer);
                            }
                        }
                    });
                }
            });
            itemContainer.addView(imageView);
            // 初始化文本
            if (text != null && !((String) text).trim().isEmpty()) {
                TextView textView = new TextView(mContext);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
                textView.setText(text);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(0, dp2px(5), 0, 0);
                itemContainer.addView(textView);
            }
            mViews.add(itemContainer);
            return this;
        }

        public BottomSectorMenuView apply() {
            init();
            // 开启时, 将我们的Container加载到Window中
            mAnchorView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 先添加进Window
                    mWindowManager.addView(mWindowContainer, mParams);
                    // 再开启菜单
                    mBottomMenuView.openMenu();
                }
            });
            // 给mContainer绑定返回监听器
            mWindowContainer.setOnBackPressedListener(new ContainerView.OnBackPressedListener() {
                @Override
                public void onBackPress() {
                    if (mBottomMenuView.isOpen()) {
                        mBottomMenuView.closeMenu(null);
                    }
                }
            });
            mAnchorView.post(new Runnable() {
                @Override
                public void run() {
                    mBottomMenuView.setToggleDuration(mMenuOpenDuration, mMenuCloseDuration);
                    mBottomMenuView.setAnchorRotationAngle(mAnchorRotationAngle);
                    mBottomMenuView.initData(mAnchorView, Converter.this);
                    mBottomMenuView.addMenuItems(mViews);
                }
            });
            return mBottomMenuView;
        }

        private void init() {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            mParams = new WindowManager.LayoutParams();
            mParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
            mParams.height = mContext.getResources().getDisplayMetrics().heightPixels;
            mParams.flags = WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            mParams.format = PixelFormat.TRANSPARENT;
            mWindowContainer = new ContainerView(mContext);
            mBottomMenuView = new BottomSectorMenuView(mContext);
            mBottomMenuView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            mWindowContainer.addView(mBottomMenuView);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onMenuClosed() {
            if (mWindowContainer.isAttachedToWindow()) {
                mWindowManager.removeView(mWindowContainer);
            }
        }

        protected int dp2px(float dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        }
    }

    /**
     * 用于回调Window下的点击返回事件的ViewGroup
     */
    public static class ContainerView extends FrameLayout {

        private boolean mIsBackPressed = false;

        public ContainerView(Context context) {
            super(context);
        }

        public ContainerView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        /**
         * 点击返回事件的回调接口
         */
        public interface OnBackPressedListener {
            void onBackPress();
        }

        OnBackPressedListener mOnBackPressedListener = null;

        public void setOnBackPressedListener(OnBackPressedListener listener) {
            mOnBackPressedListener = listener;
        }

        /**
         * 重写该方法, 用于监听返回键
         */
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                    || event.getKeyCode() == KeyEvent.KEYCODE_SETTINGS) {
                if (mIsBackPressed) return true;// 防止返回按钮被处触发两次
                mIsBackPressed = true;
                if (mOnBackPressedListener != null) {
                    // 从我们定义的接口中将其回调出去
                    mOnBackPressedListener.onBackPress();
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mIsBackPressed = false;
                        }
                    }, 500);
                    return true;
                }
            }
            return super.dispatchKeyEvent(event);
        }
    }
}
