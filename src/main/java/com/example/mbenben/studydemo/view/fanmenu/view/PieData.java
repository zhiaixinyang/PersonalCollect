package com.example.mbenben.studydemo.view.fanmenu.view;


public class PieData {
    private String name;
    //缩写
    private String name_label;
    //设置权重，默认为1
    private float weight = 1;
    //标签层颜色
    private int labelColor = 0;
    private float angle = 0;
    //内切bitmap的最大宽/高
    private double max_drawable_size;

    public String getName_label() {
        return name_label;
    }

    public void setName_label(String name_label) {
        this.name_label = name_label;
    }

    public double getMax_drawable_size() {
        return max_drawable_size;
    }

    public void setMax_drawable_size(double max_drawable_size) {
        this.max_drawable_size = max_drawable_size;
    }

    private int drawableId;

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


}
