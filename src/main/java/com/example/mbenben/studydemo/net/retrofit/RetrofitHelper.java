package com.example.mbenben.studydemo.net.retrofit;

import android.os.Environment;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.net.retrofit.model.RetrofitApi;
import com.example.mbenben.studydemo.net.retrofit.model.bean.RetrofitBean;
import com.example.mbenben.studydemo.utils.NetUtil;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by MDove on 2017/1/5.
 */

public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private static RetrofitApi retrofitApi;

    public RetrofitHelper(){
        init();
    }


    private void init(){
        initOkHttp();
        retrofitApi= getRetrofitApiServiceByGson();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "data";
        File cacheFile = new File(filePath+"/RetrofitCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkAvailable(App.getInstance().getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtil.isNetworkAvailable(App.getInstance().getContext())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static RetrofitApi getRetrofitApiServiceByGson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RetrofitApi.class);
    }
    private static RetrofitApi getRetrofitApiByString(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitApi.URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(RetrofitApi.class);
    }

    public Observable<List<RetrofitBean>> getDetailInfo() {
        return retrofitApi.getRetrofitDatas();
    }
    public Observable<String> postData(RequestBody name, RequestBody content,
                                 MultipartBody.Part img){
        return getRetrofitApiByString().postImgAndString(name,content,img);
    }
}
