package com.example.mbenben.studydemo.net.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.net.retrofit.RetrofitAdapter;
import com.example.mbenben.studydemo.net.retrofit.model.RetrofitApi;
import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2017/1/9.
 */

public class RxJavaActivity extends AppCompatActivity{
    @BindView(R.id.tv_content1) TextView tvContent1;
    @BindView(R.id.rlv_main) RecyclerView rlvMain;
    @BindView(R.id.iv_image) ImageView ivImage;
    private String imagePath = "http://www.ohonor.xyz/image/TestImage.png";


    private List<RetrofitBean> datas;
    private RetrofitAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acctivity_rxjava);
        ButterKnife.bind(this);
        //如果不设置操作线程问题，默认都处在同一个线程之中
        //rxjavaNetBitmap();
        datas= new ArrayList<>();
        adapter=new RetrofitAdapter(this,datas);
        rlvMain.setLayoutManager(new LinearLayoutManager(this));
        rlvMain.setAdapter(adapter);

    }
    public void rxJavaChange(View view){
        downloadBitmap(imagePath);
    }
    public void recyclerView(View view){
        getRetrofitByRxJava();
    }
    private void downloadBitmap(final String imagePath) {
        Observable.just(imagePath)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        HttpURLConnection conn=null;
                        InputStream is=null;
                        Bitmap bitmap=null;
                        try {
                            URL url=new URL(imagePath);
                            conn= (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.setRequestMethod("GET");
                            if (conn.getResponseCode()==200){
                                is=conn.getInputStream();
                                bitmap=BitmapFactory.decodeStream(is);
                                return bitmap;
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        ivImage.setImageBitmap(bitmap);
                    }
                });

    }

    private void getRetrofitByRxJava() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(RetrofitApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofit.create(RetrofitApi.class).getRetrofitDatasByRxJava()
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<RetrofitBean>>() {

            @Override
            public void call(List<RetrofitBean> list) {
                datas.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void rxjavaNetBitmap() {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                HttpURLConnection conn=null;
                InputStream is=null;
                Bitmap bitmap=null;
                try {
                    URL url=new URL(imagePath);
                    conn= (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode()==200){
                        is=conn.getInputStream();
                        bitmap= BitmapFactory.decodeStream(is);
                        //完成业务逻辑，通知观察者
                        subscriber.onNext(bitmap);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread())//子线程
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Action1<Bitmap>() {
              @Override
              public void call(Bitmap bitmap) {
                  ivImage.setImageBitmap(bitmap);
              }
          });
    }
}
