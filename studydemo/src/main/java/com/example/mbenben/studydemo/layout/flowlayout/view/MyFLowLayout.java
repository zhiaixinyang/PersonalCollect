package com.example.mbenben.studydemo.layout.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MBENBEN on 2017/2/18.
 */

public class MyFLowLayout extends ViewGroup {
    private int layoutWidth;
    private int layoutHeight;

    public MyFLowLayout(Context context) {
        super(context);
    }

    public MyFLowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFLowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int rowWidth = 0, rowHeight = 0, line = 1;

        if (widthMode == MeasureSpec.AT_MOST) {
            layoutWidth = widthSize;
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                int childWidth = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();

                rowWidth += childWidth;
                //如果当前一行满了
                if (rowWidth == widthSize) {
                    rowWidth = childWidth;
                    line++;
                }
                rowHeight = childHeight * line;
            }

        }
        layoutWidth = rowWidth + getPaddingLeft() + getPaddingRight();
        layoutHeight = rowHeight + getPaddingBottom() + getPaddingTop();

        setMeasuredDimension(layoutWidth, layoutHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft=getPaddingLeft();
        int paddingTop=getPaddingTop();
        int parentWidth=r-l-getPaddingRight();
        int line=0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int localLeft=paddingLeft+i*view.getMeasuredWidth();
            int localRight=paddingLeft+(i+1)*view.getMeasuredWidth();
            int localTop=paddingTop+line*view.getMeasuredHeight();
            int localBottom=paddingTop+(line+1)*view.getMeasuredHeight();
            if (localRight>parentWidth){
                line++;
                localTop=paddingTop+line*view.getHeight();
            }
            view.layout(localLeft,localRight,localTop,localBottom);
        }
    }
}
