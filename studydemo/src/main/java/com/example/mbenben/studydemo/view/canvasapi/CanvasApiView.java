package com.example.mbenben.studydemo.view.canvasapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by MBENBEN on 2016/12/12.
 */

public class CanvasApiView extends View{
    public CanvasApiView(Context context) {
        super(context);
    }

    public CanvasApiView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //初始化一个画笔对象。
        Paint paint=new Paint(Color.BLACK);
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
        canvas.drawLine(100,100,400,100,paint);
        /**
         * 恢复画布状态，此后的代码仍会在一个未进行过任何操作的画布上。
         * 即：没有刚才rotate方法的旋转操作。
         */
        canvas.restore();
        canvas.drawLine(100,400,400,400,paint);
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
        canvas.drawText("Hahahaha",100,400,paint);
        //将画笔的模式设置为填充，那么我们的矩形就会被涂满。
        paint.setStyle(Paint.Style.FILL);
        //new Rect(100,500,300,600)的意思就是生成一个起点（100,500）终点（300,600）的区域
        //此方法和canvas.drawRect(100,500,300,600,paint)效果一样
        canvas.drawRect(new Rect(100,500,300,600),paint);
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
