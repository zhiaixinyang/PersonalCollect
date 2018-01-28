package com.example.mbenben.studydemo.layout.indicator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import static com.example.mbenben.studydemo.layout.indicator.view.ColorTrackTextView.Direction.DIRECTION_LEFT;
import static com.example.mbenben.studydemo.layout.indicator.view.ColorTrackTextView.Direction.DIRECTION_RIGHT;

/**
 * Created by MDove on 2017/10/25.
 */

public class ColorTrackTextView extends TextView {
    // 默认的字体颜色的画笔
    private Paint mOriginPaint;
    // 改变的字体颜色的画笔
    private Paint mChangePaint;
    // 当前的进度
    private float mCurrentProgress = 0.6f;
    private String TAG = "CTTV";

    // 当前文本
    private String mText;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mOriginPaint = getPaintByColor(Color.BLACK);
        mChangePaint = getPaintByColor(Color.RED);
    }

    /**
     * 获取画笔根据不同的颜色
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        // 仿抖动
        paint.setDither(true);
        paint.setColor(color);
        // 字体的大小设置为TextView的文字大小
        paint.setTextSize(getTextSize());
        return paint;
    }


    // 当前朝向
    private Direction mDirection = DIRECTION_LEFT;
    // 绘制的朝向枚举
    public enum Direction {
        DIRECTION_LEFT, DIRECTION_RIGHT
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取当前文本
        mText = getText().toString();
        // 获取控件宽度
        int width = getWidth();
        if (!TextUtils.isEmpty(mText)) {
            // 根据当前进度计算中间位置
            int middle = (int) (width * mCurrentProgress);

            // 根据不同的朝向去画字体
            if (mDirection == DIRECTION_LEFT) {
                drawOriginDirectionLeft(canvas, middle);
                drawChangeDirectionLeft(canvas, middle);
            }
            if (mDirection == DIRECTION_RIGHT) {
                drawOriginDirectionRight(canvas, middle);
                drawChangeDirectionRight(canvas, middle);
            }
        }
    }

    /**
     * 画朝向右边变色字体
     */
    private void drawChangeDirectionRight(Canvas canvas, int middle) {
        drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
    }

    /**
     * 画朝向左边默认色字体
     */
    private void drawOriginDirectionRight(Canvas canvas, int middle) {
        drawText(canvas, mOriginPaint, 0, getWidth() - middle);
    }

    /**
     * 画朝向左边变色字体
     */
    private void drawChangeDirectionLeft(Canvas canvas, int middle) {
        drawText(canvas, mChangePaint, 0, middle);
    }

    /**
     * 画朝向左边默认色字体
     */
    private void drawOriginDirectionLeft(Canvas canvas, int middle) {
        drawText(canvas, mOriginPaint, middle, getWidth());
    }

    /**
     * 设置当前的进度
     *
     * @param currentProgress 当前进度
     */
    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        // 重新绘制
        invalidate();
    }

    /**
     * 设置绘制方向，从右到左或者从左到右
     *
     * @param direction 绘制方向
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    /**
     * 画变色的字体部分
     */
    private void drawChange(Canvas canvas, int middle) {
        drawText(canvas, mChangePaint, 0, middle);
    }

    /**
     * 画不变色的字体部分
     */
    private void drawOrigin(Canvas canvas, int middle) {
        drawText(canvas, mOriginPaint, middle, getWidth());
    }

    /**
     * 绘制文本根据指定的位置
     *
     * @param canvas canvas画布
     * @param paint  画笔
     * @param startX 开始的位置
     * @param endX   结束的位置
     */
    private void drawText(Canvas canvas, Paint paint, int startX, int endX) {
        // 保存画笔状态
        canvas.save();
        // 截取绘制的内容，待会就只会绘制clipRect设置的参数部分
        canvas.clipRect(startX, 0, endX, getHeight());
        // 获取文字的范围
        Rect bounds = new Rect();
        mOriginPaint.getTextBounds(mText, 0, mText.length(), bounds);
        // 获取文字的Metrics 用来计算基线
        Paint.FontMetricsInt fontMetrics = mOriginPaint.getFontMetricsInt();
        // 获取文字的宽高
        int fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        // 计算基线到中心点的位置
        int offY = fontTotalHeight / 2 - fontMetrics.bottom;
        // 计算基线位置
        int baseline = (getMeasuredHeight() + fontTotalHeight) / 2 - offY;
        canvas.drawText(mText, getMeasuredWidth() / 2 - bounds.width() / 2, baseline, paint);
        // 释放画笔状态
        canvas.restore();
    }
}