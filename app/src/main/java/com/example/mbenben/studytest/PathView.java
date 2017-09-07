package com.example.mbenben.studytest;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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
import android.util.Log;
import android.view.View;

/**
 * Created by MBENBEN on 2017/3/1.
 */

public class PathView extends View {
    Path path;
    Paint paint;
    float length;
    ValueAnimator animator;

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.moveTo(50, 50);
        for (int i = 0; i < 4; i++) {
            path.lineTo(450 - i * 50, 50 + i * 50);
            path.lineTo(450 - i * 50, 450 - i * 50);
            path.lineTo(50 + i * 50, 450 - i * 50);
            path.lineTo(50 + i * 50, 100 + i * 50);
        }
        //path.quadTo(300, 200, 300, 300);

        // Measure the path
        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                paint.setPathEffect(createPathEffect(length, fraction));
                postInvalidate();
            }
        });

    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (isStart) {
            c.drawPath(path, paint);
        }
    }
    private boolean isStart=false;

    public void startPath(){
        isStart=true;
        animator.start();
    }

    private static PathEffect createPathEffect(float pathLength, float phase) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
                pathLength - phase * pathLength);
    }
}
