package com.example.mbenben.studydemo.basenote;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yinshangtai on 2017/12/7.
 */

public class WaterMarkDrawable extends Drawable {

    private Paint paint = new Paint();

    private String mMark;
    private int mSize = 40;
    /**
     * 每行文字间距
     */
    private int mRowSpacing;
    private int mColor = -1;
    /**
     * 行内文字间距
     */
    private int mColSpacing;

    public WaterMarkDrawable(String mark) {
        this.mMark = mark;
    }

    public WaterMarkDrawable(String mark, int size, int rowSpacing) {
        this.mMark = mark;
        mSize = size;
        mRowSpacing = rowSpacing;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        int width = getBounds().width();
        int height = getBounds().height();

        canvas.drawColor(Color.parseColor("#00000000"));
        if (mColor == -1) {
            paint.setColor(Color.parseColor("#ebebeb"));
        } else {
            paint.setColor(mColor);
        }
        paint.setAntiAlias(true);
        paint.setTextSize(mSize);
        canvas.save();
        canvas.rotate(-30);

        float textWidth = paint.measureText(mMark);
        if (mRowSpacing <= 0) {
            mRowSpacing = height / 10;
        }
        int totalColSpacing;
        if (mColSpacing <= 0) {
            totalColSpacing = (int) (textWidth * 2);
        } else {
            totalColSpacing = (int) (textWidth + mColSpacing);
        }
        int index = 0;
        //高度取原来的对角线
        int diagonal = (int) Math.sqrt(width * width + height * height);
        for (int positionY = mRowSpacing; positionY <= (diagonal + mRowSpacing); positionY += mRowSpacing) {
            float fromX = -width + (index++ % 2) * textWidth;
            for (float positionX = fromX; positionX < width; positionX += totalColSpacing) {
                canvas.drawText(mMark, positionX, positionY, paint);
            }
        }

        canvas.restore();

    }

    /**
     * 设置每行文字间距
     * @param spacing
     */
    public void setRowSpacing(int spacing) {
        mRowSpacing = spacing;
    }

    /**
     * 设置行内文字间距
     * @param spacing
     */
    public void setColSpacing(int spacing) {
        mColSpacing = spacing;
    }

    /**
     * 设置水印字体颜色
     * @param color 需包含alpha值
     */
    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter filter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

}
