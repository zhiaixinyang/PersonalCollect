package com.example.mbenben.studydemo.view.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.ScreenUtils;

/**
 * Created by MBENBEN on 2017/4/23.
 */

public class MyBezierView extends View {
    private Paint linePaint;
    private Paint ballPaint;
    private Path path;
    private int width,height;
    private int ctrlX,ctrlY;
    public MyBezierView(Context context) {
        this(context,null);
    }

    public MyBezierView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePaint =new Paint();
        linePaint.setStrokeWidth(12);
        linePaint.setColor(ContextCompat.getColor(App.getInstance().getContext(), R.color.red));
        linePaint.setAntiAlias(true);

        ballPaint =new Paint();
        ballPaint.setStrokeWidth(30);
        ballPaint.setColor(ContextCompat.getColor(App.getInstance().getContext(), R.color.black));
        ballPaint.setAntiAlias(true);

        path=new Path();
        width= ScreenUtils.getScreenWidth();
        height=ScreenUtils.getScreenHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(2*width/10,height/2);
        if (ctrlY>height/2+200){
            ctrlY=height/2+200;
        }else if(ctrlX<2*width/10-100){
            ctrlX=2*width/10-100;
        }
        path.quadTo(ctrlX,ctrlY,8*width/10,height/2);
        canvas.drawPath(path,linePaint);

    }
}
