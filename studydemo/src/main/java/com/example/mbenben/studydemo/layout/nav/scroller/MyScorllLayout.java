package com.example.mbenben.studydemo.layout.nav.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by MBENBEN on 2016/12/23.
 */

public class MyScorllLayout extends LinearLayout{
    private View one;
    private View two;
    private View three;
    private int topViewHeight;
    private Scroller scroller;
    private int touchSlop;
    private VelocityTracker velocityTracker;
    private int maximumVelocity, minimumVelocity;
    private float lastY;
    private boolean dragging;

    public MyScorllLayout(Context context) {
        super(context);
    }

    public MyScorllLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * 滚动操作：当我们相关做滚动效果时，可以使用此类相关内容进行
         * 最终通过invalidate()重绘界面
         */
        scroller = new Scroller(context);
        /**
         * 此变量可以理解为系统所能识别的最小滑动距离。
         * 此类并不是必须在Scroller相关操作中存在。
         * 它在本例的作用是配合实现惯性滑动
         * 简单说：当我们的手指滑动速度满足一定的值（就是这个touchSlop变量对应的值）时，
         * 就可以判断达到滑动阈值，设置Scroller进行对应滑动，
         * 以及相关的惯性滑动
         * PS：并不是必须。它存在的效果是判断滑动距离达到一定值才会触发相应的操作
         */
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        /**
         * 这俩个变量：仅仅是通过系统ViewConfiguration类获取的俩个系统推荐值而已
         * 完全可以自己设置
         * 效果就是在配合VelocityTracker进行速度计算，实现惯性滑动
         * PS：它在此的作用是做惯性滑动，没有它Scroller一样可以滑动，只是没有惯性而已
         */
        maximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity()+100;
        minimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
    }

    private void initVelocityTracker()
    {
        if (velocityTracker == null)
        {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    private void closeVelocityTracker()
    {
        if (velocityTracker != null)
        {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        initVelocityTracker();
        //给VelocityTracker添加MotionEvent，既然要获取速度，那么势必要先有滑动事件。
        velocityTracker.addMovement(event);
        float y = event.getY();
        Log.d("aaa","y:"+y);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished())
                    scroller.abortAnimation();
                //记录手指按下时的坐标
                lastY = y;
                //消费点击事件
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - lastY;


                scroller.startScroll(0,scroller.getCurrY(),0,(int)-dy,100);
                invalidate();
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                dragging = false;
                closeVelocityTracker();
                if (!scroller.isFinished())
                {
                    scroller.abortAnimation();
                }
                break;
//            //在抬起时进行惯性相关处理
//            case MotionEvent.ACTION_UP:
//                dragging = false;
//                /**
//                 * 此方法的源码注释翻译如下：
//                 * 根据收集到的触摸点计算当前速率。因为此方法相当消耗性能，
//                 * 所以只有当真的想要检测速度的时候才调用这个方法
//                 * 然后可以通过getXVelocity()和getYVelocity()方法获取到追踪的速度
//                 * 第一个参数代表:代表速度的单位，值为1时：代表每毫秒运动x个像素，x px/ms
//                 *                          值为1000时：代表每秒运动x个像素，x px/s
//                 * 第二个参数:代表可以被本方法计算的最大速度
//                 *          这个值必须用同一个作为速度参数的单位声明，而且值必须为正数
//                 * 鸡蛋来说这个方法每次调用时会计算出当前的速度，然后通过get获取
//                 */
//                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
//                int velocityY = (int) velocityTracker.getYVelocity();
//                if (Math.abs(velocityY) > minimumVelocity)
//                {
//                    /**
//                     * 被调用的这个方法源码如下：
//                     * fling(    int startX, int startY,
//                     *           int velocityX, int velocityY,
//                     *           int minX, int maxX,
//                     *           int minY, int maxY     )
//                     * 对应惯性滚动时的起点xy，xy轴上的速度，能滑动的最大最小距离
//                     * 至于实际能滚多少...是源码算法说的算
//                     */
//                    scroller.fling(0, getScrollY(), 0, -velocityY, 0, 0, 0, topViewHeight);
//                    invalidate();
//
//                }
//                closeVelocityTracker();
//                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > topViewHeight)
        {
            y = topViewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll()
    {
        if (scroller.computeScrollOffset())
        {
            scrollBy(0, scroller.getCurrY());
            invalidate();
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        one=getChildAt(0);
        two=getChildAt(1);
        three=getChildAt(2);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        topViewHeight = one.getMeasuredHeight();
    }
}
