package com.example.mbenben.studydemo.basenote.ballwidget.widget;

import android.support.annotation.ColorInt;

/**
 * @author MDove on 2018/4/19.
 */
class Configuration {

    private final float width;
    private final float height;
    @ColorInt
    private final int crossColor;
    @ColorInt
    private final int crossOverlappedColor;
    private final float crossStrokeWidth;

    /**
     * 悬浮球的shadow
     */
    private float shadowRadius;
    private float shadowDx;
    private float shadowDy;
    @ColorInt
    private int shadowColor;

    public Configuration(Builder builder) {
        this.width = builder.width;
        this.height = builder.radius;
        this.crossColor = builder.crossColor;
        this.crossOverlappedColor = builder.crossOverlappedColor;
        this.crossStrokeWidth = builder.crossStrokeWidth;
        this.shadowRadius = builder.shadowRadius;
        this.shadowDx = builder.shadowDx;
        this.shadowDy = builder.shadowDy;
        this.shadowColor = builder.shadowColor;
    }

    float widgetWidth() {
        return width;
    }

    float radius() {
        return height;
    }

    @ColorInt
    int crossColor() {
        return crossColor;
    }

    @ColorInt
    int crossOverlappedColor() {
        return crossOverlappedColor;
    }

    float crossStrokeWidth() {
        return crossStrokeWidth;
    }

    float shadowRadius() {
        return shadowRadius;
    }

    float shadowDx() {
        return shadowDx;
    }

    float shadowDy() {
        return shadowDy;
    }

    int shadowColor() {
        return shadowColor;
    }

    static final class Builder {
        private float width;
        private float radius;
        /**
         * 叉子的颜色
         */
        private int crossColor;
        /**
         * 删除选中时叉子的颜色
         */
        private int crossOverlappedColor;
        private float crossStrokeWidth;

        /**
         * 悬浮球的shadow
         */
        private float shadowRadius;
        private float shadowDx;
        private float shadowDy;
        @ColorInt
        private int shadowColor;

        Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        Builder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        Builder setCrossColor(@ColorInt int crossColor) {
            this.crossColor = crossColor;
            return this;
        }

        Builder setCrossOverlappedColor(@ColorInt int crossOverlappedColor) {
            this.crossOverlappedColor = crossOverlappedColor;
            return this;
        }

        Builder setCrossStrokeWidth(float crossStrokeWidth) {
            this.crossStrokeWidth = crossStrokeWidth;
            return this;
        }

        Builder setShadowRadius(float shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        Builder setShadowDx(float shadowDx) {
            this.shadowDx = shadowDx;
            return this;
        }

        Builder setShadowDy(float shadowDy) {
            this.shadowDy = shadowDy;
            return this;
        }

        Builder setShadowColor(@ColorInt int shadowColor) {
            this.shadowColor = shadowColor;
            return this;
        }

        Configuration build() {
            return new Configuration(this);
        }
    }
}
