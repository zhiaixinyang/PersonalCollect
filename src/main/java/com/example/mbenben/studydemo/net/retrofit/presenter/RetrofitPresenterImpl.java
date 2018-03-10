package com.example.mbenben.studydemo.net.retrofit.presenter;

import com.example.mbenben.studydemo.net.retrofit.RetrofitHelper;
import com.example.mbenben.studydemo.net.retrofit.RxUtil;
import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;
import com.example.mbenben.studydemo.net.retrofit.view.RetrofitView;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by MDove on 2017/1/5.
 */

public class RetrofitPresenterImpl implements RetrofitPresenter {
    private RetrofitView retrofitView;
    private RetrofitHelper retrofitHelper;

    public RetrofitPresenterImpl(RetrofitView retrofitView) {
        this.retrofitView = retrofitView;
        retrofitHelper = new RetrofitHelper();
    }

    @Override
    public void getRetrofitDatas() {
        retrofitView.refreshLoading();

        RxUtil.wrapper(retrofitHelper.getDetailInfo())
                .subscribe(new Consumer<List<RetrofitBean>>() {
                    @Override
                    public void accept(List<RetrofitBean> retrofitBeen) throws Exception {
                        retrofitView.initRetrofitDatas(retrofitBeen);
                        retrofitView.refreshSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        retrofitView.initError(throwable.getMessage());
                        retrofitView.refreshSuccess();
                    }
                });
    }

    @Override
    public void postRetrofitDatas(RetrofitBean retrofitBean, File file) {
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData
                ("photo", "icon.png", photoRequestBody);
        RxUtil.wrapper(retrofitHelper.postData(
                RequestBody.create(null, retrofitBean.getName()),
                RequestBody.create(null, retrofitBean.getContent()), photo))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        retrofitView.postSuccess(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        retrofitView.postError(throwable.getMessage());
                    }
                });
    }
}
