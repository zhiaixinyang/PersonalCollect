package com.example.mbenben.studydemo.model;

/**
 * Created by MBENBEN on 2017/3/31.
 */

public class HashSetSearchBean {
    private String name;
    private Class activityClass;

    public HashSetSearchBean(String name, Class activityClass) {
        this.name = name;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }
}
