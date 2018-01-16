package com.example.mbenben.studydemo.layout.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by MDove on 2018/1/1.
 */

public class MyCityItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mTextPaint,mBgPant;
    private int mTopHeight=100;
    public MyCityItemDecoration() {
        //简单初始化画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(36);

        mBgPant = new Paint();
        mBgPant.setColor(Color.BLACK);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        final int itemCount = state.getItemCount();
        final int childCount = parent.getChildCount();
        final int left = parent.getLeft() + parent.getPaddingLeft();
        final int right = parent.getRight() - parent.getPaddingRight();
        String preProvinceName;
        String currentProvinceName = null;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preProvinceName = currentProvinceName;
            currentProvinceName = mListener.getProvince(position);
            /**
             *  这里为什么没有使用mListener.getIsFirst()来进行判断。
             *  因为每一次的滑动都会造成这个方法的回调，因此我们每次都会重新去我们的Canvas进行重绘。
             *  如果我们单纯通过isFirst去判断，那么会造成这种情况：划出第一个数据时，我们的isFirst
             *  将不在为true，引起重绘之后，那么我们的分割线也将会消失。
             */
            if (currentProvinceName == null || TextUtils.equals(currentProvinceName, preProvinceName))
                continue;
            int viewBottom = view.getBottom();
            //分割线悬浮：因为bottom用于我们的分割线绘制，而它在非挤压状态下永远不会小于分割线高度（mTopHeight）
            float bottom = Math.max(mTopHeight, view.getTop());

            if (position + 1 < itemCount) {
                /**
                 *  挤压效果的计算：
                 *      挤压效果只会出现在下一组数据的分割线接触到当前悬浮的分割线。
                 *      我们只需要处理悬浮分割线慢慢上移即可，因为下一组分割线是正常移动的。而我们悬浮的分割线
                 *      被固定到了那个高度（bottom），因此我们来改变bottom的值即可。
                 */
                if (mListener.getIsFirst(position+1) && viewBottom < bottom) {
                    bottom = viewBottom;
                }
            }
            //绘制背景
            c.drawRect(left, bottom - mTopHeight, right, bottom, mBgPant);
            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            //绘制文字
            float baseLine = bottom - (mTopHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(currentProvinceName, left, baseLine, mTextPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //通过判断我们的数据结构的isFirst是否为true来决定是否需要设置内边距
        if (mListener.getIsFirst(parent.getChildAdapterPosition(view))) {
            outRect.top = mTopHeight;
        }
    }

    /**
     * 为什么需要写一个回调，那是因为：
     *     我们需要Adapter中的数据结构进行判断，然而我们没办法在这个类中获取到Adapter的数据结构。
     *     因此我们需要一个回调，让外部去为我们提供这个值。
     */
    private GetDataListener mListener;
    public void setGetDataListener(GetDataListener listener) {
        mListener = listener;
    }
    public interface GetDataListener {
        boolean getIsFirst(int position);
        String getProvince(int position);
    }
}
