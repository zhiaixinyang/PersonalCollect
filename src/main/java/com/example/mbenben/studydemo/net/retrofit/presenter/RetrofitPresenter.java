package com.example.mbenben.studydemo.net.retrofit.presenter;

import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;

import java.io.File;

/**
 * Created by MDove on 2017/1/5.
 */

public interface RetrofitPresenter {
    void getRetrofitDatas();
    void postRetrofitDatas(RetrofitBean retrofitBean, File file);
}
