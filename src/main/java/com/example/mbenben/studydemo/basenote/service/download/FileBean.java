package com.example.mbenben.studydemo.basenote.service.download;

import java.io.Serializable;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class FileBean implements Serializable{
    private int id;
    private String url;
    private String fileName;
    private int length;
    private int finshLength;

    public FileBean() {
    }

    public FileBean(int id, String url, String fileName, int length, int finshLength) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finshLength = finshLength;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinshLength() {
        return finshLength;
    }

    public void setFinshLength(int finshLength) {
        this.finshLength = finshLength;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finshLength=" + finshLength +
                '}';
    }
}
