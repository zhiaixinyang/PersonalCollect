package com.example.mbenben.studydemo.basenote.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by MDove on 2018/3/10.
 */

public class RxJava2Activity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";
    private static final String TAG = "RxJava2Activity";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RxJava2Activity.class);
        intent.putExtra(EXTRA_ACTION, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(EXTRA_ACTION));
        setContentView(R.layout.activity_rxjava_2);

        normal();
    }

    private void normal() {
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                // onComplete和onError必须唯一并且互斥, 即不能发多个onComplete,
                // 也不能发多个onError, 也不能先发一个onComplete, 然后再发一个onError, 反之亦然
                emitter.onComplete();//emitter.onError()
                //onComplete(),onError()之后的事件会发送，但不会再接收
                emitter.onNext(4);
                emitter.onNext(5);
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                LogUtils.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                LogUtils.d(TAG, "complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }
}
