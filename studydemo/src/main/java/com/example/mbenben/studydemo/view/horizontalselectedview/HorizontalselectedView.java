package com.example.mbenben.studydemo.view.horizontalselectedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;
import com.example.mbenben.studydemo.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2017/6/21.
 */

public class HorizontalselectedView extends View {

    private Context context;
    private List<String> strings = new ArrayList<String>();//数据源字符串数组

    private int seeSize = 5;//可见个数

    private int averageWidth;//每个字母所占的大小；
    private TextPaint textPaint;
    private int width;//控件宽度
    private int height;//控件高度
    private Paint selectedPaint;//被选中文字的画笔
    private int n;
    private float downX;
    private float offset;
    private float selectedTextSize;
    private int selectedColor;
    private float textSize;
    private int textColor;
    private Rect rect = new Rect();

    private int textWidth = 0;
    private int textHeight = 0;
    private int centerTextHeight = 0;


    public HorizontalselectedView(Context context) {
        this(context, null);
    }

    public HorizontalselectedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalselectedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setWillNotDraw(false);
        setClickable(true);
        initAttrs(attrs);//初始化属性
        initPaint();//初始化画笔
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        selectedPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(selectedColor);
        selectedPaint.setTextSize(selectedTextSize);
        selectedPaint.setTextSize(textSize);
    }


    /**
     * 初始化属性
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.HorizontalselectedView);
        //两种字体颜色和字体大小
        seeSize = tta.getInteger(R.styleable.HorizontalselectedView_horizontalselectedview_see_size, 5);
        selectedTextSize = tta.getFloat(R.styleable.HorizontalselectedView_horizontalselectedview_selected_textsize, 50);
        selectedColor = tta.getColor(R.styleable.HorizontalselectedView_horizontalselectedview_selected_textcolor, context.getResources().getColor(android.R.color.black));
        textSize = tta.getFloat(R.styleable.HorizontalselectedView_horizontalselectedview_textsize, 40);
        textColor = tta.getColor(R.styleable.HorizontalselectedView_horizontalselectedview_textcolor, context.getResources().getColor(android.R.color.darker_gray));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();//获得点下去的x坐标
                break;
            case MotionEvent.ACTION_MOVE://复杂的是移动时的判断
                float scrollX = event.getX();

                if (n != 0 && n != strings.size() - 1)
                    offset = scrollX - downX;//滑动时的偏移量，用于计算每个是数据源文字的坐标值
                else {
                    offset = (float) ((scrollX - downX) / 1.5);//当滑到两端的时候添加一点阻力
                }

                if (scrollX > downX) {
                    //向右滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (scrollX - downX >= averageWidth) {
                        if (n > 0) {
                            offset = 0;
                            n = n - 1;
                            downX = scrollX;
                        }
                    }
                } else {

                    //向左滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (downX - scrollX >= averageWidth) {

                        if (n < strings.size() - 1) {
                            offset = 0;
                            n = n + 1;
                            downX = scrollX;
                        }
                    }
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                //抬起手指时，偏移量归零，相当于回弹。
                offset = 0;
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
        //总空间宽度/可见数目
        averageWidth = width / seeSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDrawLine(canvas);

        if (n >= 0 && n <= strings.size() - 1) {
            String selectText = strings.get(n);
            /**
             * 得到被选中文字 绘制时所需要的宽高
             */
            selectedPaint.getTextBounds(selectText,0,selectText.length(),rect);
            int centerTextWidth = rect.width();
            centerTextHeight = rect.height();
            int selectTextHeight=getHeight() / 2 + centerTextHeight / 2;
            //绘制选中的文字
            canvas.drawText(selectText,
                    getWidth() / 2 - centerTextWidth / 2 + offset,
                    selectTextHeight, selectedPaint);

            for (int i = 0; i < strings.size(); i++) {
                //遍历strings，把每个地方都绘制出来
                if (n > 0 && n < strings.size() - 1) {
                    //这里主要是因为strings数据源的文字长度不一样，为了让被选中两边文字距离中心宽度一样，我们取得左右两个文字长度的平均值
                    textPaint.getTextBounds(strings.get(n - 1), 0, strings.get(n - 1).length(), rect);
                    int width1 = rect.width();
                    textPaint.getTextBounds(strings.get(n + 1), 0, strings.get(n + 1).length(), rect);
                    int width2 = rect.width();
                    textWidth = (width1 + width2) / 2;
                }
                if (i == 0) {
                    //得到高，高度是一样的
                    textPaint.getTextBounds(strings.get(0), 0, strings.get(0).length(), rect);
                    textHeight = rect.height();
                }
                //通过上述得到的长宽，绘制文字（除了被选中的，上边已经绘制完毕）
                if (n != i) {
                    canvas.drawText(strings.get(i), (i - n) * averageWidth + width / 2 - textWidth / 2 + offset, (height + textHeight) / 2, textPaint);
                }
            }
        }
    }

    private void initDrawLine(Canvas canvas) {
        Path guideTriangle=new Path();
        guideTriangle.moveTo(width/2- DpUtils.dp2px(10),height/2-DpUtils.dp2px(30));
        guideTriangle.lineTo(width/2,height/2-DpUtils.dp2px(15));
        guideTriangle.lineTo(width/2+DpUtils.dp2px(10),height/2-DpUtils.dp2px(30));
        guideTriangle.close();
        canvas.drawPath(guideTriangle,textPaint);
        canvas.drawLine(0,
                height/2-DpUtils.dp2px(30),
                ScreenUtils.getScreenWidth(),
                height/2-DpUtils.dp2px(30),textPaint);
        canvas.drawLine(0,
                height/2+DpUtils.dp2px(30),
                ScreenUtils.getScreenWidth(),
                height/2+DpUtils.dp2px(30),textPaint);
        for(int i=1;i<seeSize+1;i++){
            canvas.drawLine(averageWidth*i/2+averageWidth*(i-1)/2,
                    height/2+DpUtils.dp2px(20),
                    averageWidth*i/2+averageWidth*(i-1)/2,
                    height/2+DpUtils.dp2px(30),textPaint);
        }
    }

    /**
     * 改变中间可见文字的数目
     *
     * @param seeSizes 可见数
     */
    public void setSeeSize(int seeSizes) {
        if (seeSize > 0) {
            seeSize = seeSizes;
            invalidate();
        }

    }


    /**
     * 向左移动一个单元
     */
    public void setAnLeftOffset() {
        if (n < strings.size() - 1) {
            n = n + 1;
            invalidate();
        }

    }

    /**
     * 向右移动一个单元
     */
    public void setAnRightOffset() {
        if (n > 0) {
            n = n - 1;
            invalidate();
        }
    }

    /**
     * 设置个数据源
     *
     * @param strings 数据源String集合
     */
    public void setData(List<String> strings) {
        this.strings = strings;
        n = strings.size() / 2;
        invalidate();
    }

    /**
     * 获得被选中的文本
     *
     * @return 被选中的文本
     */
    public String getSelectedString() {
        if (strings.size() != 0) {
            return strings.get(n);
        }
        return null;
    }
}
