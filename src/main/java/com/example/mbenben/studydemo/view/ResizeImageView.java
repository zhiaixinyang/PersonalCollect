package com.example.mbenben.studydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;

/**
 * Created by magic on 15/8/10.
 */
public class ResizeImageView extends ImageView {

    public enum Type {
        BASE_WIDTH, BASE_HEIGHT;

        public static Type getResizeType(int type) {
            switch (type) {
                case 0:
                    return BASE_WIDTH;
                case 1:
                    return BASE_HEIGHT;
                default:
                    return BASE_WIDTH;
            }
        }
    }

    private boolean ifResize;
    private int widthScale;
    private int heightScale;
    private Type resizeType;

    public ResizeImageView(Context context) {
        this(context, null);
    }

    public ResizeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResizeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ResizeImageView);
        heightScale = styledAttrs.getInt(R.styleable.ResizeImageView_height_scale, 0);
        widthScale = styledAttrs.getInt(R.styleable.ResizeImageView_width_scale, 0);
        ifResize = styledAttrs.getBoolean(R.styleable.ResizeImageView_ifResize, false);
        resizeType = Type.getResizeType(styledAttrs.getInt(R.styleable.ResizeImageView_resizeType, 0));
        styledAttrs.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (ifResize && widthScale > 0 && heightScale > 0) {
            resize();
        }
    }

    private void resize() {
        if (resizeType == Type.BASE_WIDTH && getMeasuredWidth() > 0) {
            int width = getMeasuredWidth();
            int height = width * heightScale / widthScale;
            setMeasuredDimension(width, height);
        } else if (resizeType == Type.BASE_HEIGHT && getMeasuredHeight() > 0) {
            int height = getMeasuredHeight();
            int width = height * widthScale / heightScale;
            setMeasuredDimension(width, height);
        }
    }

    public void setIfResize(boolean ifResize) {
        this.ifResize = ifResize;
    }

    public void setWidthScale(int widthScale) {
        this.widthScale = widthScale;
    }

    public void setHeightScale(int heightScale) {
        this.heightScale = heightScale;
    }
}
