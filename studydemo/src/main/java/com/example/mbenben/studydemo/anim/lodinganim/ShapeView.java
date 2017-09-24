package com.example.mbenben.studydemo.anim.lodinganim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MDove on 2017/9/23.
 */

public class ShapeView extends View {

    private Paint paint;
    private ViewType viewType;

    public enum ViewType {
        Circle, Triangle, Square
    }

    public ShapeView(Context context) {
        this(context, null);

    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(DpUtils.dp2px(4));
        paint.setStyle(Paint.Style.STROKE);
        viewType = ViewType.Circle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        switch (viewType) {
            case Circle:
                canvas.drawCircle(0, 0, DpUtils.dp2px(10), paint);
                break;
            case Square:
                canvas.drawRect(0 - DpUtils.dp2px(10), 0 - DpUtils.dp2px(10),
                        0 + DpUtils.dp2px(10), 0 + DpUtils.dp2px(10), paint);
                break;
            case Triangle:
                Path path = new Path();
                int move = (int) Math.sqrt((DpUtils.dp2px(20) * DpUtils.dp2px(20)) / 2);

                path.moveTo(0 - move, 0 + move);
                path.lineTo(move, 0 + move);
                path.lineTo(0, 0 - move);
                path.lineTo(0 - move, 0 + move);
                canvas.drawPath(path,paint);
                break;
            default:
                break;
        }
        canvas.restore();
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
        invalidate();
    }
}
