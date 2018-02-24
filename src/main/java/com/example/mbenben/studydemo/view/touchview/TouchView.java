package com.example.mbenben.studydemo.view.touchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MDove on 18/2/24.
 *
 * 原项目GitHub：https://github.com/Zhaoss/GestureViewDemo
 */

public class TouchView  extends View {
    private float downX;
    private float downY;
    private float firstX;
    private float firstY;
    private OnClickListener listener;
    private boolean clickable = true;
    private float whRatio;
    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context) {
        super(context);
    }

    /**
     * 因为重写的onTouch, 那么onClick也必须要重写
     */
    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    private float lastDis;
    private float coreX;
    private float coreY;
    private boolean doubleMove = false;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //在使用layoutParams设置宽高时, Margin值也会作为MAX宽高的一部分生效, 所以请设置位置的时候不用使用Margin值, 尽量用setX()这种
        if(minWidth == 0){
            //算出宽高比
            whRatio = getWidth()*1f/getHeight();
            //我设置的最小宽度是默认宽度的2分之1 这个可以随便设置
            minWidth = getWidth()/2;
            ViewGroup parent = (ViewGroup) getParent();
            maxWidth = parent.getWidth();//设置最大宽度是父view的宽度

            minHeight = getHeight()/2;
            maxHeight = (int) (maxWidth / whRatio);
        }
    }

    View copyView;
    float lastRota;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = downX = event.getRawX();
                firstY = downY = event.getRawY();
                //得到view的中心点坐标
                coreX = getWidth()/2+getX();
                coreY = getHeight()/2+getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //得到手指触摸点数量
                int pointerCount = event.getPointerCount();
                //双点触摸事件
                if(pointerCount >= 2){
                    doubleMove = true;//双指手势触摸状态
                    float distance = getSlideDis(event);
                    float spaceRotation = getRotation(event);
                    //复制一个同样的copyView盖到原原view上
                    if(copyView == null){
                        copyView = new View(getContext());
                        copyView.setX(getX());
                        copyView.setY(getY());
                        copyView.setRotation(getRotation());
                        copyView.setBackground(getBackground());
                        copyView.setLayoutParams(new ViewGroup.LayoutParams(getWidth(), getHeight()));
                        ViewGroup parent = (ViewGroup) getParent();
                        parent.addView(copyView);
                        //把原view隐藏
                        setAlpha(0);
                    }else{
                        //放大缩小逻辑
                        int slide = (int) (lastDis - distance);
                        int slide2 = (int) (slide/whRatio);
                        ViewGroup.LayoutParams layoutParams = getLayoutParams();
                        layoutParams.width = (layoutParams.width - slide);
                        layoutParams.height = layoutParams.height - slide2;

                        if(layoutParams.width > maxWidth || layoutParams.height > maxHeight){//判断长宽最大值
                            layoutParams.width = maxWidth;
                            layoutParams.height = maxHeight;
                        }else if(layoutParams.width < minWidth || layoutParams.height < minHeight){
                            layoutParams.width = minWidth;
                            layoutParams.height = minHeight;
                        }

                        //当这个view宽高发生变化时, 我们要此view的中心点还是保持不变, 所以要重新设置x和y轴的坐标
                        setLayoutParams(layoutParams);
                        float x = coreX - getWidth() / 2;
                        float y = coreY - getHeight() / 2;
                        setX(x);
                        setY(y);
                        copyView.setX(x);
                        copyView.setY(y);

                        //将copyView的长宽变成和原view一样大小, 这是为了每次手势缩放时视觉保持一致
                        ViewGroup.LayoutParams layoutParams1 = copyView.getLayoutParams();
                        layoutParams1.width = layoutParams.width;
                        layoutParams1.height = layoutParams.height;
                        copyView.setLayoutParams(layoutParams1);

                        if(lastRota != 0){
                            float f = lastRota-spaceRotation;
                            //重点来了, 这时我们对view进行的旋转操作全部施加在copyView上了
                            //这样我们得到的旋转参数每次就正常了, 因为我们的手势是操控在原view上的
                            copyView.setRotation(copyView.getRotation()-f);
                        }
                    }
                    //记录本次双指旋转度数
                    lastRota = spaceRotation;
                    //记录本次双指相距距离
                    lastDis = distance;
                }else if(!doubleMove && pointerCount == 1){//单点移动事件
                    float moveX = event.getRawX();
                    float moveY = event.getRawY();
                    //根据上次坐标, 计算出本次手指移动距离
                    float slideX = moveX - downX;
                    float slideY = moveY - downY;
                    //设置view坐标
                    setX(getX()+slideX);
                    setY(getY()+slideY);
                    //记录view移动后的坐标值
                    downX = moveX;
                    downY = moveY;
                }
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时, 家境copyView上的徐娜转参数赋值回去
                if(copyView != null){
                    setAlpha(1);
                    setRotation(copyView.getRotation());
                    ViewGroup parent = (ViewGroup) getParent();
                    parent.removeView(copyView);
                }

                //初始化所有参数信息
                lastRota = 0;
                copyView = null;
                doubleMove = false;
                lastDis = 0;

                //因为重写了onTouch, 所以也要重写单击事件
                float upX = event.getRawX();
                float upY = event.getRawY();
                if (Math.abs(upX - firstX) < 10 && Math.abs(upY - firstY) < 10 && clickable) {
                    if (listener != null) listener.onClick(this);//触发单击
                }
                break;
        }
        return true;
    }

    /**
     * 获取手指间的旋转角度
     */
    private float getRotation(MotionEvent event) {

        double deltaX = event.getX(0) - event.getX(1);
        double deltaY = event.getY(0) - event.getY(1);
        double radians = Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(radians);
    }

    /**
     * 获取手指间的距离
     */
    private float getSlideDis(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
