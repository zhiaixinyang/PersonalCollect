package com.example.mbenben.studydemo.view.qqspacead;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.mbenben.studydemo.R;

/**
 * Created by MDove on 2018/10/13.
 *
 * 原项目Blog：https://www.jianshu.com/p/85f8e189d76f
 */

public class BeautView extends View {
    private Bitmap topBitmap;
    private Bitmap bottomBitmap;
    private Paint topPaint;
    private Paint bottomPaint;
    private int width;
    private int height;
    //锚点
    private Point povit = new Point();
    private Context mContext;
    private int radius;
    private PorterDuffXfermode mXfermode;
    public static final int MODE_UP = 1;
    public static final int MODE_DOWN = 2;
    private int mode = MODE_DOWN;

    public BeautView(Context context) {
        super(context);
    }

    public BeautView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;

        topBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.banner_2);
        bottomBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.banner_1);

        topPaint = new Paint();
        bottomPaint = new Paint();

        //src_over 模式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        //首先缩放bitmap，完全填满View的空间
        topBitmap = zoomBitmap(topBitmap, width, height);
        bottomBitmap = zoomBitmap(bottomBitmap, width, height);
        //填充画笔
        setPaintShader(topBitmap, topPaint);
        setPaintShader(bottomBitmap, bottomPaint);
    }

    /**
     * 设置画笔的填充
     */
    private void setPaintShader(Bitmap bitmap, Paint paint) {
        BitmapShader bShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float bScale = (float) Math.max(width * 1.0 / bitmap.getWidth(), height * 1.0 / bitmap.getHeight());
        Matrix bMatrix = new Matrix();
        bMatrix.setScale(bScale, bScale);
        bShader.setLocalMatrix(bMatrix);
        paint.setShader(bShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sc = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        //画笔填充了bitmap，绘制的时候只要写出绘制的区域dst就好
        canvas.drawRect(0, 0, width, height, bottomPaint);
        topPaint.setXfermode(mXfermode);
        //绘制圆src区域
        canvas.drawCircle(povit.x, povit.y, radius,  topPaint);
        topPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    public int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public int getWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 放大图片   会生成新的图片，资源浪费
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        float maxScale = Math.max(scaleWidth, scaleHeight);
        matrix.postScale(maxScale, maxScale);
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    /**
     * 计算view距顶top的时候radius的大小
     * @param top
     */
    public void setBitmapTop(int top) {
        if (top > 100) {
            povit.x = 0;
            povit.y = 0;
            radius = top - 100;
        } else {
            radius = 0;
        }
        invalidate();
    }

    /**
     * 计算距底bottom的时候radius的大小
     * @param bottom
     */
    public void setBitmapBottom(int bottom) {
        int screenHeight = getHeight(mContext);
        if(bottom < screenHeight - 100) {
            povit.x = width;
            povit.y = height;
            radius = screenHeight - 100 - bottom;
        } else {
            radius = 0;
        }
        invalidate();
    }

    public int getMode() {
        return mode;
    }

    /**
     * 设置mode  实际也是改变mode
     * 改变mode的时候需要交换画笔，也就是交换了bitmap绘制顺序，上面的bitmap去下面了，下面的上来了
     * @param mode
     */
    public void setMode(int mode) {
        this.mode = mode;
        Paint temp = topPaint;
        topPaint = bottomPaint;
        bottomPaint = temp;
    }
}

