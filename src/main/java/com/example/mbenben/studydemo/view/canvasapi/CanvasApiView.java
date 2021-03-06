package com.example.mbenben.studydemo.view.canvasapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by MDove on 2016/12/12.
 */

public class CanvasApiView extends View {
    //特殊的虚线
    private Paint mDashPaint;
    private float mPhase;

    public CanvasApiView(Context context) {
        super(context);
    }

    public CanvasApiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDashPaint = new Paint();
        mDashPaint.setAntiAlias(true);
        mDashPaint.setColor(Color.BLACK);
        mDashPaint.setStyle(Paint.Style.STROKE);
        mDashPaint.setStrokeWidth(2.0f);
        /**
         * 指定了绘制8px的实线,再绘制10px的透明,再绘制8px的实线,再绘制10px的透明,依次重复来绘制达到path对象的长度。
         * mPhase参数指定了绘制的虚线相对了起始地址（Path起点）的取余偏移（对路径总长度）。
         */
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{8, 10, 8, 10}, mPhase);
        mDashPaint.setPathEffect(dashPathEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //初始化一个画笔对象。
        Paint paint = new Paint(Color.BLACK);
        //设置画笔实线宽度为2dp
        paint.setStrokeWidth(dp2px(2));
        //设置画笔写出的字体大小16sp
        paint.setTextSize(sp2px(26));
        //保存画布状态（此时保存的状态为未对画布进行任何操作）
        canvas.save();
        //顺旋转画布20度，此时的旋转是在保存之后
        canvas.rotate(20);
        /**
         * 在canvas这块画布用paint画笔；
         * 以（100,100）为起点，（200,100）为终点；
         * 用drawLine方法画线。
         */
        canvas.drawLine(100, 100, 400, 100, paint);
        /**
         * 恢复画布状态，此后的代码仍会在一个未进行过任何操作的画布上。
         * 即：没有刚才rotate方法的旋转操作。
         */
        canvas.restore();
        canvas.drawLine(100, 400, 400, 400, paint);
        canvas.save();
        /**
         * 将画笔的模式设置为不填充。
         * 什么意思呢？就是正常我们使用笔芯为2dp的画笔去画任何东西，
         * 默认情况是填充模式，也就是画什么东西我们都要用这个2dp的
         * 画笔反复涂抹，直到全部涂满图形。
         * 不过设置成这样呢？那就是不涂抹，也就是空心字的效果。
         */
        paint.setStyle(Paint.Style.STROKE);
        //在（100,400）坐标的位置写字，注意看效果：这个坐标是第一个字符的左下角的位置。
        canvas.drawText("Hahahaha", 100, 400, paint);
        //将画笔的模式设置为填充，那么我们的矩形就会被涂满。
        paint.setStyle(Paint.Style.FILL);
        //new Rect(100,500,300,600)的意思就是生成一个起点（100,500）终点（300,600）的区域
        //此方法和canvas.drawRect(100,500,300,600,paint)效果一样
        canvas.drawRect(new Rect(100, 500, 300, 600), paint);
        canvas.translate(800, 500);
        canvas.drawCircle(0, 0, 100, paint);

        canvas.restore();
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        for (int i = 0; i < 19; i++) {
            canvas.drawLine(-100, 0, -120, 0, paint);
            canvas.rotate(10);
        }
        canvas.restore();
        Path path = new Path();
        path.addArc(new RectF(0, 0, 500, 500), 0, 360);
        canvas.drawTextOnPath("https://github.com/zhiaixinyang/MyFirstApp", path, 0, 0, paint);
        dashDraw(canvas);
    }

    private void dashDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(200, 800);
        path.lineTo(800, 800);

        canvas.drawPath(path, mDashPaint);

        mPhase++;
        // 重绘，产生动画效果
        invalidate();
    }

    /**
     * 快捷虚线方案:
     * <shape xmlns:android="http://schemas.android.com/apk/res/android"
     * android:shape="line">
     * <stroke
     * android:width="1dp"
     * android:color="#4cffffff"
     * android:dashGap="5dp"
     * android:dashWidth="5dp" />
     * </shape>
     * <p>
     * <View>
     * ......
     * android:background="@drawable/bg_dotted_line"
     * android:layerType="software"
     * </View>
     */

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
