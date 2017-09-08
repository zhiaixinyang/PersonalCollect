package com.example.mbenben.studydemo.view.fanmenu.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.mbenben.studydemo.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FanMenuChart extends View {
    public static final String TAG = "QDX";
    //默认的宽高都为屏幕宽度/高度
    private int DEFAULT_HEIGHT;
    private int DEFAULT_WIDTH;
    //宽高
    private int mWidth;
    private int mHeight;
    //数据
    private ArrayList<PieData> mPieData = new ArrayList<>();
    // 每块模块的 间隔  以下简称饼状图像为模块
    private float PIE_SPACING = 2f;
    //整个view展示的角度, 360为圆
    private int PIE_VIEW_ANGLE = 360;
    /**
     * animator 动画时候绘制的角度，最大为 PIE_VIEW_ANGLE
     */
    private float animatedValue;
    //绘制模块  以及 弹出模块上的中心文字
    private Paint mPaint = new Paint();
    //画在边缘上的 缩写
    private Paint mLabelPaint = new Paint();
    //XferMode御用的笔
    private Paint mXPaint = new Paint();
    //饼状图初始绘制角度
    private float startAngle = 0;
    /**
     * rectF            显示bitMap模块
     * rectFGold        白金色圈
     * rectFIn          白色内圈
     * rectFLabel        标签外圈
     */
    private RectF rectF, rectFGold, rectFIn, rectFLabel;
    /**
     * rectFF       弹出的最外圈，
     * rectFGlodF    弹出的白金层
     * reatFInF     弹出的透明层
     * rShadowFF    弹出的最外圈的阴影
     */
    private RectF rectFF, rectFGlodF, rectFInF, rShadowFF;
    /**
     * rLabel rectFLabel     未弹出时，最外层标签
     * r      rectF         最外圈的半径
     * rGold  rectFGold     白金的半径
     * rIn    reatFInF      透明内圈
     * rF     rectF         弹出的最外圈的半径
     * rGlodF  rectFGold     弹出白金的半径
     */
    private float rLabel, r, rGold, rIn, rF, rGlodF, rInF;

    //展示动画
    private ValueAnimator animatorStart;
    //end动画
    private ValueAnimator animatorEnd;
    //动画时间
    private long animatorDuration = 500;
    private TimeInterpolator timeInterpolator = new AccelerateDecelerateInterpolator();
    /**
     * 每一个模块占有的总角度 （前几模块角度之和）
     */
    private float[] pieAngles;
    /**
     * 选中位置，对应触摸的模块位置
     */
    private int touchSeleteId;
    //点触扇形 偏移比例
    private double touchRatioRectFF = 1.4;
    //最外层标签
    private double labelRadioRectF = 1.2;
    //绘制bitmap圆环半径比例
    private double widthBmpRadioRectF = 0.8;
    //白金层
    private double goldRadioRectF = 0.4;
    //最里面透明层
    private double insideRadiusScale = 0.3;
    /**
     * 中间bitmap收缩倍数
     */
    private float bmpScale = 1f;
    //文字颜色
    private int touchTextColor = Color.BLACK;
    //标签文字颜色
    private int labelTextColor = Color.WHITE;
    //阴影颜色
    private int shadowColor = 0x22000000;
    //白金区域颜色
    private int goldColor = 0x66b8e0e0;

    /**
     * 弹出的 模块 中心文字
     */
    private int touchCenterTextSize = 50;
    /**
     * 写在边缘的标签文字大小
     */
    private int labelTextSize = 40;
    /**
     * 绘制每个模块里的图片
     */
    private List<Bitmap> bmpList = new ArrayList<>();


    public FanMenuChart(Context context) {
        this(context, null);
    }

    public FanMenuChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanMenuChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (DEFAULT_WIDTH == 0 || DEFAULT_HEIGHT == 0) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int displayWidth = wm.getDefaultDisplay().getWidth();
            int displayHeight = wm.getDefaultDisplay().getHeight();
            DEFAULT_WIDTH = displayWidth;
            DEFAULT_HEIGHT = displayHeight;
        }


        int width = measureSize(1, DEFAULT_WIDTH, widthMeasureSpec);
        int height = measureSize(1, DEFAULT_HEIGHT, heightMeasureSpec);
        int measureSize = Math.min(width, height);   //取最小的 宽|高
        setMeasuredDimension(measureSize, measureSize);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        rLabel = (float) (Math.max(mWidth, mHeight) / 2 * widthBmpRadioRectF * labelRadioRectF);
        rectFLabel = new RectF(-rLabel, -rLabel, rLabel, rLabel);

        //标签层内绘制bmp
        r = (float) (Math.max(mWidth, mHeight) / 2 * widthBmpRadioRectF);// 饼状图半径
        rectF = new RectF(-r, -r, r, r);

        //白金圆弧
        rGold = (float) (r * goldRadioRectF);
        rectFGold = new RectF(-rGold, -rGold, rGold, rGold);

        //透明层圆
        rIn = (float) (r * insideRadiusScale);
        rectFIn = new RectF(-rIn, -rIn, rIn, rIn);


        /************************以下为弹出模块的RECTF************************************/
        //弹出的圆弧
        rF = (float) (Math.max(mWidth, mHeight) / 2 * widthBmpRadioRectF * touchRatioRectFF);// 饼状图半径
        // 饼状图绘制区域
        rectFF = new RectF(-rF, -rF, rF, rF);

        rShadowFF = new RectF(-rF - PIE_SPACING * 10, -rF - PIE_SPACING * 10, rF + PIE_SPACING * 10, rF + PIE_SPACING * 10);

        //弹出的白金层圆弧
        rGlodF = (float) (rF * goldRadioRectF);
        rectFGlodF = new RectF(-rGlodF, -rGlodF, rGlodF, rGlodF);

        //弹出的透明圆
        rInF = (float) (rF * insideRadiusScale);
        rectFInF = new RectF(-rInF, -rInF, rInF, rInF);


        initDate(mPieData);
//        showStartAnim();
    }


    private Bitmap bmpCenter;
    private Matrix bmpMatrix;

    /**
     * 设置居中旋转的bitmap
     */
    public void setCenterBitmap(int resourceId, int width, int heigth) {
        bmpCenter = BitmapFactory.decodeResource(getResources(), resourceId);
        bmpCenter = BitmapUtils.zoomImage(bmpCenter, width, heigth);
        bmpMatrix = new Matrix();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPieData == null)
            return;

        canvas.translate(mWidth / 2, mHeight / 2);// 将画板坐标原点移动到中心位置

        if (bmpCenter != null) {   //展示的时候旋转中心图片
            bmpMatrix.reset();
            bmpMatrix.preTranslate(-bmpCenter.getWidth() / 2, -bmpCenter.getHeight() / 2);  //将bitmap移动中心点(原本中心点位于bitmap坐上角(0,0))
            bmpMatrix.preRotate(360 / PIE_VIEW_ANGLE * animatedValue * 2, bmpCenter.getWidth() / 2, bmpCenter.getHeight() / 2);//以bitmap中心点为中心 闭着眼 旋转 跳跃
            canvas.drawBitmap(bmpCenter, bmpMatrix, mPaint);
        }

//        canvas.drawLine(-mWidth / 2, 0, mWidth / 2, 0, mPaint);      //绘制一下中心坐标 x,y
//        canvas.drawLine(0, mHeight / 2, 0, -mHeight / 2, mPaint);

        /**
         * 先画扇形的每一模块
         */
        drawPieRectF(canvas);

        /**
         * 再绘制扇形上的文字/图片
         */
        drawTextAndBmp(canvas);
    }

    /**
     * 负责绘制扇形的所有 Rect , Arc
     *
     * @param canvas 画板
     */
    private void drawPieRectF(Canvas canvas) {
        float currentStartAngle = 0;// 当前已经绘制的角度,我们从0开始，直到animatedValue
        canvas.save();
        canvas.rotate(startAngle);
        float sweepAngle;   //当前要绘制的角度

        for (int i = 0; i < mPieData.size(); i++) {
            PieData pie = mPieData.get(i);

            sweepAngle = Math.min(pie.getAngle() - PIE_SPACING, animatedValue - currentStartAngle);   //-1 是为了显示每个模块之间的空隙

            if (currentStartAngle + sweepAngle == PIE_VIEW_ANGLE - PIE_SPACING) {   //防止最后一个模块缺角 ，如果是 360° 且最后一块希望是缺觉，可以注释这段
                sweepAngle = pie.getAngle();
            }

            if (sweepAngle > 0) {
                Log.i(TAG, "绘制角度" + (sweepAngle + currentStartAngle));
                Log.i(TAG, "绘制角度 animatedValue" + animatedValue);
                if (i == touchSeleteId) {    //弹出触摸的板块
                    drawArc(canvas, currentStartAngle, sweepAngle, pie, rectFF, rectFGlodF, rectFInF, true);
                } else {
                    drawArc(canvas, currentStartAngle, sweepAngle, pie, rectF, rectFGold, rectFIn, false);
                }

            }
            currentStartAngle += pie.getAngle();
        }

        canvas.restore();
    }

    /**
     * 绘制所有的文字 | bitmap
     * 绘制文字和绘制扇形的RectF/Arc 有所不同，绘制文字是当animatedValue达到某一值的时候讲文字绘制
     * 而绘制扇形的时候是根据animatedValue一个角度一个角度绘制的
     *
     * @param canvas 画板
     */
    private void drawTextAndBmp(Canvas canvas) {
        float currentStartAngle = startAngle;  //当前绘制的起始角度，用户设定 。绘制文字我们直接加上其实角度的值即可(更便捷计算)

        for (int i = 0; i < mPieData.size(); i++) {
            PieData pie = mPieData.get(i);

            int pivotX;   //中心点X
            int pivotY;   //中心点Y

            if (animatedValue > pieAngles[i] - pie.getAngle() / 3) {  //当要绘制的角度 >当前模块的 2/3 的时候才绘制。 增强界面动画绘制效果

                if (i == touchSeleteId) {  //如果当前模块是触摸的模块,绘制文字
                    pivotX = (int) (Math.cos(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) * (rF + rGlodF) / 2); //获取当前模块中心点x,y轴坐标
                    pivotY = (int) (Math.sin(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) * (rF + rGlodF) / 2);
                    textCenter(pie.getName(), mPaint, canvas, pivotX, pivotY);
                } else {
                    pivotX = (int) (Math.cos(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) * (r + rLabel) / 2);
                    pivotY = (int) (Math.sin(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) * (r + rLabel) / 2);

                    canvas.save();
                    canvas.rotate((currentStartAngle + pie.getAngle() / 2) + 90, pivotX, pivotY);  //+90° 原因是 0°的时候文字竖直绘制
                    textCenter(pie.getName_label(), mLabelPaint, canvas, pivotX, pivotY);   //画边缘标签的文字
                    canvas.restore();

                    pivotX = (int) (Math.cos(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) * (r + rGold) / 2);
                    pivotY = (int) (Math.sin(Math.toRadians(currentStartAngle + (pie.getAngle() / 2))) * (r + rGold) / 2);
                    if (bmpList != null && bmpList.size() > 0 && bmpList.get(i) != null) { //每次invalidate() 都要重新绘制一遍图片在画板
                        Log.d(TAG, "绘制图片" + i);
                        canvas.drawBitmap(bmpList.get(i), (pivotX - (int) (pie.getMax_drawable_size() / 2)), (pivotY - (int) (pie.getMax_drawable_size() / 2)), mPaint);
                    }

                }
                currentStartAngle += pie.getAngle();
            }
        }
    }

    /**
     * 画出扇形   ,两种不同情况 展示|触摸弹出
     *
     * @param canvas            画板
     * @param currentStartAngle 当前的扇形总角度
     * @param sweepAngle        要绘制扇形（模块）的角度
     * @param pie               数据,获取其颜色
     * @param outRectF          最外圈的矩形
     * @param midRectF          白金的矩形
     * @param inRectF           透明区域的矩形
     */
    private void drawArc(Canvas canvas, float currentStartAngle, float sweepAngle, PieData pie, RectF outRectF, RectF midRectF, RectF inRectF, boolean isTouch) {
        int layerID;
        if (!isTouch) {  //没有touch情况  .显示标签
            layerID = canvas.saveLayer(rectFLabel, mPaint, Canvas.ALL_SAVE_FLAG);

            mXPaint.setColor(pie.getLabelColor());
            //drawArc里  useCenter的意思是画出的弧形是否连接中心点，否则就连接头尾两点
            canvas.drawArc(rectFLabel, currentStartAngle, sweepAngle, true, mXPaint);  //标签层写文字

        } else {    //touch 情况
            layerID = canvas.saveLayer(rShadowFF, mPaint, Canvas.ALL_SAVE_FLAG);

            mXPaint.setColor(shadowColor);
            canvas.drawArc(rShadowFF, currentStartAngle - PIE_SPACING, sweepAngle + PIE_SPACING * 2, true, mXPaint);  //画个阴影，左右边填充满间隙


        }
        mXPaint.setColor(Color.WHITE);
        canvas.drawArc(outRectF, currentStartAngle, sweepAngle, true, mXPaint);  //touch情况该层写文字


        mXPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawArc(midRectF, currentStartAngle - PIE_SPACING, sweepAngle + PIE_SPACING * 2, true, mXPaint);  //先把白金区域 擦拭干净。再绘制
        mXPaint.setXfermode(null);


        if (sweepAngle <= (pie.getAngle() - PIE_SPACING) && !isTouch) {       //不要间隔
            sweepAngle += PIE_SPACING;
        }

        mXPaint.setColor(goldColor);
        canvas.drawArc(midRectF, currentStartAngle, sweepAngle, true, mXPaint);  //白金区域

        mXPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawArc(inRectF, -1f, PIE_VIEW_ANGLE + 2f, true, mXPaint);   //透明圈

        Log.d(TAG, "drawArc currentStartAngle==" + currentStartAngle + "+sweepAngle==" + sweepAngle + "  ==" + (currentStartAngle + sweepAngle));
        mXPaint.setXfermode(null);

        canvas.restoreToCount(layerID);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return onPieTouchEvent(event, event.getX(), event.getY());
    }

    /**
     * 该方法可提供给外界使用
     * 即设置当前屏幕触控点对应 我们控件的坐标，可实现功能: 触摸-->(显示我们控件的btn，我们控件显示)-->手指触摸不放，移动手指弹出 模块
     * 使用方法为传入 event, event.getRawX() - pieLocation[0], event.getRawY() - pieLocation[1]
     *
     * @param eventPivotX 在我们控件内对应的 x 坐标
     * @param eventPivotY 在我们控件内对应的 y 坐标
     */
    public boolean onPieTouchEvent(MotionEvent event, float eventPivotX, float eventPivotY) {
        if (mPieData.size() > 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:

                    getParent().requestDisallowInterceptTouchEvent(true);
                    float x = eventPivotX - (mWidth / 2);    //因为我们已经将坐标中心点(0,0)移动到   mWidth / 2,mHeight / 2
                    float y = eventPivotY - (mHeight / 2);

                    float touchAngle = 0;   //Android 绘图坐标和我们的数学认知不同，安卓由第4 - 3 - 2 -1 象限绘制角度
                    if (x < 0) {   //x<0 的时候 ,求出的角度实为 [0°,-90°) ,此时我们给它掰正
                        touchAngle += 180;
                    }
                    touchAngle += Math.toDegrees(Math.atan(y / x));
                    touchAngle = touchAngle - startAngle;
                    if (touchAngle < 0) {
                        touchAngle = touchAngle + 360;
                    }
                    float touchRadius = (float) Math.sqrt(y * y + x * x);//求出触摸的半径

                    //我们用touchSeleteId来表示当前选中的模块
                    if (rIn < touchRadius && touchRadius < rLabel) {//触摸的范围在绘制的区域
                        if (-Arrays.binarySearch(pieAngles, touchAngle) - 1 == touchSeleteId) {   //如果模块有变动(手指挪到其他模块)再绘制
                            return true;   //如果触摸的地方是已经展开的，那么就不再重复绘制
                        }
                        touchSeleteId = -Arrays.binarySearch(pieAngles, touchAngle) - 1;
                        invalidate();
                        Log.d(TAG, " ACTION_DOWN MOVE invalidate()");

                    } else if (0 < touchRadius && touchRadius < rIn) {  //触摸的范围在原点bmp范围

                        if (animatedValue == PIE_VIEW_ANGLE && event.getAction() == MotionEvent.ACTION_DOWN) {  //如果已经全展开
                            showEndAnim();
                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {   //有展开模块时候，手指移动到开关，收起模块
                            touchSeleteId = -1;
                            invalidate();
                        }
                    }

                    return true;


                case MotionEvent.ACTION_UP:

                    if (animatedValue == PIE_VIEW_ANGLE) {     //如果有触摸且已经显示了 那么隐藏
                        showEndAnim();
                        try {
                            Toast.makeText(getContext(), mPieData.get(touchSeleteId).getName(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getParent().requestDisallowInterceptTouchEvent(false);

                    touchSeleteId = -1;
                    invalidate();
                    Log.d(TAG, " ACTION_UP  invalidate()");
                    return true;
            }
        }
        return super.onTouchEvent(event);
    }


    private void init() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(touchTextColor);
        mPaint.setTextSize(touchCenterTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);


        mLabelPaint.setStyle(Paint.Style.FILL);
        mLabelPaint.setAntiAlias(true);
        mLabelPaint.setColor(labelTextColor);
        mLabelPaint.setTextSize(labelTextSize);
        mLabelPaint.setTextAlign(Paint.Align.CENTER);

        mXPaint.setStyle(Paint.Style.FILL);
    }


    /**
     * 在onSizeChanged 后调用，这样才可以拿到 r 和 rGold 的值。否则无法测量 扇形内最大的bitmap
     */
    private void initDate(ArrayList<PieData> mPieData) {

        if (mPieData == null || mPieData.size() == 0)
            return;
        pieAngles = new float[mPieData.size()];
        float sumValue = 0;   //总 权重
        for (int i = 0; i < mPieData.size(); i++) {
            PieData pie = mPieData.get(i);
            sumValue += pie.getWeight();
        }

        float sumAngle = 0;
        for (int i = 0; i < mPieData.size(); i++) {
            PieData pie = mPieData.get(i);
            float percentage = pie.getWeight() / sumValue;
            float angle = percentage * PIE_VIEW_ANGLE;
            pie.setAngle(angle);
            sumAngle += angle;
            pieAngles[i] = sumAngle;

            /****************防止cos90° 取值极其小*********************/

            double centerR = (r + rGold) / 2;
            double maxH = r - rGold;
            double maxW = Math.sin(Math.toRadians(angle / 2)) * centerR * Math.sqrt(2);
            maxW = maxW < 1 ? maxH : maxW;
            maxH = maxH < 1 ? maxW : maxH;
            /****************防止cos90° 取值极其小*********************/

            pie.setMax_drawable_size(Math.min(maxW, maxH) * bmpScale);
            //设置 扇形内最大的bitmap size
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), pie.getDrawableId());
            bitmap = BitmapUtils.zoomImage(bitmap, pie.getMax_drawable_size(), pie.getMax_drawable_size());
            bmpList.add(bitmap);

        }
        touchSeleteId = -1;
    }


    public void showStartAnim() {
        setVisibility(View.VISIBLE);
        startAnimator();
    }


    public void showEndAnim() {
        endAnimator();
        animatedValue = 0;

    }


    private void endAnimator() {
        if (animatorEnd != null) {
            if (animatorStart.isRunning() || animatorEnd.isRunning()) {
                return;
            }
            animatorEnd.start();
        } else {
            animatorEnd = ValueAnimator.ofFloat(PIE_VIEW_ANGLE, 0).setDuration(animatorDuration);
            animatorEnd.setInterpolator(timeInterpolator);
            animatorEnd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                    Log.v(TAG, "animatorEnd invalidate()");

                    if (animatedValue == 0) {
                        setVisibility(View.GONE);
                    }
                }
            });
            animatorEnd.start();
        }
    }


    private void startAnimator() {
        if (animatorStart != null) {
            if (animatorStart.isRunning() || (animatorEnd != null && animatorEnd.isRunning())) {
                return;
            }
            animatorStart.start();
        } else {
            animatorStart = ValueAnimator.ofFloat(0, PIE_VIEW_ANGLE).setDuration(animatorDuration);
            animatorStart.setInterpolator(timeInterpolator);
            animatorStart.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                    Log.v(TAG, "animatorStart invalidate()");

                }
            });
            animatorStart.start();
        }
    }

    /**
     * 绘制文字
     *
     * @param pivotX 文字中心点x
     * @param pivotY 文字中心点y
     */
    private void textCenter(String string, Paint paint, Canvas canvas, int pivotX, int pivotY) {
        //为了验证文字是否画在中心点，可以画两条基线判断一下
//        canvas.drawLine(pivotX - mWidth / 2, pivotY, pivotX + mWidth / 2, pivotY, paint);
//        canvas.drawLine(pivotX, pivotY - mHeight / 2, pivotX, pivotY + mHeight / 2, paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float total = -fontMetrics.ascent + fontMetrics.descent;
        float yAxis = total / 2 - fontMetrics.descent;

        canvas.drawText(string, pivotX, pivotY + yAxis, paint);
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

    /**
     * 设置起始角度
     *
     * @param mStartAngle 起始角度
     */
    public void setStartAngle(float mStartAngle) {
        while (mStartAngle < 0) {
            mStartAngle = mStartAngle + 360;
        }
        while (mStartAngle > 360) {
            mStartAngle = mStartAngle - 360;
        }
        this.startAngle = mStartAngle;
    }

    /**
     * 设置数据
     *
     * @param mPieData 数据
     */
    public void setPieData(ArrayList<PieData> mPieData) {
        this.mPieData = mPieData;
    }

    /**
     * 设置间距
     */
    public void setPieSpacing(float spaing) {
        this.PIE_SPACING = spaing;
    }

    /**
     * 整个view的展示角度
     */
    public void setPieShowAngle(int angle) {
        this.PIE_VIEW_ANGLE = angle;
    }

    /**
     * 写在边缘标签文字的size
     */
    public void setLabelTextSize(int labelTextSize) {
        this.labelTextSize = labelTextSize;
        mLabelPaint.setTextSize(labelTextSize);
    }

    /**
     * 触摸后弹出模块的文字size
     */
    public void setTouchCenterTextSize(int touchCenterTextSize) {
        this.touchCenterTextSize = touchCenterTextSize;
        mPaint.setTextSize(touchCenterTextSize);
    }

    /**
     * 标签文字颜色
     */
    public void setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
        mLabelPaint.setColor(labelTextColor);
    }

    /**
     * 触摸后弹出模块的文字颜色
     */
    public void setTouchTextColor(int touchTextColor) {
        this.touchTextColor = touchTextColor;
        mPaint.setColor(touchTextColor);
    }

    /**
     * 白金模块颜色
     */
    public void setGoldColor(int goldColor) {
        this.goldColor = goldColor;
    }

    /**
     * 触摸后弹出模块的阴影颜色
     */
    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
    }

    /**
     * 中间bmp比例
     */
    public void setBmpScale(float bmpScale) {
        this.bmpScale = bmpScale;
    }

    /**
     * 最里面透明层比例
     */
    public void setRatioInsideRect(double insideRadiusScale) {
        this.insideRadiusScale = insideRadiusScale;
    }

    /**
     * 白金层比例
     */
    public void setRatioGoldRect(double goldRatio) {
        this.goldRadioRectF = goldRatio;
    }

    /**
     * 绘制bitmap层比例
     */
    public void setRatioBmpRect(double widthBmpScaleRadius) {
        this.widthBmpRadioRectF = widthBmpScaleRadius;
    }

    /**
     * 绘制标签层比例
     */
    public void setRatioLabelRect(double labelRadiusScale) {
        this.labelRadioRectF = labelRadiusScale;
    }

    /**
     * 绘制触摸层(弹出层)比例
     */
    public void setTouchRatioRect(double touchScaleRadius) {
        this.touchRatioRectFF = touchScaleRadius;
    }

    /**
     * 中间的bitmap
     */
    public void setBmpCenter(Bitmap bmpCenter) {
        this.bmpCenter = bmpCenter;
    }

    /**
     * 动画持续时间
     */
    public void setAnimatorDuration(long animatorDuration) {
        this.animatorDuration = animatorDuration;
    }
}
