package com.example.mbenben.studydemo.layout.nav.scroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2016/12/23.
 */

public class ScorllLayout extends LinearLayout{
    private View one;
    private View two;
    private View three;
    private View topPanel,iv_avatar;
    private int topViewHeight;
    private int avatarLeft;
    private Scroller scroller;
    private float lastY;
    private boolean dragging;
    private Paint paint;
    private Path path;

    public ScorllLayout(Context context) {
        super(context);
        init(context);
    }

    public ScorllLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        scroller = new Scroller(context);
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawPath(path,paint);
        super.dispatchDraw(canvas);
    }

    private float downY=0;
    private float scale=1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished())
                    scroller.abortAnimation();
                //记录手指按下时的坐标
                lastY = y;
                downY=event.getY();
                //消费点击事件
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - lastY;
                scrollBy(0, (int) -dy);
                lastY = y;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > topViewHeight) {
            y = topViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        one=getChildAt(0);
        two=getChildAt(1);
        three=getChildAt(2);
        topPanel=findViewById(R.id.topPanel);
        iv_avatar=findViewById(R.id.iv_image);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        one.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = three.getLayoutParams();
        params.height = getMeasuredHeight() - two.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), one.getMeasuredHeight() + two.getMeasuredHeight() + three.getMeasuredHeight());
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topViewHeight = one.getMeasuredHeight();
        avatarLeft=getMeasuredWidth()/2;
        path=new Path();
        path.moveTo(avatarLeft-25,topViewHeight);
        path.lineTo(avatarLeft+25,topViewHeight);
        path.lineTo(avatarLeft,topViewHeight-25);
        path.close();
    }
}
