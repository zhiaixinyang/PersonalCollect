package com.example.mbenben.studydemo.layout.swipecards;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.example.mbenben.studydemo.R;

/**
 * Created by dionysis_lorentzos on 5/8/14
 * for package com.lorentzos.swipecards
 * and project Swipe cards.
 * Use with caution dinosaurs might appear!
 *
 * 原作者项目GitHub：https://github.com/Diolor/Swipecards
 */

public class SwipeFlingAdapterView extends BaseFlingAdapterView {


    private int MAX_VISIBLE = 4;
    private int MIN_ADAPTER_STACK = 6;
    private float ROTATION_DEGREES = 15.f;

    private Adapter mAdapter;
    private int LAST_OBJECT_IN_STACK = 0;
    private onFlingListener mFlingListener;
    private AdapterDataSetObserver mDataSetObserver;
    private boolean mInLayout = false;
    private View mActiveCard = null;
    private OnItemClickListener mOnItemClickListener;
    private FlingCardListener flingCardListener;
    private PointF mLastTouchPoint;


    public SwipeFlingAdapterView(Context context) {
        this(context, null);
    }

    public SwipeFlingAdapterView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SwipeFlingStyle);
    }

    public SwipeFlingAdapterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeFlingAdapterView, defStyle, 0);
        MAX_VISIBLE = a.getInt(R.styleable.SwipeFlingAdapterView_max_visible, MAX_VISIBLE);
        MIN_ADAPTER_STACK = a.getInt(R.styleable.SwipeFlingAdapterView_min_adapter_stack, MIN_ADAPTER_STACK);
        ROTATION_DEGREES = a.getFloat(R.styleable.SwipeFlingAdapterView_rotation_degrees, ROTATION_DEGREES);
        a.recycle();
    }


    /**
     * A shortcut method to set both the listeners and the adapter.
     *
     * @param context The activity context which extends onFlingListener, OnItemClickListener or both
     * @param mAdapter The adapter you have to set.
     */
    public void init(final Context context, Adapter mAdapter) {
        if(context instanceof onFlingListener) {
            mFlingListener = (onFlingListener) context;
        }else{
            throw new RuntimeException("Activity does not implement SwipeFlingAdapterView.onFlingListener");
        }
        if(context instanceof OnItemClickListener){
            mOnItemClickListener = (OnItemClickListener) context;
        }
        setAdapter(mAdapter);
    }

    @Override
    public View getSelectedView() {
        return mActiveCard;
    }


    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mAdapter == null) {
            return;
        }

        mInLayout = true;
        final int adapterCount = mAdapter.getCount();
        //如果adapter中没有对象，清空View
        if(adapterCount == 0) {
            removeAllViewsInLayout();
        }else {
            //获取最上层的Card，LAST_OBJECT_IN_STACK初始化为0
            View topCard = getChildAt(LAST_OBJECT_IN_STACK);
            if(mActiveCard!=null && topCard!=null && topCard==mActiveCard) {
                if (this.flingCardListener.isTouching()) {
                    //此处通过Listener的getLastPoint()拿到Card的坐标信息
                    PointF lastPoint = this.flingCardListener.getLastPoint();
                    //第一次时mLastTouchPoint必然为null，此时进行拖动赋值
                    if (this.mLastTouchPoint == null || !this.mLastTouchPoint.equals(lastPoint)) {
                        this.mLastTouchPoint = lastPoint;
                        //移除第一个Card，加载下面的Card
                        removeViewsInLayout(0, LAST_OBJECT_IN_STACK);
                        //加载下面的Card
                        layoutChildren(1, adapterCount);
                    }
                }
            }else{
                //重置UI并设置顶视图监听器
                removeAllViewsInLayout();
                layoutChildren(0, adapterCount);
                setTopView();
            }
        }

        mInLayout = false;

        if(adapterCount <= MIN_ADAPTER_STACK) mFlingListener.onAdapterAboutToEmpty(adapterCount);
    }


    private void layoutChildren(int startingIndex, int adapterCount){
        while (startingIndex < Math.min(adapterCount, MAX_VISIBLE) ) {
            //获取移除后的下一个Card
            View newUnderChild = mAdapter.getView(startingIndex, null, this);
            if (newUnderChild.getVisibility() != GONE) {
                //显示移除后的下一个Card
                makeAndAddView(newUnderChild);
                //LAST_OBJECT_IN_STACK赋值成当前的Card的索引
                LAST_OBJECT_IN_STACK = startingIndex;
            }
            //自增
            startingIndex++;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void makeAndAddView(View child) {

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
        /**
          * 将child加入到ViewGroup中
          * true:则调用此方法将不会触发子对象的布局请求
          *
          * 想要Child被绘制出来，需要调用Child的layout()
          */
        addViewInLayout(child, 0, lp, true);

        //指示在下一层次布局传递期间是否请求此视图的布局
        final boolean needToMeasure = child.isLayoutRequested();

        //对View进行测量
        if (needToMeasure) {
            int childWidthSpec = getChildMeasureSpec(getWidthMeasureSpec(),
                    getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin,
                    lp.width);
            int childHeightSpec = getChildMeasureSpec(getHeightMeasureSpec(),
                    getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin,
                    lp.height);
            child.measure(childWidthSpec, childHeightSpec);
        } else {
            cleanupLayoutState(child);
        }


        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();

        int gravity = lp.gravity;

        //对Gravity的情况进行处理
        if (gravity == -1) {
            gravity = Gravity.TOP | Gravity.START;
        }

        int layoutDirection = getLayoutDirection();
        final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
        final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

        int childLeft;
        int childTop;

        //通过不同的Gravity模式，计算的Card位置
        switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                childLeft = (getWidth() + getPaddingLeft() - getPaddingRight()  - w) / 2 +
                        lp.leftMargin - lp.rightMargin;
                break;
            case Gravity.END:
                childLeft = getWidth() + getPaddingRight() - w - lp.rightMargin;
                break;
            case Gravity.START:
            default:
                childLeft = getPaddingLeft() + lp.leftMargin;
                break;
        }

        switch (verticalGravity) {
            case Gravity.CENTER_VERTICAL:
                childTop = (getHeight() + getPaddingTop() - getPaddingBottom()  - h) / 2 +
                        lp.topMargin - lp.bottomMargin;
                break;
            case Gravity.BOTTOM:
                childTop = getHeight() - getPaddingBottom() - h - lp.bottomMargin;
                break;
            case Gravity.TOP:
            default:
                childTop = getPaddingTop() + lp.topMargin;
                break;
        }

        //设置Card的位置，不调用此方法addViewInLayout()无效果
        child.layout(childLeft, childTop, childLeft + w, childTop + h);
    }




    /**
     *  Set the top view and add the fling listener
     */
    private void setTopView() {
        if(getChildCount()>0){
            //拿到第一个Card
            mActiveCard = getChildAt(LAST_OBJECT_IN_STACK);
            if(mActiveCard!=null) {
                //移动Card的监听，具体展开在下边
                flingCardListener = new FlingCardListener(mActiveCard, mAdapter.getItem(0),
                        ROTATION_DEGREES, new FlingCardListener.FlingListener() {

                    @Override
                    public void onCardExited() {
                        mActiveCard = null;
                        mFlingListener.removeFirstObjectInAdapter();
                    }
                    //从左边离开回调
                    @Override
                    public void leftExit(Object dataObject) {
                        mFlingListener.onLeftCardExit(dataObject);
                    }
                    //从右边离开回调
                    @Override
                    public void rightExit(Object dataObject) {
                        mFlingListener.onRightCardExit(dataObject);
                    }
                    //点击回调
                    @Override
                    public void onClick(Object dataObject) {
                        if(mOnItemClickListener!=null)
                            mOnItemClickListener.onItemClicked(0, dataObject);

                    }
                    //手指移动不松开的回调
                    @Override
                    public void onScroll(float scrollProgressPercent) {
                        mFlingListener.onScroll(scrollProgressPercent);
                    }
                });
                mActiveCard.setOnTouchListener(flingCardListener);
            }
        }
    }

    public FlingCardListener getTopCardListener() throws NullPointerException{
        if(flingCardListener==null){
            throw new NullPointerException();
        }
        return flingCardListener;
    }

    public void setMaxVisible(int MAX_VISIBLE){
        this.MAX_VISIBLE = MAX_VISIBLE;
    }

    public void setMinStackInAdapter(int MIN_ADAPTER_STACK){
        this.MIN_ADAPTER_STACK = MIN_ADAPTER_STACK;
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }


    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }

        mAdapter = adapter;

        if (mAdapter != null  && mDataSetObserver == null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    public void setFlingListener(onFlingListener onFlingListener) {
        this.mFlingListener = onFlingListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }




    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FrameLayout.LayoutParams(getContext(), attrs);
    }


    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            requestLayout();
        }

    }


    public interface OnItemClickListener {
        void onItemClicked(int itemPosition, Object dataObject);
    }

    public interface onFlingListener {
        void removeFirstObjectInAdapter();
        void onLeftCardExit(Object dataObject);
        void onRightCardExit(Object dataObject);
        void onAdapterAboutToEmpty(int itemsInAdapter);
        void onScroll(float scrollProgressPercent);
    }


}

