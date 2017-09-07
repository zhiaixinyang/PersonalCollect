package com.example.mbenben.studydemo.layout.viewpager.cardviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2017/8/5.
 *
 * 可调整宽高比的CardView，默认开启阴影效果
 * 原项目GitHub：https://github.com/crazysunj/CardSlideView
 */

public class ElasticCardView extends CardView {

    private final float RATIO;

    public ElasticCardView(Context context) {
        this(context, null);
    }

    public ElasticCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ElasticCardView);
        RATIO = array.getFloat(R.styleable.ElasticCardView_ratio, 1.0f);
        array.recycle();

        setPreventCornerOverlap(true);
        setUseCompatPadding(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (RATIO > 0) {
            int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (MeasureSpec.getSize(widthMeasureSpec) * RATIO), MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
