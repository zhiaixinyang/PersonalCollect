package com.example.mbenben.studydemo.net.retrofit;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MDove on 2017/1/5.
 */
public class RxUtil {

    public static <T> Observable<T> wrapper(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
