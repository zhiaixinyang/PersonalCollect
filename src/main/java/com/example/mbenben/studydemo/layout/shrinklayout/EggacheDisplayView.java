package com.example.mbenben.studydemo.layout.shrinklayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/6/19.
 *
 * 原项目GitHub：https://github.com/DthFish/EggacheDisplayView
 */

public class EggacheDisplayView  extends ViewGroup {

    private ArrayList<Animator> mExpendAnimators;
    private ArrayList<Animator> mCollapseAnimators;
    private AnimatorListenerAdapter mExpandListener;
    private AnimatorListenerAdapter mCollapseListener;
    private int mMaxButtonWidth;
    private AnimatorSet mLoopAnimationSet;
    /**
     * 若为 true 在 loop 模式下点击 view 直接展开，List 模式下响应子 view 点击
     */
    private boolean mClickLoopToExpand;
    private int mMaxButtonHeight;

    public enum DisplayMode {
        LIST,
        LOOP
    }

    public enum ListDirection {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    public enum LoopDirection {
        RIGHT_TO_LEFT,
        BOTTOM_TO_TOP
    }

    private DisplayMode mDisplayMode = DisplayMode.LIST;
    private ListDirection mListDirection = ListDirection.BOTTOM;
    private LoopDirection mLoopDirection = LoopDirection.RIGHT_TO_LEFT;
    private static final int ANIMATION_DURATION = 400;
    private AnimatorSet mExpandAnimatorSet = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mCollapseAnimatorSet = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mExpandBtnAnimatorSet = new AnimatorSet();
    /**
     * LOOP mode 的动画间隔
     */
    private int mLoopGap = 2000;
    private int mLoopDuration = 1000;
    private boolean isStarted;
    /**
     * LOOP mode 当前显示的 MenuView
     */
    private View mCurrentView;
    /**
     * LOOP mode 即将显示的 MenuView
     */
    private View mNextView;
    private View mBtnCollapse;


    private int mBtnSpacing;
    private View mBtnExpand;
    private boolean mIsExtend = true;
    /**
     * 除去{@link #mBtnCollapse,#mBtnExpand} 的所有子 View
     */
    private List<View> mMenuViews = new ArrayList<>();
    public static final String TAG = "EggacheDisplayView";


    public EggacheDisplayView(@NonNull Context context) {
        this(context, null);
    }

    public EggacheDisplayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EggacheDisplayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EggacheDisplayView, 0, 0);
        mBtnSpacing = typedArray.getDimensionPixelSize(R.styleable.EggacheDisplayView_btn_spacing, dpToPx(context, 10));
        int layoutCollapse = typedArray.getResourceId(R.styleable.EggacheDisplayView_collapse_layout, R.layout.layout_collapse_button);
        int layoutExpand = typedArray.getResourceId(R.styleable.EggacheDisplayView_expand_layout, R.layout.layout_expand_button);
        mClickLoopToExpand = typedArray.getBoolean(R.styleable.EggacheDisplayView_click_loop_to_expand, false);

        typedArray.recycle();

        createCollapseAndExpandButton(context, layoutCollapse, layoutExpand);
    }

    private void createCollapseAndExpandButton(Context context, int layoutCollapse, int layoutExpand) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        mBtnCollapse = layoutInflater.inflate(layoutCollapse, this, false);
        mBtnExpand = layoutInflater.inflate(layoutExpand, this, false);
        mBtnExpand.setVisibility(GONE);

        addView(mBtnCollapse);
        addView(mBtnExpand);

        mBtnCollapse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse();
            }
        });
        mBtnExpand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expand();
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickLoopToExpand && mDisplayMode == DisplayMode.LOOP) {
                    expand();
                }
            }
        });

    }

    /**
     * 设置子 menu view
     */
    public void setMenuViews(List<View> views) {
        if (views == null || views.isEmpty()) {
            return;
        }
        stop();
        mMenuViews.clear();
        mMenuViews.addAll(views);
        removeAllViews();
        addView(mBtnCollapse);
        addView(mBtnExpand);
        for (View view : views) {
            addView(view);
        }
    }

    /**
     * @param views          menu view
     * @param keepChildCount 保留在 xml 里面的添加的子view 数量，因为要除去mBtnCollapse/mBtnExpand，
     *                       所以第三个开始计算
     */
    public void setMenuViews(List<View> views, int keepChildCount) {
        if (views == null || views.isEmpty()) {
            return;
        }
        stop();
        if (getChildCount() - 2 >= keepChildCount) {
            List<View> tempViews = new ArrayList<>();
            for (int i = 0; i < keepChildCount; i++) {
                tempViews.add(mMenuViews.get(i));
            }
            mMenuViews.clear();
            mMenuViews.addAll(tempViews);
            mMenuViews.addAll(views);
            int start = 2 + keepChildCount;
            removeViews(start, getChildCount() - start);
            for (View view : views) {
                addView(view);
            }
        } else {
            setMenuViews(views);
        }
    }

    /**
     * 在已经存在 menu item 的时候调用，不会清除已有的 item
     *
     * @param views 注意不要重复添加
     */
    public void addMenuViews(List<View> views) {
        if (views == null || views.isEmpty()) {
            return;
        }
        stop();
        mMenuViews.addAll(views);
        for (View view : views) {
            addView(view);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate: " + getChildCount());
        mMenuViews.clear();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mBtnCollapse || child == mBtnExpand) continue;
//            child.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "item onClick: ");
//                }
//            });
            mMenuViews.add(child);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        mMaxButtonWidth = 0;
        mMaxButtonHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            mMaxButtonWidth = Math.max(mMaxButtonWidth, child.getMeasuredWidth());
            mMaxButtonHeight = Math.max(mMaxButtonHeight, child.getMeasuredHeight());
        }
        if (mDisplayMode == DisplayMode.LIST) {
            // DisplayMode.LIST 相当于 LinearLayout

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == GONE) continue;

                height += child.getMeasuredHeight();
                // 最后一个不加
                if (i != getChildCount() - 1) {
                    height += mBtnSpacing;
                }

            }

        } else if (mDisplayMode == DisplayMode.LOOP) {
            // DisplayMode.LOOP 高度只包括 最高的子 view 的高度 + mBtnExpand 的高度
            height += mMaxButtonHeight + mBtnSpacing + mBtnExpand.getMeasuredHeight();

        }
        width = mMaxButtonWidth + getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();


        if (getLayoutParams().width == LayoutParams.MATCH_PARENT) {
            width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        }
        if (getLayoutParams().height == LayoutParams.MATCH_PARENT) {
            height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 上往下展开
        if (mDisplayMode == DisplayMode.LIST) {
            int centerHorizontalX = (right - left) / 2;
            int nextY = getPaddingTop();
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == GONE || child == mBtnExpand) {
                    continue;
                }
                // DisplayMode.LOOP 模式时，onLayout 会把 menuView 进行横坐标的偏移，这里把坐标回置
                child.setTranslationX(0);
                int l = centerHorizontalX - child.getMeasuredWidth() / 2;
                int t = nextY;
                int r = l + child.getMeasuredWidth();
                int b = t + child.getMeasuredHeight();
                child.layout(l, t, r, b);
                nextY += child.getMeasuredHeight() + mBtnSpacing;
            }
            mBtnExpand.layout(0, 0, 0, 0);
        } else if (mDisplayMode == DisplayMode.LOOP) {
            int centerHorizontalX = (right - left) / 2;
            int nextY = getPaddingTop();

            boolean hasLayoutFirstVisibleMenu = false;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);

                if (child.getVisibility() == GONE) {
                    continue;
                }
                if (child == mBtnExpand || child == mBtnCollapse) {
                    int l = centerHorizontalX - child.getMeasuredWidth() / 2;
                    int t = nextY;
                    int r = l + child.getMeasuredWidth();
                    int b = t + child.getMeasuredHeight();
                    child.layout(l, t, r, b);
                    nextY += mMaxButtonHeight + mBtnSpacing;
                } else {
                    // DisplayMode.LIST 模式时，动画 会把 menuView 进行纵坐标的偏移，透明度设置，这里把坐标，透明度回置
                    child.setAlpha(1);
                    child.setTranslationY(0);
                    // 除去第一个item 设置到可见位置，其余的设置到控件右边不可见位置
                    if (hasLayoutFirstVisibleMenu) {
//                        int l = centerHorizontalX + child.getMeasuredWidth() / 2 + mMaxButtonWidth;
                        int l = centerHorizontalX - child.getMeasuredWidth() / 2;
                        int t = getPaddingTop();
                        int r = l + child.getMeasuredWidth();
                        int b = t + child.getMeasuredHeight();
                        child.layout(l, t, r, b);
                        child.setTranslationX(mMaxButtonWidth);
                    } else {
                        int l = centerHorizontalX - child.getMeasuredWidth() / 2;
                        int t = getPaddingTop();
                        int r = l + child.getMeasuredWidth();
                        int b = t + child.getMeasuredHeight();
                        child.layout(l, t, r, b);
                        hasLayoutFirstVisibleMenu = true;

                    }
                }
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("EggacheDisplayView", "onSizeChanged: ");
        createViewsAnimation();
    }

    private void createViewsAnimation() {
        if (mDisplayMode == DisplayMode.LIST) {
            createListModeAnimation();
        } else if (mDisplayMode == DisplayMode.LOOP) {
            createLoopModeAnimation();
        }


    }

    private void createListModeAnimation() {
        mExpendAnimators = new ArrayList<>();
        mCollapseAnimators = new ArrayList<>();
        // 第一个item舍弃透明度变化
        boolean hasDropFirstItemAlpha = false;
        int beginY = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mBtnCollapse && mBtnCollapse.getVisibility() != GONE) {
                beginY -= mBtnCollapse.getMeasuredHeight();
                beginY -= mBtnSpacing;
            } else if (child == mBtnExpand) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(child, "translationY", -mBtnExpand.getMeasuredHeight(), 0);
                animator.setStartDelay(400);
                animator.setDuration(800);
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(child, "alpha", 0, 1);
                alphaAnimator.setStartDelay(400);
                alphaAnimator.setDuration(800);
                mExpandBtnAnimatorSet.playTogether(animator, alphaAnimator);

            } else if (child.getVisibility() == GONE) {
                // do nothing
            } else {
                ObjectAnimator animator = ObjectAnimator.ofFloat(child, "translationY", beginY, 0);
                mExpendAnimators.add(animator);
                if (hasDropFirstItemAlpha) {
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(child, "alpha", 0, 1);
                    mExpendAnimators.add(alphaAnimator);
                }

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(child, "translationY", 0, beginY);
                mCollapseAnimators.add(animator2);
                if (hasDropFirstItemAlpha) {
                    ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(child, "alpha", 1, 0);
                    mCollapseAnimators.add(alphaAnimator2);
                }
                hasDropFirstItemAlpha = true;
                beginY -= child.getMeasuredHeight();
                beginY -= mBtnSpacing;
            }
        }
        if (mExpandListener == null) {
            mExpandListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    // 展开,这里会引发重绘
                    mDisplayMode = DisplayMode.LIST;
                    mBtnExpand.setVisibility(GONE);
                    mBtnCollapse.setVisibility(VISIBLE);
                }
            };

            mExpandAnimatorSet.addListener(mExpandListener);
        }
        if (mCollapseListener == null) {
            mCollapseListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    // 收起,这里会引发重绘
                    mBtnExpand.setAlpha(0);
                    mDisplayMode = DisplayMode.LOOP;
                    mBtnExpand.setVisibility(VISIBLE);
                    mBtnCollapse.setVisibility(INVISIBLE);
                    mExpandBtnAnimatorSet.start();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            };
            mCollapseAnimatorSet.addListener(mCollapseListener);
        }
    }

    private void createLoopModeAnimation() {
        start();
    }

    public void expand() {
        if (!mIsExtend) {
            mIsExtend = true;
            stop();
            mCollapseAnimatorSet.cancel();
            mExpandAnimatorSet.playTogether(mExpendAnimators);
            mExpandAnimatorSet.start();
        }
    }

    public void collapse() {
        if (mIsExtend) {
            mIsExtend = false;
            mExpandAnimatorSet.cancel();
            mCollapseAnimatorSet.playTogether(mCollapseAnimators);
            mCollapseAnimatorSet.start();

        }

    }

    public void start() {
        if (!isStarted && mMenuViews.size() > 1 && mDisplayMode == DisplayMode.LOOP) {
            isStarted = true;
            mCurrentView = mMenuViews.get(0);
            mNextView = mMenuViews.get(1);
            postDelayed(mRunnable, mLoopGap);
        }
    }

    public void stop() {
        isStarted = false;
        removeCallbacks(mRunnable);
        if (mLoopAnimationSet != null) {
            mLoopAnimationSet.cancel();
        }
    }


    private void performSwitch() {
        if (!isStarted) {
            return;
        }
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mCurrentView, "translationX", -mMaxButtonWidth);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mNextView, "translationX", 0);
        mLoopAnimationSet = new AnimatorSet();
        mLoopAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                if (mCurrentView != null) {
                    mCurrentView.setTranslationX(0);
                }
                if (mNextView != null) {
                    mNextView.setTranslationX(0);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentView.setTranslationX(mMaxButtonWidth);
                mCurrentView = mNextView;
                int index = mMenuViews.indexOf(mCurrentView);
                mNextView = index == mMenuViews.size() - 1 ? mMenuViews.get(0) : mMenuViews.get(index + 1);
            }

        });
        mLoopAnimationSet.setDuration(mLoopDuration);
        mLoopAnimationSet.playTogether(animator1, animator2);
        mLoopAnimationSet.start();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (mClickLoopToExpand && mDisplayMode == DisplayMode.LOOP) || super.onInterceptTouchEvent(ev);
    }

    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {

        @Override
        public void run() {
            performSwitch();
            postDelayed(mRunnable, mLoopGap + mLoopDuration);
        }
    }


    @Override
    public MarginLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected MarginLayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected MarginLayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
                MarginLayoutParams.WRAP_CONTENT);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

}
