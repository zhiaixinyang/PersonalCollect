package com.example.mbenben.studydemo.anim.camera;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ScreenUtils;

/**
 * Created by MDove on 2017/9/24.
 */

public class CameraView extends View {
    private Camera mCamera;
    private int mCenterX, mCenterY;
    private Paint mPaint;
    private Bitmap mBg;
    private Bitmap mWord;
    private int mTop;
    private int mLeft;
    private float mRotation;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mRotation = 0;

        mCamera = new Camera();
        mCenterX = ScreenUtils.getScreenWidth() / 2;
        mCenterY = ScreenUtils.getScreenHeight() / 2;

        mBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.pintu);

        //关闭硬件加速，不然相同的ComposeShader无效(会很卡)
        //setLayerType(LAYER_TYPE_SOFTWARE, null);
        Bitmap bitmapBg = BitmapFactory.decodeResource(context.getResources(), R.drawable.pintu);
        mWord = BitmapFactory.decodeResource(context.getResources(), R.drawable.btm_lu);
        mPaint = new Paint();
        BitmapShader bgShader = new BitmapShader(bitmapBg, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        BitmapShader wordShader = new BitmapShader(mWord, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        ComposeShader composeShader = new ComposeShader(bgShader, wordShader, PorterDuff.Mode.SRC_OVER);

        mPaint.setShader(composeShader);

        mLeft = mCenterX - bitmapBg.getWidth() / 2;
        mTop = mCenterY - bitmapBg.getHeight() / 2;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "cameraRotation", 0, 180);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }
    private RectF rectF = null;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rectF == null) {
            rectF=new RectF(0,0,600,200);
        }else{
            canvas.save();
            //离屏缓冲，避免一系列绘制问题
            int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(mBg,0,0,mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            canvas.drawBitmap(mWord,0,0,mPaint);

            mPaint.setXfermode(null);
            canvas.restoreToCount(saved);

        }
        //一个正方的的路径
//        Path squarePath=new Path();
//        squarePath.moveTo(0- DpUtils.dp2px(30),0- DpUtils.dp2px(30));
//        squarePath.lineTo(0- DpUtils.dp2px(30),0+ DpUtils.dp2px(60));
//        squarePath.lineTo(0+ DpUtils.dp2px(60),0+ DpUtils.dp2px(60));
//        squarePath.lineTo(0+ DpUtils.dp2px(60),0- DpUtils.dp2px(60));
//        squarePath.lineTo(0- DpUtils.dp2px(30),0- DpUtils.dp2px(30));
//        canvas.clipPath(squarePath);

//        canvas.save();
//        // 保存 Camera 的状态
//        mCamera.save();
//        // 旋转 Camera 的三维空间
//        mCamera.rotateX((int) mRotation);
//        //移动相机z轴位置，负数，也就是更远离屏幕，这样投射出来的图像就会相对较小
//        //mCamera.setLocation(0, 0, -1500);
//        canvas.translate(mCenterX, mCenterY); // 旋转之后把投影移动回来
//        mCamera.applyToCanvas(canvas); // 把旋转投影到 Canvas
//        canvas.translate(-mCenterX, -mCenterY); // 旋转之前把绘制内容移动到轴心（原点）
//        mCamera.restore(); // 恢复 Camera 的状态
//
//        canvas.drawBitmap(mBg, mLeft, mTop, mPaint);
//        canvas.restore();

        int bitmapWidth = mBg.getWidth();
        int bitmapHeight = mBg.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        // 第一遍绘制：上半部分
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), centerY);
        canvas.drawBitmap(mBg, x, y, mPaint);
        canvas.restore();

        // 第二遍绘制：下半部分
        canvas.save();

        if (mRotation < 90) {
            canvas.clipRect(0, centerY, getWidth(), getHeight());
        } else {
            canvas.clipRect(0, 0, getWidth(), centerY);
        }
        mCamera.save();
        mCamera.rotateX(mRotation);
        canvas.translate(centerX, centerY);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-centerX, -centerY);
        mCamera.restore();

        canvas.drawBitmap(mBg, x, y, mPaint);
        canvas.restore();
    }

    public void setCameraRotation(float cameraRotation) {
        mRotation = cameraRotation;
        invalidate();
    }

    public float getCameraRotation() {
        return mRotation;
    }
}
