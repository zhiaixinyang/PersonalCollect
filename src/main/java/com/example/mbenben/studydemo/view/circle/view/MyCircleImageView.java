package com.example.mbenben.studydemo.view.circle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MBENBEN on 2017/1/25.
 */
public class MyCircleImageView extends ImageView {
    private Paint paint;
    private int radius;
    private int borderColor;
    private int borderWidth;
    private Bitmap srcBitmap;
    private int bpWidth,bpHeight;

    public MyCircleImageView(Context context) {
        this(context,null);
    }

    public MyCircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.MyCircleImageView);
        borderColor=a.getColor(R.styleable.MyCircleImageView_border_color,
                ContextCompat.getColor(context,R.color.blue_dark));
        borderWidth= (int) a.getDimension(R.styleable.MyCircleImageView_border_width, DpUtils.dp2px(2));
        Drawable drawable=getDrawable();
        srcBitmap=((BitmapDrawable)drawable).getBitmap();
        bpWidth= srcBitmap.getWidth();
        bpHeight= srcBitmap.getHeight();
        paint=new Paint();
        paint.setAntiAlias(true);
    }
    int realWidth,realHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode==MeasureSpec.EXACTLY){
            realWidth=widthSize;
        }else{
            bpWidth=getPaddingLeft()+getPaddingRight()+bpWidth;
            if (widthMode==MeasureSpec.AT_MOST){
                realWidth=Math.min(widthSize,bpWidth);
            }
        }
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode==MeasureSpec.EXACTLY){
            realHeight=heightSize;
        }else{
            bpHeight=getPaddingLeft()+getPaddingRight()+bpHeight;
            if (heightMode==MeasureSpec.AT_MOST){
                realHeight=Math.min(bpHeight,heightSize);
            }
        }
        setMeasuredDimension(realWidth,realHeight);
    }
    private final RectF borderRect = new RectF();
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        radius=Math.min(realHeight,realWidth)/2;
        srcBitmap=Bitmap.createScaledBitmap(srcBitmap,Math.min(realHeight,realWidth),Math.min(realHeight,realWidth),false);
        Bitmap dst=Bitmap.createBitmap(realWidth,realHeight, Bitmap.Config.ARGB_8888);
//        Canvas dstCanvas=new Canvas(dst);
//        dstCanvas.drawCircle(radius,radius,radius,paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        dstCanvas.drawBitmap(srcBitmap,0,0,paint);
//
//        canvas.drawBitmap(dst,0,0,null);
        canvas.drawBitmap(dst,10,10,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap,0,0,paint);

        canvas.restore();
    }
}
