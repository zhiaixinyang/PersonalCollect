package com.example.mbenben.studydemo.layout.recyclerview.model;

/**
 * Created by MBENBEN on 2017/1/1.
 */

public class DataBean {
    public static int TYPE_ONE=1;
    public static int TYPE_TWO=2;
    public static int TYPE_THREE=3;

    private int type=1;
    private String tvOne;
    private int ivTwo;
    private String tvThreeOne;
    private String tvThreeTwo;
    private int ivThree;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTvOne() {
        return tvOne;
    }

    public void setTvOne(String tvOne) {
        this.tvOne = tvOne;
    }

    public int getIvTwo() {
        return ivTwo;
    }

    public void setIvTwo(int ivTwo) {
        this.ivTwo = ivTwo;
    }

    public String getTvThreeOne() {
        return tvThreeOne;
    }

    public void setTvThreeOne(String tvThreeOne) {
        this.tvThreeOne = tvThreeOne;
    }

    public String getTvThreeTwo() {
        return tvThreeTwo;
    }

    public void setTvThreeTwo(String tvThreeTwo) {
        this.tvThreeTwo = tvThreeTwo;
    }

    public int getIvThree() {
        return ivThree;
    }

    public void setIvThree(int ivThree) {
        this.ivThree = ivThree;
    }
}
