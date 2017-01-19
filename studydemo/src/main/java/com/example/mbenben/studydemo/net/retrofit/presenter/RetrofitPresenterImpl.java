package com.example.mbenben.studydemo.net.retrofit.presenter;

import com.example.mbenben.studydemo.net.retrofit.RetrofitHelper;
import com.example.mbenben.studydemo.net.retrofit.RxUtil;
import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;
import com.example.mbenben.studydemo.net.retrofit.view.RetrofitView;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by MBENBEN on 2017/1/5.
 */

public class RetrofitPresenterImpl implements RetrofitPresenter{
    private RetrofitView retrofitView;
    private RetrofitHelper retrofitHelper;
    private CompositeSubscription compositeSubscription;

    public RetrofitPresenterImpl(RetrofitView retrofitView) {
        this.retrofitView = retrofitView;
        retrofitHelper =new RetrofitHelper();
        compositeSubscription=new CompositeSubscription();
    }

    @Override
    public void getRetrofitDatas() {
        retrofitView.refreshLoading();

        Subscription rxSubscription = retrofitHelper.getDetailInfo()
                .compose(RxUtil.<List<RetrofitBean>>rxSchedulerHelper())
                .subscribe(new Action1<List<RetrofitBean>>() {
                    @Override
                    public void call(List<RetrofitBean> retrofitBeen) {
                        retrofitView.initRetrofitDatas(retrofitBeen);
                        retrofitView.refreshSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        retrofitView.initError(throwable.getMessage());
                        retrofitView.refreshSuccess();
                    }
                });
        compositeSubscription.add(rxSubscription);
    }

    @Override
    public void postRetrofitDatas(RetrofitBean retrofitBean, File file) {
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData
                ("photo", "icon.png", photoRequestBody);
        Subscription subscription=retrofitHelper.postData(
                RequestBody.create(null,retrofitBean.getName()),
                RequestBody.create(null,retrofitBean.getContent()), photo)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        retrofitView.postSuccess(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        retrofitView.postError(throwable.getMessage());
                    }
                });
        compositeSubscription.add(subscription);
    }
}
