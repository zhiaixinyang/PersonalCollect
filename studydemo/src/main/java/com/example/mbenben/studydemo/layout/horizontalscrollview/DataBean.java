package com.example.mbenben.studydemo.layout.horizontalscrollview;

/**
 * Created by MBENBEN on 2017/3/5.
 */

public class DataBean {
    private int imageId;
    private String text;

    public DataBean(int imageId, String text) {
        this.imageId = imageId;
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
