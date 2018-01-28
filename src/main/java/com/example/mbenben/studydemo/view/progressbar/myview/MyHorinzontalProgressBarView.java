package com.example.mbenben.studydemo.view.progressbar.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.utils.DpUtils;

/**
 * Created by MBENBEN on 2017/1/23.
 */

public class MyHorinzontalProgressBarView extends ProgressBar {
    protected int reachColor = ContextCompat.getColor(App.getInstance().getContext(), R.color.blue_dark);
    protected int unReachColor = ContextCompat.getColor(App.getInstance().getContext(), R.color.trans_blue_dark);
    protected int reachHeight = DpUtils.dp2px(4);
    protected int unReachHeight = DpUtils.dp2px(3);

    protected int textSize = DpUtils.sp2px(12);
    protected int textColor = ContextCompat.getColor(App.getInstance().getContext(), R.color.blue_dark);
    protected int textOffSet = DpUtils.dp2px(10);

    protected Paint paint = new Paint();
    protected int realWidth;

    public MyHorinzontalProgressBarView(Context context) {
        this(context, null);
    }

    public MyHorinzontalProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHorinzontalProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyHorinzontalProgressBarView);

        reachColor = typedArray.getColor(
                R.styleable.MyHorinzontalProgressBarView_myprogress_reach_color, reachColor);
        unReachColor = typedArray.getColor(
                R.styleable.MyHorinzontalProgressBarView_myprogress_unreach_color, unReachColor);
        reachHeight = (int) typedArray.getDimension(
                R.styleable.MyHorinzontalProgressBarView_myprogress_reach_height, reachHeight);
        unReachHeight = (int) typedArray.getDimension(
                R.styleable.MyHorinzontalProgressBarView_myprogress_unreach_height, unReachHeight);

        textColor = typedArray.getColor(
                R.styleable.MyHorinzontalProgressBarView_myprogress_text_color, textColor);
        textSize = (int) typedArray.getDimension(
                R.styleable.MyHorinzontalProgressBarView_myprogress_text_size, textSize);
        textOffSet = (int) typedArray.getDimension(
                R.styleable.MyHorinzontalProgressBarView_myprogress_text_offset, textOffSet);

        typedArray.recycle();

        paint.setTextSize(textSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSize = measureHeight(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
        //调用此方法后，系统完成控件的测量。此时可以获取控件属性
        realWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSzie = MeasureSpec.getSize(heightMeasureSpec);
        //分情况返回
        if (heightMode == MeasureSpec.EXACTLY) {
            //对应xml中的match_parent以及精确值
            result = heightSzie;
        } else {
            //对应wrap_content；只有在非EXACTLY模式下才会获得padding的值
            /**
             * paint.descent()：
             *      基线以下的长度，为负
             * paint.ascent()：
             *      基线以上的长度，为正
             * 基线：大体位置就是贴近这一串aaagaaa下部的一条线，以此g实际上是有一部分在基线以下
             */
            int textHeight = (int) (paint.descent() - paint.ascent());
            //因为本自定义View中涵盖三个部分，所以在这里获取三个部分的最大高度
            result = getPaddingTop() + getPaddingBottom() +
                    Math.max(Math.max(reachHeight, unReachHeight), Math.abs(textHeight));
            //在AT_MOST模式之下：将子控件限制在一个默认的最大值之内
            if (heightMode == MeasureSpec.AT_MOST) {
                //result=Math.min(result,heightSzie);
            }
        }
        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        //先移动画布到中间位置
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean isReach = false;
        /**
         * getProgress:当前进度，在0和{@link #getMax（）}之间
         * getMax:返回此进度条范围的上限
         *
         * scale:当前进度的比例
         */
        float scale = getProgress() * 1.0f / getMax();
        float progressWidth = scale * realWidth;
        //设置显示进度的文本
        String text = getProgress() + "%";
        /**
         * measureText(text):获取传入文本宽度
         * getTextBounds(text,0,text.length(),bound=new Rect()):
         *      返回传入文本的各位置坐标
         *      bound.width()也是返回文本宽
         */
        int textWidth = (int) paint.measureText(text);
        if (progressWidth + textWidth > realWidth) {
            progressWidth = realWidth;
            isReach = true;
        }

        float currentReachWidth = scale * realWidth - textOffSet / 2;
        if (currentReachWidth > 0) {
            paint.setColor(reachColor);
            paint.setStrokeWidth(reachHeight);
            //因为画布此时处于正中间位置，所以其实坐标设置为0
            canvas.drawLine(0, 0, currentReachWidth, 0, paint);
        }

        //开始画进度文本
        paint.setColor(textColor);
        //此值计算的是大约为基线以下文本高度大约1/2的位置
        float drawPsiY = -(paint.descent() + paint.ascent()) / 2;
        canvas.drawText(text, progressWidth, drawPsiY, paint);

        //开始画未完成的进度
        if (!isReach) {
            float startX = progressWidth + textWidth + textOffSet / 2;
            paint.setColor(unReachColor);
            paint.setStrokeWidth(unReachHeight);
            canvas.drawLine(startX,0,realWidth,0,paint);
        }

        canvas.restore();
    }
}
