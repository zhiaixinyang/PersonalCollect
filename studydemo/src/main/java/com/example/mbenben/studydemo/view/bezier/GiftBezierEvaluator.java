package com.example.mbenben.studydemo.view.bezier;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by MBENBEN on 2017/1/12.
 *
 * 原作者项目GitHub：https://github.com/AlanCheen/PeriscopeLayout
 */

public class GiftBezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF1;
    private PointF pointF2;

    public GiftBezierEvaluator(PointF pointF1, PointF pointF2) {

        this.pointF1 = pointF1;
        this.pointF2 = pointF2;

    }

    @Override
    public PointF evaluate(float fraction, PointF pointF0, PointF pointF3) {
        // TODO Auto-generated method stub

        PointF pointF = new PointF();
        //贝塞尔曲线的实现.根据贝塞尔曲线的公式得到x.y的值
        pointF.x = (float) ((pointF0.x * (Math.pow((1 - fraction), 3))) + 3
                * pointF1.x * fraction * (Math.pow((1 - fraction), 2)) + 3
                * pointF2.x * (Math.pow(fraction, 2) * (1 - fraction)) + pointF3.x
                * (Math.pow(fraction, 3)));

        pointF.y = (float) ((pointF0.y * (Math.pow((1 - fraction), 3))) + 3
                * pointF1.y * fraction * (Math.pow((1 - fraction), 2)) + 3
                * pointF2.y * (Math.pow(fraction, 2) * (1 - fraction)) + pointF3.y
                * (Math.pow(fraction, 3)));

        return pointF;
    }

}

