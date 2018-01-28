package com.example.mbenben.studydemo.layout.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MDove on 2018/1/1.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint onDrawPaint, onDrawOverPaint;
    private Paint dashPaint;

    public MyItemDecoration() {
        onDrawPaint = new Paint();
        onDrawPaint.setColor(Color.BLACK);

        onDrawOverPaint = new Paint();
        onDrawOverPaint.setColor(Color.RED);

        dashPaint = new Paint();
        dashPaint.setAntiAlias(true);
        dashPaint.setColor(Color.BLACK);
        dashPaint.setStrokeWidth(2);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int left = 0;
        final int right = parent.getWidth();
        int viewSize = parent.getChildCount();
        for (int i = 0; i < viewSize; i++) {
            final Path path = new Path();
            final View childView = parent.getChildAt(i);
            final int bottom = childView.getBottom() + 50;
            path.moveTo(left, bottom);
            path.lineTo(right, bottom);
            c.drawPath(path, dashPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position % 2 == 0) {
            outRect.bottom = 100;
        }
    }
}
