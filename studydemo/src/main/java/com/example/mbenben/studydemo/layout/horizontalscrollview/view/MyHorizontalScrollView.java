package com.example.mbenben.studydemo.layout.horizontalscrollview.view;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.mbenben.studydemo.layout.horizontalscrollview.adapter.HorizontalScrollViewAdapter;
import com.example.mbenben.studydemo.utils.ScreenUtils;

/**
 * Created by MBENBEN on 2017/3/3.
 */

public class MyHorizontalScrollView extends HorizontalScrollView implements OnClickListener {

    public interface CurrentImageChangeListener {
        void onCurrentImgChanged(int position, View viewIndicator);
    }

    public interface OnItemClickListener {
        void onClick(View view, int pos);
    }

    private CurrentImageChangeListener listener;

    private OnItemClickListener onItemClickListener;

    private LinearLayout container;

    /**
     * 子元素的宽度
     */
    private int childWidth;
    /**
     * 子元素的高度
     */
    private int childHeight;
    /**
     * 当前最后一张图片的index
     */
    private int currentIndex;
    /**
     * 当前第一张图片的下标
     */
    private int fristIndex;
    /**
     * 数据适配器
     */
    private HorizontalScrollViewAdapter adapter;
    /**
     * 每屏幕最多显示的个数
     */
    private int countOneScreen;
    /**
     * 屏幕的宽度
     */
    private int screenWitdh;


    /**
     * 保存View与位置的键值对
     */
    private Map<View, Integer> viewPos = new HashMap<View, Integer>();

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获得屏幕宽度
        screenWitdh = ScreenUtils.getScreenWidth();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        container = (LinearLayout) getChildAt(0);
    }

    /**
     * 加载下一张图片
     */
    protected void loadNextImg() {
        // 数组边界值计算
        if (currentIndex == adapter.getCount() - 1) {
            return;
        }
        //移除第一张图片，且将水平滚动位置置0
        scrollTo(0, 0);
        viewPos.remove(container.getChildAt(0));
        container.removeViewAt(0);

        //获取下一张图片，并且设置onclick事件，且加入容器中
        View view = adapter.getView(++currentIndex, null, container);
        view.setOnClickListener(this);
        container.addView(view);
        viewPos.put(view, currentIndex);

        //当前第一张图片小标
        fristIndex++;
        //如果设置了滚动监听则触发
        if (listener != null) {
            notifyCurrentImgChanged();
        }
    }
    /**
     * 加载前一张图片
     */
    protected void loadPreImg() {
        //如果当前已经是第一张，则返回
        if (fristIndex == 0) {
            return;
        }
        //获得当前应该显示为第一张图片的下标
        int index = currentIndex - countOneScreen;
        if (index >= 0) {
//			container = (LinearLayout) getChildAt(0);
            //移除最后一张
            int oldViewPos = container.getChildCount() - 1;
            viewPos.remove(container.getChildAt(oldViewPos));
            container.removeViewAt(oldViewPos);

            //将此View放入第一个位置
            View view = adapter.getView(index, null, container);
            viewPos.put(view, index);
            container.addView(view, 0);
            view.setOnClickListener(this);
            //水平滚动位置向左移动view的宽度个像素
            scrollTo(childWidth, 0);
            //当前位置--，当前第一个显示的下标--
            currentIndex--;
            fristIndex--;
            //回调
            if (listener != null) {
                notifyCurrentImgChanged();

            }
        }
    }

    /**
     * 滑动时的回调
     */
    public void notifyCurrentImgChanged() {
        //先清除所有的背景色，点击时会设置为蓝色
        for (int i = 0; i < container.getChildCount(); i++) {
            container.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        listener.onCurrentImgChanged(fristIndex, container.getChildAt(0));
    }

    /**
     * 初始化数据，设置数据适配器
     */
    public void setAdapter(HorizontalScrollViewAdapter adapter) {
        this.adapter = adapter;
        container = (LinearLayout) getChildAt(0);
        // 获得适配器中第一个View
        final View view = adapter.getView(0, null, container);
        container.addView(view);

        // 强制计算当前View的宽和高
        if (childWidth == 0 && childHeight == 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            childHeight = view.getMeasuredHeight();
            childWidth = view.getMeasuredWidth();
            childHeight = view.getMeasuredHeight();
            // 计算每次加载多少个View
            countOneScreen = (screenWitdh / childWidth == 0)?
                    screenWitdh / childWidth +1: screenWitdh / childWidth +2;

        }
        //初始化第一屏幕的元素
        initFirstScreenChildren(countOneScreen);
    }

    /**
     * 加载第一屏的View
     */
    public void initFirstScreenChildren(int countOneScreen) {
        container = (LinearLayout) getChildAt(0);
        container.removeAllViews();
        viewPos.clear();

        for (int i = 0; i < countOneScreen; i++) {
            View view = adapter.getView(i, null, container);
            view.setOnClickListener(this);
            container.addView(view);
            viewPos.put(view, i);
            currentIndex = i;
        }

        if (listener != null) {
            notifyCurrentImgChanged();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:

                int scrollX = getScrollX();
                // 如果当前scrollX为view的宽度，加载下一张，移除第一张
                if (scrollX >= childWidth) {
                    loadNextImg();
                }
                // 如果当前scrollX = 0， 往前设置一张，移除最后一张
                if (scrollX == 0) {
                    loadPreImg();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            for (int i = 0; i < container.getChildCount(); i++) {
                container.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            onItemClickListener.onClick(v, viewPos.get(v));
        }
    }

    public void setOnItemClickListener(OnItemClickListener mOnClickListener) {
        this.onItemClickListener = mOnClickListener;
    }

    public void setCurrentImageChangeListener(CurrentImageChangeListener mListener) {
        this.listener = mListener;
    }

}

