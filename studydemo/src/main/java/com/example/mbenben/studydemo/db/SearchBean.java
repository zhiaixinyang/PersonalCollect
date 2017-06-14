package com.example.mbenben.studydemo.db;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2017/3/28.
 */

public class SearchBean implements Serializable{
    private String activity;
    private String title;
    private Class activityClass;

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
