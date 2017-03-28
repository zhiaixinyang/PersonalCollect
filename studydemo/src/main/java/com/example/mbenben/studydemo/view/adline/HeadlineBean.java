package com.example.mbenben.studydemo.view.adline;

/**
 * Created by MBENBEN on 2017/1/31.
 */

public class HeadlineBean {
    private String title;
    private String content;

    public HeadlineBean() {
    }


    public HeadlineBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HeadlineBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }





}
