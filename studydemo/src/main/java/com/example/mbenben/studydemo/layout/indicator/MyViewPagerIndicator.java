package com.example.mbenben.studydemo.layout.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbenben.studydemo.App;

import java.util.List;

/**
 * Created by MBENBEN on 2016/12/13.
 */

public class MyViewPagerIndicator extends LinearLayout{
    private Paint paint;
    private Path path;
    private int triangleWidth;
    private int triangleHeight;
    private int offsetLenght;
    private int initLenght;
    private int width,height;
    private ViewPager viewPager;

    public MyViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public MyViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("aaaa", "MyViewPagerIndicator:" + "getWidth:" + getWidth());
        init();
    }

    //此方法会在父View画子View的时候回调，因此我们的三角形在此处绘制.此方法会被循环回调
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        //移动画布，我们的三角形要随ViewPager的滑动而滑动，
        //而我们绘制三角形的坐标不变，因此我们的画布也要随之移动。
        canvas.translate(initLenght+offsetLenght,getHeight());
        canvas.drawPath(path,paint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    //此方法在每次View尺寸发生变化时回调。因此在此处我们可以拿到这个控件的宽和高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        triangleWidth=w/18;//三角形的宽（设置为屏幕宽度的十八分之一）
        //三角形的高，此处我直接用的宽的一半。也就是说我们绘制的是一个直角三角形
        triangleHeight=triangleWidth/2;
        //开始时三角形的初始偏移量。（就是第一个Tab宽度的一半然后减去半个三角形的宽。）
        initLenght=w/6-triangleHeight/2;
        width=w;
        height=h;
        //通过Path路径来画三角形
        path=new Path();
        //首先移动到（0,0）
        path.moveTo(0,0);
        //然后从（0,0）画到（三角形宽度，0）这个点
        path.lineTo(triangleWidth,0);
        /**
         *然后从（triangleWidth，0）画到三角形的定点（triangleWidth/2,-triangleHeight）
         * 因为我们的（0,0）是起点，所以顶点的y在（0,0）之上，根据安卓的特性所以顶点y为负
         */
        path.lineTo(triangleWidth/2,-triangleHeight);
        //闭合路径
        path.close();
    }


    private void init() {
        paint=new Paint();
        paint.setColor(Color.WHITE);
        //抗锯齿开启
        paint.setAntiAlias(true);
        //传入的此类，会使画笔的线段拐角有圆角效果
        paint.setPathEffect(new CornerPathEffect(3));
        paint.setStyle(Paint.Style.FILL);
    }
    public void setOffsetLength(int position,float offsetLenght){
        this.offsetLenght = (int) ((getWidth() / 3) * (offsetLenght + position));
        if (position>=1){
            //导航条的移动
            //此方法用于View的滑动。
            scrollTo((int) ((position-1)*getWidth()/3+getWidth()/3*offsetLenght),0);
        }
        //此方法作用在UI线程使自身重新绘制。重新调用draw()。
        invalidate();
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager=viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /**
                 * position:ViewPager中Fragment的位置。本例则为：0,1,2
                 * positionOffset：简单打印一下就会发现这个值。随着滑动的完成,数值的变化时0-1。
                 */
                setOffsetLength(position,positionOffset);
                setSelectTab(position);
            }

            @Override
            public void onPageSelected(int position) {
                //当我们选择特定的ViewPager内部对象时返回这个对象的位置。
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /**
                 * 基本上的滑动回调的状态都是这三种：
                 * SCROLL_STATE_IDLE 当前未滚动（滚动停止后调用）
                 * SCROLL_STATE_DRAGGING 当前正在被外部输入（如用户触摸输入）拖动（正在滑动，且手指不离开屏幕）
                 * SCROLL_STATE_SETTLING 目前正在动画到最终位置，而不在外部控制之下（惯性滑动）
                 */
            }
        });
        //设置默认Viewpager显示的页面
        viewPager.setCurrentItem(0);
        setSelectTab(0);
    }

    public void setTitles(List<String> titles){
        //先移除内部的全部子View（虽然现阶段根本啥也没有...）
        removeAllViews();
        //默认排列方式是居中的
        setGravity(Gravity.LEFT);
        if (titles!=null) {
            for (String title : titles) {
                TextView textView = new TextView(App.getInstance().getContext());
                textView.setText(title);
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                /**
                 * 注意getScreenWidth是获取屏幕宽度的自定义的方法。
                 * 我们自定义调用的方法会先于
                 */
                lp.width= (int) (getScreenWidth()/3);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                addView(textView,lp);
            }
            setTabClickEvent();
        }

    }

    private float getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    private float getViewHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
    //在XML加载完成后，回调
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
    private void setSelectTab(int position){
        resetTabColor();
        if (getChildAt(position) instanceof TextView){
            ((TextView) getChildAt(position)).setTextColor(Color.BLUE);
            ((TextView) getChildAt(position)).setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        }
    }

    private void resetTabColor() {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof TextView){
                ((TextView) getChildAt(i)).setTextColor(Color.WHITE);
                ((TextView) getChildAt(i)).setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            }
        }
    }
    private void setTabClickEvent(){
        resetTabColor();
        for (int i=0;i<getChildCount();i++){
            final int a=i;
            final View view=getChildAt(i);
            if (view instanceof  TextView){
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(a);
                    }
                });
            }
        }
    }
}
