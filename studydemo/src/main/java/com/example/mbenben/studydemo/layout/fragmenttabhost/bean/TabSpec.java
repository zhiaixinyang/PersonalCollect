package com.example.mbenben.studydemo.layout.fragmenttabhost.bean;

/**
 * Created by MBENBEN on 2016/9/26.
 */
public class TabSpec {
    private String title;
    private int icon;
    private Class fragment;

    public TabSpec(String title, int icon, Class fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
