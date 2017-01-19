package com.example.mbenben.studydemo.view.tempview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.mbenben.studydemo.R;

/**
 * Created by MBENBEN on 2016/12/12.
 */

public class TempControlView extends View {

    // 控件宽
    private int width;
    // 控件高
    private int height;
    // 刻度盘半径
    private int dialRadius;
    // 圆弧半径
    private int arcRadius;
    // 刻度高
    private int scaleHeight = dp2px(10);
    // 刻度盘画笔
    private Paint dialPaint;
    // 圆弧画笔
    private Paint arcPaint;
    // 标题画笔
    private Paint titlePaint;
    // 温度标识画笔
    private Paint tempFlagPaint;
    // 旋转按钮画笔
    private Paint buttonPaint;
    // 温度显示画笔
    private Paint tempPaint;
    // 文本提示
    private String title = "最高温度设置";
    // 温度
    private int temperature;
    // 最低温度
    private int minTemp = 15;
    // 最高温度
    private int maxTemp = 30;
    // 四格（每格4.5度，共18度）代表温度1度
    private int angleRate = 4;
    // 按钮图片
    private Bitmap buttonImage = BitmapFactory.decodeResource(getResources(),
            R.mipmap.btn_rotate);
    // 按钮图片阴影
    private Bitmap buttonImageShadow = BitmapFactory.decodeResource(getResources(),
            R.mipmap.btn_rotate_shadow);
    // 抗锯齿
    private PaintFlagsDrawFilter paintFlagsDrawFilter;
    // 温度改变监听
    private OnTempChangeListener onTempChangeListener;

    // 以下为旋转按钮相关

    // 当前按钮旋转的角度
    private float rotateAngle;
    // 当前的角度
    private float currentAngle;

    public TempControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TempControlView(Context context) {
        super(context);
        init();
    }

    private void init() {
        dialPaint = new Paint();
        dialPaint.setAntiAlias(true);
        dialPaint.setStrokeWidth(dp2px(2));
        dialPaint.setStyle(Paint.Style.STROKE);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setColor(Color.parseColor("#3CB7EA"));
        arcPaint.setStrokeWidth(dp2px(2));
        arcPaint.setStyle(Paint.Style.STROKE);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextSize(sp2px(15));
        titlePaint.setColor(Color.parseColor("#3B434E"));
        titlePaint.setStyle(Paint.Style.STROKE);

        tempFlagPaint = new Paint();
        tempFlagPaint.setAntiAlias(true);
        tempFlagPaint.setTextSize(sp2px(25));
        tempFlagPaint.setColor(Color.parseColor("#E4A07E"));
        tempFlagPaint.setStyle(Paint.Style.STROKE);

        buttonPaint = new Paint();
        tempFlagPaint.setAntiAlias(true);
        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        tempPaint = new Paint();
        tempPaint.setAntiAlias(true);
        tempPaint.setTextSize(sp2px(60));
        tempPaint.setColor(Color.parseColor("#E27A3F"));
        tempPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScale(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawButton(canvas);
        drawTemp(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 控件宽、高
        width = height = Math.min(h, w);
        // 刻度盘半径
        dialRadius = width / 2 - dp2px(20);
        // 圆弧半径
        arcRadius = dialRadius - dp2px(20);
    }

    /**
     * 绘制刻度盘下的圆弧
     *
     * @param canvas 画布
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(135 + 2);
        RectF rectF = new RectF(-arcRadius, -arcRadius, arcRadius, arcRadius);
        //这个方法就是画弧线的方法，在rectF这个矩形内，画一个圆心角265度的弧（应该是这么描述吧--！）
        canvas.drawArc(rectF, 0, 265, false, arcPaint);
        canvas.restore();
    }

    /**
     * 绘制刻度盘
     *
     * @param canvas 画布
     */
    private void drawScale(Canvas canvas) {
        //先保存画布状态，因为我们最终的效果有很多层面，而且每一层的画布都相对是独立的
        canvas.save();
        //移动画布到屏幕的中心位置
        canvas.translate(getWidth() / 2, getHeight() / 2);
        /**
         * 逆时针旋转135-2度
         * 此处为什么要旋转？
         * 因为我们正常画的时候圆的时候它从x为0，y为半径处起笔，然后
         * 顺时针画至闭合。而我们想要的最终效果，就需要正常效果逆时针
         * 旋转即可。
         */
        canvas.rotate(-133);
        dialPaint.setColor(Color.parseColor("#3CB7EA"));
        /**
         * 这里就是画刻度的循环，这里有点绕，不过静下心也比较简单。
         * 我们要有一个概念。画布的绘图的原理和我们画图是一个样。
         * 大家可以这样想象，左右操控，画布，右手拿着笔。
         * 最开始时，我们通过左右逆时针旋转了我们的画布133度；
         * 但是我们的右手，不受画布的影响，该什么画还怎么画。
         * 在这个画刻度的过程。我们的右手画笔只做一个动作：画线
         * canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint)
         * 注意我们canvas.translate()方法，已经将画布移至屏幕中间;
         * 所以此时的（0,0）就是屏幕中心点。
         * 咱们再回到右手上来，右手一直都是在重复的画直线。为什么有弧线的效果？
         * 因为左手控制画布在旋转！！！
         */
        for (int i = 0; i < 60; i++) {
            //这里y为什么是负的？因为在安卓的坐标系里，y轴原点往下是正；
            //x轴往左是负
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(4.5f);
        }
        /**
         * 如果这里大家不理解为什么又转了90，那这就尴尬了。
         * 咱们为了这个效果，最初，逆时针转了130+度;
         * 然后又顺时针转了60乘4.5度，也就是说，画布回正了140度左右（就是数学计算题）
         * 而我们想让画布重回-130+度，那么再顺势针转90即可达到效果。
         * 当然我们也可以再逆时针回270度：效果是一样的
         * 也就是canvas.rotate(-270);
         * 当然还有一个方法。那就是我们重新恢复画布，然后在逆时针转133度：
         * canvas.restore();
         * canvas.rotate(-133);
         * 当然我们还要先把画布移到中心，不然它会画到左上角去。
         */
        canvas.rotate(90);
        dialPaint.setColor(Color.parseColor("#E37364"));
        for (int i = 0; i < (temperature - minTemp) * angleRate; i++) {
            canvas.drawLine(0, -dialRadius, 0, -dialRadius + scaleHeight, dialPaint);
            canvas.rotate(4.5f);
        }
        //此效果完成，恢复画布状态。
        canvas.restore();
    }

    /**
     * 绘制标题与温度标识
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        canvas.save();
        //此方法是Paint的方法，传入一个字符串获取字符串宽度。
        float titleWidth = titlePaint.measureText(title);
        /**
         * 绘制标题"最高温度设置"
         * 说实话看到这我有点方...因为作者前两部操作都是先移动画布到中心，
         * 然后再搞事情。这次不移的话就是直接以左上角为（0,0）坐标。
         * 咱们也可以用移画布的方式做，效果是一样的如下：
         * canvas.restore();
         * float titleWidth = titlePaint.measureText(title);
         * canvas.drawText(title, - titleWidth / 2, dialRadius * 2 + dp2px(15), titlePaint);
         */
        //不同的是以不同的（0,0）坐标点所以Text的起点坐标也发生了变化。仅此而已
        //width:控件的宽度。
        canvas.drawText(title, (width-titleWidth)/2, dialRadius*2+dp2px(5), titlePaint);
        // 绘制最小温度标识
        // 最小温度如果小于10，显示为0x
        String minTempFlag = minTemp < 10 ? "0" + minTemp : minTemp + "";
        float tempFlagWidth = titlePaint.measureText(maxTemp + "");
        //这个旋转画布的重载方法是指，以（width/2,height/2）这个坐标为原点旋转
        canvas.rotate(55, width/2, height/2 );
        canvas.drawText(minTempFlag, (width - tempFlagWidth) / 2, height + dp2px(5), tempFlagPaint);
        // 绘制最大温度标识
        canvas.rotate(-105, width / 2, height / 2);
        canvas.drawText(maxTemp + "", (width - tempFlagWidth) / 2, height + dp2px(5), tempFlagPaint);
        canvas.restore();
    }

    /**
     * 绘制旋转按钮
     *
     * @param canvas 画布
     */
    private void drawButton(Canvas canvas) {
        // 按钮宽高
        int buttonWidth = buttonImage.getWidth();
        int buttonHeight = buttonImage.getHeight();
        // 按钮阴影宽高
        int buttonShadowWidth = buttonImageShadow.getWidth();
        int buttonShadowHeight = buttonImageShadow.getHeight();

        // 绘制按钮阴影
        canvas.drawBitmap(buttonImageShadow, (width - buttonShadowWidth) / 2,
                (height - buttonShadowHeight) / 2, buttonPaint);
        //Matrix（矩阵），用于操作Bitmap对象，比如移动旋转等
        Matrix matrix = new Matrix();
        // 设置按钮位置
        matrix.setTranslate(buttonWidth / 2, buttonHeight / 2);
        // 设置旋转角度
        matrix.preRotate(45 + rotateAngle);
        // 按钮位置还原，此时按钮位置在左上角
        matrix.preTranslate(-buttonWidth / 2, -buttonHeight / 2);
        // 将按钮移到中心位置
        matrix.postTranslate((width - buttonWidth) / 2, (height - buttonHeight) / 2);

        //设置抗锯齿
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.drawBitmap(buttonImage, matrix, buttonPaint);
    }

    /**
     * 设置温度
     *
     * @param minTemp 最小温度
     * @param maxTemp 最大温度
     * @param temp    设置的温度
     */
    public void setTemp(int minTemp, int maxTemp, int temp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temperature = temp;
        this.angleRate = 60 / (maxTemp - minTemp);
        rotateAngle = (float) ((temp - minTemp) * angleRate * 4.5);
        invalidate();
    }

    /**
     * 设置温度改变监听
     *
     * @param onTempChangeListener 监听接口
     */
    public void setOnTempChangeListener(OnTempChangeListener onTempChangeListener) {
        this.onTempChangeListener = onTempChangeListener;
    }

    /**
     * 温度改变监听接口
     */
    public interface OnTempChangeListener {
        /**
         * 回调方法
         *
         * @param temp 温度
         */
        void change(int temp);
    }

    /**
     * 绘制温度
     *
     * @param canvas 画布
     */
    private void drawTemp(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        float tempWidth = tempPaint.measureText(temperature + "");
        float tempHeight = (tempPaint.ascent() + tempPaint.descent()) / 2;
        canvas.drawText(temperature + "°", -tempWidth / 2 - dp2px(5), -tempHeight, tempPaint);
        canvas.restore();
    }

    private boolean isDown;
    private boolean isMove;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                float downX = event.getX();
                float downY = event.getY();
                currentAngle = test(downX, downY);
                break;

            case MotionEvent.ACTION_MOVE:
                isMove = true;
                float targetX;
                float targetY;
                downX = targetX = event.getX();
                downY = targetY = event.getY();
                float angle = test(targetX, targetY);

                // 滑过的角度增量
                float angleIncreased = angle - currentAngle;

                // 防止越界
                if (angleIncreased < -270) {
                    angleIncreased = angleIncreased + 360;
                } else if (angleIncreased > 270) {
                    angleIncreased = angleIncreased - 360;
                }

                IncreaseAngle(angleIncreased);
                currentAngle = angle;
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if (isDown && isMove) {
                    // 纠正指针位置
                    rotateAngle = (float) ((temperature - minTemp) * angleRate * 4.5);
                    invalidate();
                    // 回调温度改变监听
                    onTempChangeListener.change(temperature);
                    isDown = false;
                    isMove = false;
                }
                break;
            }
        }
        return true;
    }


    private float test(float targetX, float targetY){
        float x = targetX - width / 2;
        float y = targetY - height / 2;
        double radian;
        float angle;
        radian=Math.atan2(y,x);
        angle= (float) ((180*radian)/Math.PI);
        if (angle<0){
            angle=360+angle;
        }
        //angle即为0-360
        return angle;
    }

    /**
     * 以按钮圆心为坐标圆点，建立坐标系，求出(targetX, targetY)坐标与x轴的夹角
     *
     * @param targetX x坐标
     * @param targetY y坐标
     * @return (targetX, targetY)坐标与x轴的夹角
     */
    private float calcAngle(float targetX, float targetY) {
        float x = targetX - width / 2;
        float y = targetY - height / 2;
        double radian;
        double xx=0;

        if (x != 0) {
            //正切
            float tan = Math.abs(y / x);
            if (x > 0) {
                if (y >= 0) {
                    //求弧度
                    radian = Math.atan(tan);
                } else {
                    radian = 2 * Math.PI - Math.atan(tan);
                }
            } else {
                if (y >= 0) {
                    radian = Math.PI - Math.atan(tan);

                } else {
                    radian = Math.PI + Math.atan(tan);

                }
            }
        } else {
            if (y > 0) {
                radian = Math.PI / 2;
            } else {
                radian = -Math.PI / 2;
            }
        }
        return (float) ((radian * 180) / Math.PI);
    }

    /**
     * 增加旋转角度
     *
     * @param angle 增加的角度
     */
    private void IncreaseAngle(float angle) {
        rotateAngle += angle;
        if (rotateAngle < 0) {
            rotateAngle = 0;
        } else if (rotateAngle > 270) {
            rotateAngle = 270;
        }
        temperature = (int) (rotateAngle / 4.5) / angleRate + minTemp;
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}