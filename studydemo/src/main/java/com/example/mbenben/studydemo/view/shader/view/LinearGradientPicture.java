package com.example.mbenben.studydemo.view.shader.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.mbenben.studydemo.R;


/**
 * 图片倒影
 */

public class LinearGradientPicture extends View {


    public LinearGradientPicture(Context context) {
        this(context, null);
    }

    public LinearGradientPicture(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Bitmap bitmap;
    private Bitmap bitmapShadow;
    private Paint mBmpShadowPaint;
    private Paint mXfPaint;
    private Xfermode xfermode;

    public LinearGradientPicture(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pintu);
        mBmpShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXfPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        xfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int bmpW = bitmap.getWidth();
        //我们设置图片的宽度为屏幕的 2/3
        float ratioW = w * 1.0f / bmpW * 2 / 3;

        bitmap = BmpUtils.zoomImg(bitmap, (int) (bitmap.getWidth() * ratioW), (int) (bitmap.getHeight() * ratioW));

        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        bitmapShadow = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        LinearGradient mLgShader = new LinearGradient(0, bitmap.getHeight(), 0, bitmap.getHeight() * 2, new int[]{0x00000000,0x11000000,0xaa000000,Color.WHITE},new float[]{0,0.1f,0.4f,0.6f}, Shader.TileMode.REPEAT);
        mBmpShadowPaint.setShader(mLgShader);

        shadowRectF = new RectF(0, bitmap.getHeight(), bitmap.getWidth(), bitmap.getHeight() * 2);


    }

    private RectF shadowRectF;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 6, getHeight() / 10);

        canvas.drawBitmap(bitmap, 0, 0, null);


        int layerID = canvas.saveLayer(shadowRectF, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawRect(shadowRectF, mBmpShadowPaint);
        mXfPaint.setXfermode(xfermode);
        canvas.drawBitmap(bitmapShadow, 0, bitmap.getHeight(), mXfPaint);
        mXfPaint.setXfermode(null);


        canvas.restoreToCount(layerID);

    }


}
