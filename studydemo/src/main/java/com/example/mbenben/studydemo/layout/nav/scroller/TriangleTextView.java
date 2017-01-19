package com.example.mbenben.studydemo.layout.nav.scroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by MBENBEN on 2016/12/23.
 */

public class TriangleTextView extends TextView{
    private Paint paint;
    private Path path;

    public TriangleTextView(Context context) {
        super(context);
        init();
    }

    public TriangleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path=new Path();
        path.moveTo(100,0);
        path.lineTo(200,0);
        path.lineTo(150,50);
        path.close();
        canvas.drawPath(path,paint);
        super.onDraw(canvas);
    }

}
