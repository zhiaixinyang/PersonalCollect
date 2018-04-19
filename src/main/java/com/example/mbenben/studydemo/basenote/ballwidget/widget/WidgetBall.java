package com.example.mbenben.studydemo.basenote.ballwidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.sticky.util.DensityUtil;

/**
 * @author MDove on 2018/4/19.
 */
public class WidgetBall extends FrameLayout implements IFloatView {
    public static final String TAG = "WeatherBall";
    private final ImageView mWeatherIcon;
    private Configuration mConfig;
    private Paint mShadowPaint;

    public WidgetBall(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_ball_widget, this);
        mWeatherIcon = (ImageView) findViewById(R.id.weather_icon);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.w(TAG, "onDraw ......");
        if (mShadowPaint != null) {
            int cx = DensityUtil.dip2px(getContext(), 24);
            int cy = DensityUtil.dip2px(getContext(), 24);
            float radius = DensityUtil.dip2px(getContext(), 24);
            mShadowPaint.setColor(Color.RED);
            canvas.drawCircle(cx, cy, radius, mShadowPaint);
            Log.w(TAG, "onDraw shadow");
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        mConfig = configuration;
        mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.parseColor("#4d1f1e4a"));
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setShadowLayer(
                configuration.shadowRadius(),
                configuration.shadowDx(),
                configuration.shadowDy(),
                configuration.shadowColor()
        );
        invalidate();
    }

    public void showWeather() {
        mWeatherIcon.setImageResource(R.mipmap.ic_launcher);
    }
}
