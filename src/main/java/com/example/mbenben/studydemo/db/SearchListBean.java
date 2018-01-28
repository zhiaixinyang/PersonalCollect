package com.example.mbenben.studydemo.db;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MBENBEN on 2017/3/28.
 */

public class SearchListBean implements Serializable{
    private List<SearchBean> data;

    public List<SearchBean> getData() {
        return data;
    }

    public void setData(List<SearchBean> data) {
        this.data = data;
    }
}
