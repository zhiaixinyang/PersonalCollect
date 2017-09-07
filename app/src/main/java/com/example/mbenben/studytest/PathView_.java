package com.example.mbenben.studytest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MBENBEN on 2017/3/1.
 */

public class PathView_ extends View {
    Path path;
    Paint paint;

    public PathView_(Context context) {
        super(context);
        init();
    }

    public PathView_(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathView_(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        paint.setPathEffect(new DashPathEffect(new float[]{400.0f,400.0f},200.0f));

    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        path=new Path();
        path.moveTo(100,100);
        path.lineTo(500,100);
        c.drawPath(path, paint);
    }

}
