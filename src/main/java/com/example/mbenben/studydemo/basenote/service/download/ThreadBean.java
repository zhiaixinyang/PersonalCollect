package com.example.mbenben.studydemo.basenote.service.download;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class ThreadBean implements Serializable {
    private int id;
    private String url;
    private int startLength;
    private int endLength;
    private int finshedLength;

    public ThreadBean() {
    }

    public ThreadBean(int id, String url, int startLength, int endLength, int finshedLength) {
        this.id = id;
        this.url = url;
        this.startLength = startLength;
        this.endLength = endLength;
        this.finshedLength = finshedLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStartLength() {
        return startLength;
    }

    public void setStartLength(int startLength) {
        this.startLength = startLength;
    }

    public int getEndLength() {
        return endLength;
    }

    public void setEndLength(int endLength) {
        this.endLength = endLength;
    }

    public int getFinshedLength() {
        return finshedLength;
    }

    public void setFinshedLength(int finshedLength) {
        this.finshedLength = finshedLength;
    }

    @Override
    public String toString() {
        return "ThreadBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", startLength=" + startLength +
                ", endLength=" + endLength +
                ", finshedLength=" + finshedLength +
                '}';
    }
}
