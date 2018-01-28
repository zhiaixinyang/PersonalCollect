package com.example.mbenben.studydemo.layout.drawerlayout;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import static android.graphics.Color.BLACK;
import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.Cap;
import static android.graphics.Paint.Cap.BUTT;
import static android.graphics.Paint.Cap.ROUND;
import static android.graphics.Paint.SUBPIXEL_TEXT_FLAG;
import static android.graphics.Paint.Style.STROKE;
import static android.graphics.PixelFormat.TRANSLUCENT;
import static android.support.v4.widget.DrawerLayout.DrawerListener;
import static java.lang.Math.sqrt;

/**
 * Created by MBENBEN on 2016/12/28.
 *
 * 原作者项目GitHub：https://github.com/ChrisRenke/DrawerArrowDrawable
 */

public class DrawerArrowDrawable extends Drawable {


    private static class JoinedPath {

        /**
         * 简单来说PathMeasure的作用是：
         * 获取Path的总长度，也可以快捷的获取Path中某点的位置坐标。
         */
        private final PathMeasure measureFirst;
        private final PathMeasure measureSecond;
        private final float lengthFirst;
        private final float lengthSecond;

        private JoinedPath(Path pathFirst, Path pathSecond) {
            /**
             * 官方注释：
             * 创建与指定的路径对象（已创建和指定）相关联的PathMeasure对象。
             * 度量对象现在可以返回路径的长度，以及沿路径的任何位置的位置和切线。
             * 请注意，一旦路径与度量对象关联，如果随后修改路径并且使用度量对象，则它是未定义的。
             * 如果路径被修改，则必须使用路径调用setPath。
             * @param path将由此对象测量的路径
             * @param forceClosed如果为true，则即使其路径未明确关闭，该路径也将被视为“关闭”。
             */
            measureFirst = new PathMeasure(pathFirst, false);
            measureSecond = new PathMeasure(pathSecond, false);
            lengthFirst = measureFirst.getLength();
            lengthSecond = measureSecond.getLength();
        }

        /**
         * 作者注释：
         * 返回此曲线上给定{@code parameter}处的点。
         * 对于{@code parameter}值小于.5f，第一个路径将驱动点。
         * 对于大于.5f的{@code parameter}值，第二个路径将驱动点。
         * 对于{@code parameter}等于.5f，点将是两个内部路径连接的点。
         */
        private void getPointOnLine(float parameter, float[] coords) {
            if (parameter <= .5f) {
                parameter *= 2;
                /**
                 * getPosTan(float distance, float pos[], float tan[])
                 *
                 * 通过此方法，我们可以拿到distance中的位置坐标，并且封装到了
                 * pos[]中。
                 */
                measureFirst.getPosTan(lengthFirst * parameter, coords, null);
            } else {
                parameter -= .5f;
                parameter *= 2;
                measureSecond.getPosTan(lengthSecond * parameter, coords, null);
            }
        }
    }

    /**
     * 作者注释：
     * 在距离为{@code parameter}的两个{@link JoinedPath}之间沿着每个路径绘制一条线。
     */
    private class BridgingLine {

        private final JoinedPath pathA;
        private final JoinedPath pathB;

        private BridgingLine(JoinedPath pathA, JoinedPath pathB) {
            this.pathA = pathA;
            this.pathB = pathB;
        }

        /**
         * 作者注释：
         * 在当前参数下，在返回{@code measureA}和{@code measureB}的路径上定义的点之间绘制一条线。
         */
        private void draw(Canvas canvas) {
            pathA.getPointOnLine(parameter, coordsA);
            pathB.getPointOnLine(parameter, coordsB);
            if (rounded) insetPointsForRoundCaps();
            //coordsA当前路线的坐标，通过PathMeasure的getPosTan方法获得。
            canvas.drawLine(coordsA[0], coordsA[1], coordsB[0], coordsB[1], linePaint);
        }

        /**
         * 作者注释：
         * 插入当前线的端点，以考虑为{@link Cap＃ROUND}样式线绘制的突出端。
         */
        private void insetPointsForRoundCaps() {
            vX = coordsB[0] - coordsA[0];
            vY = coordsB[1] - coordsA[1];

            magnitude = (float) sqrt((vX * vX + vY * vY));
            paramA = (magnitude - halfStrokeWidthPixel) / magnitude;
            paramB = halfStrokeWidthPixel / magnitude;

            coordsA[0] = coordsB[0] - (vX * paramA);
            coordsA[1] = coordsB[1] - (vY * paramA);
            coordsB[0] = coordsB[0] - (vX * paramB);
            coordsB[1] = coordsB[1] - (vY * paramB);
        }
    }

    /** 路径以3px / dp密度生成; 这是不同密度的比例因子。 */
    private final static float PATH_GEN_DENSITY = 3;

    /** 以{@link DrawerArrowDrawable＃PATH_GEN_DENSITY}的大小生成路径。 */
    private final static float DIMEN_DP = 23.5f;

    /**
     * 生成的目标轨迹宽度正确形成箭头，修改可能导致箭头不是很好的情况。
     */
    private final static float STROKE_WIDTH_DP = 2;

    private BridgingLine topLine;
    private BridgingLine middleLine;
    private BridgingLine bottomLine;

    private final Rect bounds;
    private final float halfStrokeWidthPixel;
    private final Paint linePaint;
    private final boolean rounded;

    private boolean flip;
    private float parameter;

    // 绘图计算期间的辅助字段。
    private float vX, vY, magnitude, paramA, paramB;
    private final float coordsA[] = { 0f, 0f };
    private final float coordsB[] = { 0f, 0f };

    public DrawerArrowDrawable(Resources resources) {
        this(resources, false);
    }

    public DrawerArrowDrawable(Resources resources, boolean rounded) {
        this.rounded = rounded;
        float density = resources.getDisplayMetrics().density;
        float strokeWidthPixel = STROKE_WIDTH_DP * density;
        halfStrokeWidthPixel = strokeWidthPixel / 2;

        linePaint = new Paint(SUBPIXEL_TEXT_FLAG | ANTI_ALIAS_FLAG);
        linePaint.setStrokeCap(rounded ? ROUND : BUTT);
        linePaint.setColor(BLACK);
        linePaint.setStyle(STROKE);
        linePaint.setStrokeWidth(strokeWidthPixel);

        int dimen = (int) (DIMEN_DP * density);
        bounds = new Rect(0, 0, dimen, dimen);

        Path first, second;
        JoinedPath joinedA, joinedB;

        // 按钮的第一条线的相关路径操作
        first = new Path();
        first.moveTo(5.042f, 20f);
        // 三阶贝塞尔曲线：说白了就是给定三个坐标点（三阶），系统通过贝塞尔方程画出一个线来。
        first.rCubicTo(8.125f, -16.317f, 39.753f, -27.851f, 55.49f, -2.765f);
        second = new Path();
        second.moveTo(60.531f, 17.235f);
        second.rCubicTo(11.301f, 18.015f, -3.699f, 46.083f, -23.725f, 43.456f);
        //将路径缩放到给定的屏幕密度。（让按钮大小适配屏幕）
        scalePath(first, density);
        scalePath(second, density);
        joinedA = new JoinedPath(first, second);

        first = new Path();
        first.moveTo(64.959f, 20f);
        first.rCubicTo(4.457f, 16.75f, 1.512f, 37.982f, -22.557f, 42.699f);
        second = new Path();
        second.moveTo(42.402f, 62.699f);
        second.cubicTo(18.333f, 67.418f, 8.807f, 45.646f, 8.807f, 32.823f);
        scalePath(first, density);
        scalePath(second, density);
        joinedB = new JoinedPath(first, second);
        topLine = new BridgingLine(joinedA, joinedB);

        // 按钮的第二条线的相关路径操作
        first = new Path();
        first.moveTo(5.042f, 35f);
        first.cubicTo(5.042f, 20.333f, 18.625f, 6.791f, 35f, 6.791f);
        second = new Path();
        second.moveTo(35f, 6.791f);
        second.rCubicTo(16.083f, 0f, 26.853f, 16.702f, 26.853f, 28.209f);
        scalePath(first, density);
        scalePath(second, density);
        joinedA = new JoinedPath(first, second);

        first = new Path();
        first.moveTo(64.959f, 35f);
        first.rCubicTo(0f, 10.926f, -8.709f, 26.416f, -29.958f, 26.416f);
        second = new Path();
        second.moveTo(35f, 61.416f);
        second.rCubicTo(-7.5f, 0f, -23.946f, -8.211f, -23.946f, -26.416f);
        scalePath(first, density);
        scalePath(second, density);
        joinedB = new JoinedPath(first, second);
        middleLine = new BridgingLine(joinedA, joinedB);

        // 按钮的第三条线的相关路径操作
        first = new Path();
        first.moveTo(5.042f, 50f);
        first.cubicTo(2.5f, 43.312f, 0.013f, 26.546f, 9.475f, 17.346f);
        second = new Path();
        second.moveTo(9.475f, 17.346f);
        second.rCubicTo(9.462f, -9.2f, 24.188f, -10.353f, 27.326f, -8.245f);
        scalePath(first, density);
        scalePath(second, density);
        joinedA = new JoinedPath(first, second);

        first = new Path();
        first.moveTo(64.959f, 50f);
        first.rCubicTo(-7.021f, 10.08f, -20.584f, 19.699f, -37.361f, 12.74f);
        second = new Path();
        second.moveTo(27.598f, 62.699f);
        second.rCubicTo(-15.723f, -6.521f, -18.8f, -23.543f, -18.8f, -25.642f);
        scalePath(first, density);
        scalePath(second, density);
        joinedB = new JoinedPath(first, second);
        bottomLine = new BridgingLine(joinedA, joinedB);
    }

    @Override public int getIntrinsicHeight() {
        return bounds.height();
    }

    @Override public int getIntrinsicWidth() {
        return bounds.width();
    }

    @Override public void draw(Canvas canvas) {
        if (flip) {
            canvas.save();
            canvas.scale(1f, -1f, getIntrinsicWidth() / 2, getIntrinsicHeight() / 2);
        }

        topLine.draw(canvas);
        middleLine.draw(canvas);
        bottomLine.draw(canvas);

        if (flip) canvas.restore();
    }

    @Override public void setAlpha(int alpha) {
        linePaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override public void setColorFilter(ColorFilter cf) {
        linePaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override public int getOpacity() {
        return TRANSLUCENT;
    }

    public void setStrokeColor(int color) {
        linePaint.setColor(color);
        invalidateSelf();
    }

    /**
     * 通常通过{@link DrawerListener＃onDrawerSlide（View，float）}的
     * {@code slideOffset}参数来设置这个drawable的旋转，它基于{@code parameter}在0到1之间。
     */
    public void setParameter(float parameter) {
        if (parameter > 1 || parameter < 0) {
            throw new IllegalArgumentException("Value must be between 1 and zero inclusive!");
        }
        this.parameter = parameter;
        invalidateSelf();
    }

    /**
     * 当为false时，从抽屉图标和返回箭头之间的3点钟到9点钟旋转。
     * 当为true时，从9点钟到3点钟位置，在返回箭头和抽屉图标之间旋转。
     */
    public void setFlip(boolean flip) {
        this.flip = flip;
        invalidateSelf();
    }

    /**
     * 将路径缩放到给定的屏幕密度。
     * 如果密度匹配{@link DrawerArrowDrawable＃PATH_GEN_DENSITY}，则不需要进行缩放。
     */
    private static void scalePath(Path path, float density) {
        if (density == PATH_GEN_DENSITY) return;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(density / PATH_GEN_DENSITY, density / PATH_GEN_DENSITY, 0, 0);
        path.transform(scaleMatrix);
    }
}