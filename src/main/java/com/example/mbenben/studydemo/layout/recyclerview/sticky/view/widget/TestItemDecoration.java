package com.example.mbenben.studydemo.layout.recyclerview.sticky.view.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MBENBEN on 2017/10/8.
 */

public class TestItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * 可以实现类似绘制背景的效果，内容在正常的Item下面，被覆盖。正常我们要结合
     * getItemOffsets将正常Item错开，免得被正常Item覆盖掉
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
    //可以绘制在内容的上面，覆盖在正常的Item内容
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
    //实现Item的类似padding的效果，也就是让我们正常的Item进行移动
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
