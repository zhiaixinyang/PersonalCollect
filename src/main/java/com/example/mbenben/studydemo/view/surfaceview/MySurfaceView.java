package com.example.mbenben.studydemo.view.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by MDove on 18/1/16.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPath;
    private boolean isDrawing;
    private int mLastX, mLastY;
    private Canvas mCanvas;

    public MySurfaceView(Context context) {
        super(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            try {
                if (mCanvas != null) {
                    mCanvas = mHolder.lockCanvas();
                    mCanvas.drawColor(Color.WHITE);
                    mCanvas.drawPath(mPath, mPaint);
                }
            } finally {
                if (mCanvas != null) {
                    /**绘制结束后解锁显示在屏幕上**/
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                if (dx >= 3 || dy >= 3) {
                    mPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                }
                mLastX = x;
                mLastY = y;
                Log.d("aaa", "mLastX:" + mLastX + "mLastY:" + mLastY + "x:" + x + "y:" + y + "dx:" + dx + "dy:" + dy);
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return true;
    }
}
