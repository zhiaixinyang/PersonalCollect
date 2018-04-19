package com.example.mbenben.studydemo.basenote.ballwidget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.mbenben.studydemo.basenote.ballwidget.anim.AnimUtils;

/**
 * @author MDove on 2018/4/19.
 */
public class RemoveWidgetView extends View {

    static final float SCALE_DEFAULT = 1.0f;
    static final float SCALE_LARGE = 1.5f;

    private final float size;
    private final float radius;
    private final Paint paint;
    private final int defaultColor;
    private final int overlappedColor;
    private final ValueAnimator sizeAnimator;
    private float scale = 1.0f;

    public RemoveWidgetView(@NonNull Context context, @NonNull Configuration configuration) {
        super(context);
        this.radius = configuration.radius();
        this.size = configuration.radius() * SCALE_LARGE * 2;
        this.paint = new Paint();
        this.defaultColor = configuration.crossColor();
        this.overlappedColor = configuration.crossOverlappedColor();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(configuration.crossStrokeWidth());
        paint.setColor(configuration.crossColor());
        paint.setStrokeCap(Paint.Cap.ROUND);
        sizeAnimator = new ValueAnimator();
        sizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public int getNormalWidth() {
        return (int) (radius * 2);
    }

    public int getOverlappedWidth() {
        return (int) size;
    }

    public float getOverlappedRadius() {
        return radius * SCALE_LARGE;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.makeMeasureSpec((int) (this.size), MeasureSpec.EXACTLY);
        super.onMeasure(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = canvas.getWidth() >> 1;
        int cy = canvas.getHeight() >> 1;
        float rad = radius * 0.75f;
        canvas.save();
        canvas.scale(scale, scale, cx, cy);
        canvas.drawCircle(cx, cy, rad, paint);
        drawCross(canvas, cx, cy, rad * 0.5f, 45);
        canvas.restore();
    }

    private void drawCross(@NonNull Canvas canvas, float cx, float cy, float radius, float startAngle) {
        drawLine(canvas, cx, cy, radius, startAngle);
        drawLine(canvas, cx, cy, radius, startAngle + 90);
    }

    private void drawLine(@NonNull Canvas canvas, float cx, float cy, float radius, float angle) {
        float x1 = AnimUtils.rotateX(cx, cy + radius, cx, cy, angle);
        float y1 = AnimUtils.rotateY(cx, cy + radius, cx, cy, angle);
        angle += 180;
        float x2 = AnimUtils.rotateX(cx, cy + radius, cx, cy, angle);
        float y2 = AnimUtils.rotateY(cx, cy + radius, cx, cy, angle);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    public void setOverlapped(boolean overlapped) {
        sizeAnimator.cancel();
        if (overlapped) {
            sizeAnimator.setFloatValues(scale, SCALE_LARGE);
            if (paint.getColor() != overlappedColor) {
                paint.setColor(overlappedColor);
                invalidate();
            }
        } else {
            sizeAnimator.setFloatValues(scale, SCALE_DEFAULT);
            if (paint.getColor() != defaultColor) {
                paint.setColor(defaultColor);
                invalidate();
            }
        }
        sizeAnimator.start();
    }
}
