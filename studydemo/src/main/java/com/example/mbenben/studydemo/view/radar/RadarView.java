package com.example.mbenben.studydemo.view.radar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2017/4/9.
 */

public class RadarView extends View {

    private static final int MSG_WHAT = 10086;
    private static final int DELAY_TIME = 20;
    //设置默认宽高，雷达一般都是圆形，所以我们下面取宽高会去Math.min(宽,高)
    private final int DEFAULT_WIDTH = 200;
    private final int DEFAULT_HEIGHT = 200;

    private int mRadarRadius;  //雷达的半径

    private Paint mRadarPaint;//雷达画笔

    private Paint mRadarBg;//雷达底色画笔

    private int radarCircleCount = 4;//雷达圆圈的个数，默认4个

    private int mRadarLineColor = Color.WHITE; //雷达线条的颜色，默认为白色

    private int mRadarBgColor = Color.BLACK; //雷达圆圈背景色
    private Shader radarShader;  //paintShader

    //雷达扫描时候的起始和终止颜色
    private int startColor = ContextCompat.getColor(App.getInstance().getContext(),R.color.trans_gray);
    private int endColor = ContextCompat.getColor(App.getInstance().getContext(),R.color.trans_blue_dark);

    private Matrix matrix;
    //旋转的角度
    private int rotateAngel = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            rotateAngel += 3;
            postInvalidate();

            matrix.reset();
            matrix.preRotate(rotateAngel, 0, 0);
            mHandler.sendEmptyMessageDelayed(MSG_WHAT, DELAY_TIME);
        }
    };

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

        mRadarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);     //设置抗锯齿
        mRadarPaint.setColor(mRadarLineColor);                  //画笔颜色
        mRadarPaint.setStyle(Paint.Style.STROKE);           //设置空心的画笔，只画圆边
        mRadarPaint.setStrokeWidth(2);                      //画笔宽度


        mRadarBg = new Paint(Paint.ANTI_ALIAS_FLAG);     //设置抗锯齿
        mRadarBg.setColor(mRadarBgColor);                  //画笔颜色
        mRadarBg.setStyle(Paint.Style.FILL);           //设置空心的画笔，只画圆边


        radarShader = new SweepGradient(0, 0, startColor, endColor);

        matrix = new Matrix();
    }

    private void init(Context context,AttributeSet attrs) {
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.radarview);
        startColor = typedArray.getColor(R.styleable.radarview_startColor, startColor);
        endColor = typedArray.getColor(R.styleable.radarview_endColor, endColor);
        mRadarBgColor = typedArray.getColor(R.styleable.radarview_radarbgColor, mRadarBgColor);
        mRadarLineColor = typedArray.getColor(R.styleable.radarview_radarlineColor, mRadarLineColor);
        radarCircleCount = typedArray.getInteger(R.styleable.radarview_circleCount, radarCircleCount);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mRadarRadius, mRadarRadius);   //将画板移动到屏幕的中心点

        mRadarBg.setShader(null);
        canvas.drawCircle(0, 0, mRadarRadius, mRadarBg);  //绘制底色(默认为黑色)，可以使雷达的线看起来更清晰

        for (int i = 1; i <= radarCircleCount; i++) {     //根据用户设定的圆个数进行绘制
            canvas.drawCircle(0, 0, (float) (i * 1.0 / radarCircleCount * mRadarRadius), mRadarPaint);  //画圆圈
        }

        canvas.drawLine(-mRadarRadius, 0, mRadarRadius, 0, mRadarPaint);  //绘制雷达基线 x轴
        canvas.drawLine(0, mRadarRadius, 0, -mRadarRadius, mRadarPaint);  //绘制雷达基线 y轴

        //设置颜色渐变从透明到不透明
        mRadarBg.setShader(radarShader);
        canvas.concat(matrix);
        canvas.drawCircle(0, 0, mRadarRadius, mRadarBg);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadarRadius = Math.min(w / 4, h / 4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(1, DEFAULT_WIDTH, widthMeasureSpec);
        int height = measureSize(0, DEFAULT_HEIGHT, heightMeasureSpec);
        int measureSize = Math.max(width, height);   //取最大的 宽|高
        setMeasuredDimension(measureSize, measureSize);
    }

    /**
     * 测绘measure
     *
     * @param specType    1为宽， 其他为高
     * @param contentSize 默认值
     */
    private int measureSize(int specType, int contentSize, int measureSpec) {
        int result;
        //获取测量的模式和Size
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(contentSize, specSize);
        } else {
            result = contentSize;

            if (specType == 1) {
                // 根据传人方式计算宽
                result += (getPaddingLeft() + getPaddingRight());
            } else {
                // 根据传人方式计算高
                result += (getPaddingTop() + getPaddingBottom());
            }
        }
        return result;
    }

    public void startScan() {
        mHandler.removeMessages(MSG_WHAT);
        mHandler.sendEmptyMessage(MSG_WHAT);
    }

    public void stopScan() {
        mHandler.removeMessages(MSG_WHAT);
    }
}
