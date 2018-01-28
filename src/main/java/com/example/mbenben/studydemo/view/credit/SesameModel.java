package com.example.mbenben.studydemo.view.credit;

import java.io.Serializable;
import java.lang.String;import java.util.ArrayList;

/**
 * Created by wanghan on 16/1/22.
 */
public class SesameModel implements Serializable {
    private int userTotal;//用户信用分
    private String assess;//评价
    private int totalMin;//区间最小值
    private int totalMax;//区间最大值
    private String firstText;//第一个文本值:BETA
    private String fourText;//第四个文本:评估时间
    private String topText;//顶部文本
    private ArrayList<SesameItemModel> sesameItemModels;

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getFirstText() {
        return firstText;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public String getFourText() {
        return fourText;
    }

    public void setFourText(String fourText) {
        this.fourText = fourText;
    }

    public int getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(int userTotal) {
        this.userTotal = userTotal;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public int getTotalMin() {
        return totalMin;
    }

    public void setTotalMin(int totalMin) {
        this.totalMin = totalMin;
    }

    public int getTotalMax() {
        return totalMax;
    }

    public void setTotalMax(int totalMax) {
        this.totalMax = totalMax;
    }

    public ArrayList<SesameItemModel> getSesameItemModels() {
        return sesameItemModels;
    }

    public void setSesameItemModels(ArrayList<SesameItemModel> sesameItemModels) {
        this.sesameItemModels = sesameItemModels;
    }
}
