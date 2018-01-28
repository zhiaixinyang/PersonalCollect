package com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Darren on 2016/12/27.
 * Email: 240336124@qq.com
 * Description: RecyclerView 分割线定制
 */
public class CategoryItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public CategoryItemDecoration(Drawable divider) {
        // 直接绘制颜色  只是用来测试
        mDivider = divider;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        // 计算需要绘制的区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            rect.top = childView.getBottom();
            rect.bottom = rect.top + mDivider.getIntrinsicHeight();
            // 直接利用Canvas去绘制
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 在每个子View的下面留出来画分割线
        outRect.bottom += mDivider.getIntrinsicHeight();
    }
}
