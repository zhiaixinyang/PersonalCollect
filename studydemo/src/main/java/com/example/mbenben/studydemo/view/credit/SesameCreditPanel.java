package com.example.mbenben.studydemo.view.credit;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;

import java.util.ArrayList;

/**
 * Created by wanghan on 16/1/19.
 */
public class SesameCreditPanel extends View {
    private Paint panelPaint;
    private Paint linePaint;
    private Paint topPaint;
    private Paint panelTextPaint;
    private Paint picPaint;
    private Paint progressPaint;
    //进度条范围
    private RectF progressRectF;
    //刻度范围
    private RectF panelRectF;
    private ArrayList<Region> regions;
    private int topHeight;
    private int viewHeight;
    private int viewWidth;
    private int progressRaduis;
    private int progressStroke = 4;
    private int mTikeCount = 31;
    private int panelStroke = 18;
    private int mItemcount = 6;
    private int startAngle = 160;
    private int sweepAngle = 220;
    private PointF centerPoint = new PointF();
    private SesameModel dataModel;
    private float progressSweepAngle = 1;
    //旋转的角度(0.34误差校准)
    private float rAngle = sweepAngle / mTikeCount + 0.34f;
    private float progressTotalSweepAngle;
    private ValueAnimator progressAnimator;
    private String sesameJiFen;
    private int startGradientColor = Color.parseColor("#f24a29");
    private int endGradientColor = Color.parseColor("#1170c1");

    public SesameCreditPanel(Context context) {
        this(context, null);
    }

    public SesameCreditPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SesameCreditPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        panelPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        panelPaint.setColor(Color.argb(125, 255, 255, 255));
        panelPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(1);

        topPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        topPaint.setStyle(Paint.Style.STROKE);
        topPaint.setTextSize(40);
        topPaint.setColor(Color.WHITE);

        panelTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        panelTextPaint.setStyle(Paint.Style.STROKE);

        picPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        picPaint.setStyle(Paint.Style.STROKE);
        picPaint.setColor(Color.WHITE);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(Color.WHITE);
        progressPaint.setStrokeWidth(progressStroke);

        regions = new ArrayList<Region>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (dataModel != null && dataModel.getSesameItemModels() != null && dataModel.getSesameItemModels().size() != 0) {
            viewWidth = w;
            viewHeight = h;
            progressRaduis = (w / 2) * 7 / 10;
            topHeight = (w / 2) * 3 / 10;
            centerPoint.set(viewWidth / 2, viewHeight / 2);
            sesameJiFen = String.valueOf(dataModel.getTotalMin());

            progressTotalSweepAngle = computeProgressAngle();
            Panel startPanel = new Panel();
            startPanel.setStartSweepAngle(1);
            startPanel.setStartSweepValue(dataModel.getTotalMin());

            Panel endPanel = new Panel();
            endPanel.setEndSweepAngle(progressTotalSweepAngle);
            endPanel.setEndSweepValue(dataModel.getUserTotal());

            progressAnimator = ValueAnimator.ofObject(new creditEvaluator(), startPanel, endPanel);
            progressAnimator.setDuration(5000);
            progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Panel panel = (Panel) animation.getAnimatedValue();
                    //更新进度值
                    progressSweepAngle = panel.getSesameSweepAngle();
                    sesameJiFen = String.valueOf(panel.getSesameSweepValue());
                    invalidateView();
                }
            });

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressAnimator.start();
                }
            }, 1000);
        }

    }

    private class creditEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Panel resultPanel = new Panel();
            Panel startPanel = (Panel) startValue;
            Panel endPanel = (Panel) endValue;
            //开始扫描角度,从1度开始扫描
            float startSweepAngle = startPanel.getStartSweepAngle();
            //结束扫描的角度,为计算出来的用户信用分在仪表盘上扫描过的角度
            float endSweepAngle = endPanel.getEndSweepAngle();
            float sesameSweepAngle = startSweepAngle + fraction * (endSweepAngle - startSweepAngle);
            //计算出来进度条变化时变化的角度
            resultPanel.setSesameSweepAngle(sesameSweepAngle);
            //开始扫描的值,为起始刻度350
            float startSweepValue = startPanel.getStartSweepValue();
            //结束扫描的值,为用户的信用分
            float endSweepValue = endPanel.getEndSweepValue();
            //计算出进度条在变化的时候信用分的值
            float sesameSweepValue = startSweepValue + fraction * (endSweepValue - startSweepValue);
            resultPanel.setSesameSweepValue((int) sesameSweepValue);
            return resultPanel;

        }
    }

    /**
     * 计算用户信用分所占角度
     */
    private float computeProgressAngle() {
        ArrayList<SesameItemModel> list = dataModel.getSesameItemModels();
        int userTotal = dataModel.getUserTotal();
        float progressAngle = 0;
        for (int i = 0; i < list.size(); i++) {
            if (userTotal > list.get(i).getMax()) {
                progressAngle += mItemcount * rAngle;
                continue;
            }
            int blance = userTotal - list.get(i).getMin();
            float areaItem = (list.get(i).getMax() - list.get(i).getMin()) / mItemcount;
            progressAngle += (blance / areaItem) * rAngle;
            if (blance % areaItem != 0) {
                blance -= (blance / areaItem) * areaItem;
                float percent = (blance / areaItem);
                progressAngle += (int) (percent * rAngle);
            }
            break;
        }
        return progressAngle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataModel != null && dataModel.getSesameItemModels() != null && dataModel.getSesameItemModels().size() != 0) {
            drawBackground(canvas);
            drawTopText(canvas);
            drawPanel(canvas);
            drawBottomPic(canvas);
        }
    }

    private void drawBottomPic(Canvas canvas) {
        regions.clear();
        Bitmap b;
        float picViewX = viewWidth / 4;
        float picViewY = viewHeight * 8.5f / 10;

        b = getBitmapByResource(getResources(), R.mipmap.ic_fen, 0, 0);
        float picX = picViewX - b.getWidth() / 2;
        float picY = picViewY - b.getHeight() / 2;
        canvas.drawBitmap(b, picX, picY, null);
        Region region = new Region((int) picX, (int) picY, (int) (picX + b.getWidth()), (int) (picY + b.getHeight()));
        regions.add(region);

        b = getBitmapByResource(getResources(), R.mipmap.ic_up, 0, 0);
        picX = 3 * picViewX - b.getWidth() / 2;
        picY = picViewY - b.getHeight() / 2;
        canvas.drawBitmap(b, picX, picY, null);
        region = new Region((int) picX, (int) picY, (int) (picX + b.getWidth()), (int) (picY + b.getHeight()));
        regions.add(region);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
            if (regions.size() == 0) {
                return true;
            }
            for (int i = 0; i < regions.size(); i++) {
                Region mPicArea = regions.get(i);
                if (mPicArea.contains((int) event.getX(), (int) event.getY())) {
                    Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
        return true;
    }


    private void drawTopText(Canvas canvas) {
        Rect rectF = new Rect();
        topPaint.getTextBounds(dataModel.getTopText(), 0, dataModel.getTopText().length(), rectF);
        canvas.drawText(dataModel.getTopText(), centerPoint.x - rectF.width() / 2, topHeight, topPaint);
        Path path = new Path();
        Paint paint = new Paint(topPaint);
        paint.setStrokeWidth(2);
        //左边线
        path.moveTo(centerPoint.x - rectF.width() / 2 - 20, topHeight - rectF.height() / 3);
        path.lineTo(centerPoint.x - rectF.width() / 2 - 20 - rectF.width() / 4, topHeight - rectF.height() / 3);
        //右边线
        path.moveTo(centerPoint.x + rectF.width() / 2 + 20, topHeight - rectF.height() / 3);
        path.lineTo(centerPoint.x + rectF.width() / 2 + 20 + rectF.width() / 4, topHeight - rectF.height() / 3);
        canvas.drawPath(path, paint);
    }

    private void drawBackground(Canvas canvas) {
        int startColor = computeGradientColor(startGradientColor, endGradientColor, progressSweepAngle, progressTotalSweepAngle);
        canvas.drawColor(startColor);
    }

    /**
     * 绘制仪表盘
     */
    private void drawPanel(Canvas canvas) {
        panelPaint.setStrokeWidth(progressStroke);
        progressRectF = new RectF(centerPoint.x - progressRaduis, centerPoint.y - progressRaduis, centerPoint.x + progressRaduis, centerPoint.y + progressRaduis);
        canvas.drawArc(progressRectF, startAngle, sweepAngle, false, panelPaint);
        canvas.drawArc(progressRectF, startAngle, progressSweepAngle, false, progressPaint);

        panelPaint.setStrokeWidth(panelStroke);
        int panelRadius = progressRaduis * 9 / 10;
        panelRectF = new RectF(centerPoint.x - panelRadius, centerPoint.y - panelRadius, centerPoint.x + panelRadius, centerPoint.y + panelRadius);
        canvas.drawArc(panelRectF, startAngle, sweepAngle, false, panelPaint);
        canvas.save();
        //旋转-110度,即坐标系270度位置  即垂直方向,便于计算
        canvas.rotate(-110, centerPoint.x, centerPoint.y);
        drawLineAndText(canvas);
        canvas.restore();
        drawPanelText(canvas);
    }

    /**
     * 绘制仪表盘文本
     */
    private void drawPanelText(Canvas canvas) {
        float drawTextY, textSpace = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        Rect rect = new Rect();

        String text = dataModel.getFirstText();
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.parseColor("#e5e5e5"));
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = centerPoint.y - panelRectF.height() / 2 * 0.45f;
        canvas.drawText(text, centerPoint.x - rect.width() / 2, drawTextY, panelTextPaint);

        text = sesameJiFen;
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 70, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(true);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerPoint.x - rect.width() / 2, drawTextY, panelTextPaint);

        text = dataModel.getAssess();
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerPoint.x - rect.width() / 2, drawTextY, panelTextPaint);


        text = dataModel.getFourText();
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerPoint.x - rect.width() / 2, drawTextY, panelTextPaint);
    }

    /**
     * 绘制仪表盘刻度以及刻度文字
     */
    private void drawLineAndText(Canvas canvas) {
        ArrayList<SesameItemModel> sesameItemdataModels = dataModel.getSesameItemModels();
        for (int i = 0; i < sesameItemdataModels.size(); i++) {
            SesameItemModel sesameItem = sesameItemdataModels.get(i);
            for (int j = 0; j < mItemcount; j++) {
                if (j == 0) {
                    //大刻度
                    linePaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawLine(centerPoint.x, panelRectF.top - panelStroke / 2, centerPoint.x, panelRectF.top + panelStroke / 2, linePaint);
                } else {
                    //小刻度
                    linePaint.setColor(Color.parseColor("#e5e5e5"));
                    canvas.drawLine(centerPoint.x, panelRectF.top - panelStroke / 2, centerPoint.x, panelRectF.top + panelStroke / 4, linePaint);
                }
                String itemMin = String.valueOf(sesameItem.getMin());
                String itemMax = String.valueOf(sesameItem.getMax());
                String itemType = sesameItem.getArea();
                Rect rect = new Rect();
                if (j == 0) {
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemMin, 0, itemMin.length(), rect);
                    canvas.drawText(itemMin, centerPoint.x - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
                if (j == 3) {
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemType, 0, itemType.length(), rect);
                    canvas.drawText(itemType, centerPoint.x - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
                canvas.rotate(rAngle, centerPoint.x, centerPoint.y);
                //补足最后一个大刻度
                if (i == sesameItemdataModels.size() - 1 && j == 5) {
                    //最后大刻度
                    linePaint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawLine(centerPoint.x, panelRectF.top - panelStroke / 2, centerPoint.x, panelRectF.top + panelStroke / 2, linePaint);
                    //最后一个文本
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemMax, 0, itemMax.length(), rect);
                    canvas.drawText(itemMax, centerPoint.x - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
            }
        }
    }

    public static Bitmap getBitmapByResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqheight) {
        if (reqWidth <= 0 || reqheight <= 0) {
            return 1;
        }
        int inSampleSize = 1;
        final int bitmapWidth = options.outWidth;
        final int bitmapHeight = options.outHeight;
        if (bitmapWidth > reqWidth || bitmapHeight > reqheight) {
            final int bitmapHalfWidth = bitmapWidth / 2;
            final int bitmapHalfHeight = bitmapHeight / 2;
            while ((bitmapHalfWidth / inSampleSize) > reqWidth && (bitmapHalfHeight / inSampleSize) > reqheight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    /**
     * 根据当前进度条变化的角度与总角度计算两个颜色的变化期间的颜色值
     * */
    private int computeGradientColor(int startColor, int endColor, float curAngle, float totalAngle) {
        int r, g, b;
        r = (int) (Color.red(startColor) + (Color.red(endColor) - Color.red(startColor)) * curAngle / totalAngle);
        g = (int) (Color.green(startColor) + (Color.green(endColor) - Color.green(startColor)) * curAngle / totalAngle);
        b = (int) (Color.blue(startColor) + (Color.blue(endColor) - Color.blue(startColor)) * curAngle / totalAngle);
        return Color.rgb(r, g, b);
    }

    public void setDataModel(SesameModel datadataModel) {
        this.dataModel = datadataModel;
        invalidateView();
    }

}
