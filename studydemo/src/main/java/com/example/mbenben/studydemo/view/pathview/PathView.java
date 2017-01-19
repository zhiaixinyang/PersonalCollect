package com.example.mbenben.studydemo.view.pathview;



import android.animation.ObjectAnimator;
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
 * Created by MBENBEN on 2017/1/4.
 *
 * 原作者项目GitHub：https://github.com/mcxtzhang/PathAnimView
 */

public class PathView extends View
{
    Path path;
    Paint paint;
    float length;

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
        path.lineTo(50, 500);
        path.lineTo(200, 500);
        path.lineTo(200, 300);
        path.lineTo(350, 300);
        path.quadTo(360,100,400,310);

        // Measure the path
        PathMeasure measure = new PathMeasure(path, false);
        length = measure.getLength();

        float[] intervals = new float[]{length, length};

        ObjectAnimator animator = ObjectAnimator.ofFloat(PathView.this, "phase", 0.0f, 1.0f);
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 此方法是配合属性动画的。（自定义属性动画效果）
     * setPhase->对应ObjectAnimator.ofFloat(PathView.this, "phase", 0.0f, 1.0f)
     * 中的"phase"
     */
    public void setPhase(float phase) {
        Log.d("pathview", "setPhase called with:" + String.valueOf(phase));
        paint.setPathEffect(createPathEffect(length, phase, 0.0f));
        invalidate();//will calll onDraw
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[] { pathLength, pathLength },
                pathLength - phase * pathLength);
//        return new DashPathEffect(new float[] { phase*pathLength, pathLength },
//               0);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        c.drawPath(path, paint);
    }
}

