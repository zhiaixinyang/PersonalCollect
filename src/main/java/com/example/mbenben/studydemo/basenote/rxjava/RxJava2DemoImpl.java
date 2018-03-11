package com.example.mbenben.studydemo.basenote.rxjava;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import retrofit2.http.Field;

/**
 * Created by MDove on 2018/3/11.
 */

public class RxJava2DemoImpl implements RxJava2DemoApi {
    @Override
    public Observable<String> loginIn() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("登录成功，我就是个假数据");
            }
        });
    }

    @Override
    public Observable<String> getUserName() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("获取当前登录账号信息：Hello RxJava2");
            }
        });
    }
}
