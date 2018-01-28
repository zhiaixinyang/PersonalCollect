package com.example.mbenben.studydemo.basenote.plugin;

/**
 * Created by MBENBEN on 2017/6/14.
 */
public class PluginBean {
    private String appName;
    private String packageName;

    public PluginBean(String appName, String packageName) {
        this.appName = appName;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
