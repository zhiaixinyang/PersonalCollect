package com.example.mbenben.studydemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/4/11.
 */
@Entity
public class ContentProviderInfo {
    @Id(autoincrement = true)
    public Long id;
    public String mContent;
    public String mName;
    @Generated(hash = 625570452)
    public ContentProviderInfo(Long id, String mContent, String mName) {
        this.id = id;
        this.mContent = mContent;
        this.mName = mName;
    }
    @Generated(hash = 503421339)
    public ContentProviderInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMContent() {
        return this.mContent;
    }
    public void setMContent(String mContent) {
        this.mContent = mContent;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }
}
