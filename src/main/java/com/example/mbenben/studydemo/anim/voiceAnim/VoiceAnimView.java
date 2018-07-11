package com.example.mbenben.studydemo.anim.voiceAnim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mbenben.studydemo.R;

/**
 * 原项目GitHub：https://github.com/ChestnutPlus/ModulesUi
 */
public class VoiceAnimView extends View {

    private Bitmap bgBitmap;
    private Paint paint;
    private int pointWidth = 6;
    private VoiceAnimPoint[] points;
    private int pointIndex = 0;
    private boolean isRevert = false;
    private boolean isStart = false;
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            VoiceAnimView.this.invalidate();
            VoiceAnimView.this.postDelayed(r, 42);
        }
    };

    public VoiceAnimView(Context context) {
        super(context);
    }

    public VoiceAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ai_chat_icon);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xffC2E379);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(pointWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        points = new VoiceAnimPoint[5];
        points[0] = new VoiceAnimPoint(getWidth() / 2 - 24, getHeight() / 2, 16f, 14.7f, 11f, 7.3f);
        points[1] = new VoiceAnimPoint(getWidth() / 2 - 12, getHeight() / 2, 24f, 21.7f, 15f, 8.3f);
        points[2] = new VoiceAnimPoint(getWidth() / 2, getHeight() / 2, 38f, 33.9f, 22f, 10.1f);
        points[3] = new VoiceAnimPoint(getWidth() / 2 + 12, getHeight() / 2, 24f, 21.7f, 15f, 8.3f);
        points[4] = new VoiceAnimPoint(getWidth() / 2 + 24, getHeight() / 2, 16f, 14.7f, 11f, 7.3f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bgBitmap.getWidth(), bgBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBitmap, getWidth() / 2 - bgBitmap.getWidth() / 2, getHeight() / 2 - bgBitmap.getHeight() / 2, paint);
        for (int i = 0; i < points.length; i++) {
            VoiceAnimPoint point = points[i];
            int y = indexChangeFunc(pointIndex - i * 2);
            switch (y) {
                case 0:
                    canvas.drawLine(point.centerX, point.centerY, point.centerX, point.centerY + 0.01f, paint);
                    break;
                case 2:
                    canvas.drawLine(point.centerX, point.centerY - point.halfHeight / 2 + pointWidth / 2,
                            point.centerX, point.centerY + point.halfHeight / 2 - pointWidth / 2, paint);
                    break;
                case 4:
                    canvas.drawLine(point.centerX, point.centerY - point.maxHeight / 2 + pointWidth / 2,
                            point.centerX, point.centerY + point.maxHeight / 2 - pointWidth / 2, paint);
                    break;
                case 1:
                    canvas.drawLine(point.centerX, point.centerY - point.oneHeight / 2 + pointWidth / 2,
                            point.centerX, point.centerY + point.oneHeight / 2 - pointWidth / 2, paint);
                    break;
                case 3:
                    canvas.drawLine(point.centerX, point.centerY - point.threeHeight / 2 + pointWidth / 2,
                            point.centerX, point.centerY + point.threeHeight / 2 - pointWidth / 2, paint);
                    break;
            }
        }
        if (!isRevert) {
            pointIndex++;
        } else {
            pointIndex--;
        }
        if (pointIndex == 23) {
            isRevert = true;
            pointIndex = 17;
        } else if (pointIndex == -6) {
            pointIndex = 0;
            isRevert = false;
        }
    }

    public void startAnim() {
        if (!isStart) {
            isStart = true;
            this.post(r);
        }
    }

    public void stopAnim() {
        if (isStart) {
            isStart = false;
            this.removeCallbacks(r);
        }
    }

    /**
     * 动画轨迹其实符合一个函数
     * 这里传入对应的x，返回函数的y
     *
     * @param x 位置 [0,17]，相隔2，作为其他元素的坐标
     * @return y 4 ： 最大， 3：threeHeight， 2： 一半， 1：oneHeight， 0 ：0 。
     */
    private int indexChangeFunc(int x) {
        if (x < 0)
            return 0;
        else if (x < 4)
            return x;
        else if (x < 8)
            return -x + 8;
        else
            return 0;
    }
}
