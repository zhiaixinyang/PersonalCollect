package com.example.mbenben.studydemo.net.retrofit.view;

import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;

import java.util.List;

/**
 * Created by MBENBEN on 2017/1/5.
 */

public interface RetrofitView {
    void initRetrofitDatas(List<RetrofitBean> datas);
    void initError(String error);
    void postSuccess(String success);
    void postError(String error);
    void refreshLoading();
    void refreshSuccess();
    void postLoading();
}
