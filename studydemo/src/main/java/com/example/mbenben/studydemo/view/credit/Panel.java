package com.example.mbenben.studydemo.view.credit;

/**
 * Created by wanghan on 16/1/23.
 */
public class Panel {
    private float startSweepAngle;//扫描开始角度
    private float endSweepAngle;//扫描结束角度
    private float startSweepValue;//扫描开始过程对应的值
    private float endSweepValue;//扫描结束过程对应的值
    private float sesameSweepAngle;//计算出来的扫描中的角度
    private int sesameSweepValue;//计算出来的扫描中的角度对应的信用值

    public float getStartSweepAngle() {
        return startSweepAngle;
    }

    public void setStartSweepAngle(float startSweepAngle) {
        this.startSweepAngle = startSweepAngle;
    }

    public float getEndSweepAngle() {
        return endSweepAngle;
    }

    public void setEndSweepAngle(float endSweepAngle) {
        this.endSweepAngle = endSweepAngle;
    }

    public float getStartSweepValue() {
        return startSweepValue;
    }

    public void setStartSweepValue(float startSweepValue) {
        this.startSweepValue = startSweepValue;
    }

    public float getEndSweepValue() {
        return endSweepValue;
    }

    public void setEndSweepValue(float endSweepValue) {
        this.endSweepValue = endSweepValue;
    }

    public float getSesameSweepAngle() {
        return sesameSweepAngle;
    }

    public void setSesameSweepAngle(float sesameSweepAngle) {
        this.sesameSweepAngle = sesameSweepAngle;
    }

    public int getSesameSweepValue() {
        return sesameSweepValue;
    }

    public void setSesameSweepValue(int sesameSweepValue) {
        this.sesameSweepValue = sesameSweepValue;
    }
}
