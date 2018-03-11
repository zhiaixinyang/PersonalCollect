package com.example.mbenben.studydemo.basenote.rxjava;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by MDove on 2018/3/11.
 * <p>
 * ！！这只是一个test接口类，并没有正真的作用只是为了演示。省略生成Retrofit的过程
 * <p>
 * {@line com.example.mbenben.studydemo.basenote.rxjava.RxJava2Activity#mapAndflatMap()}
 */

public interface RxJava2DemoApi {

    @GET
    Observable<String> loginIn();

    @GET
    Observable<String> getUserName();
}
