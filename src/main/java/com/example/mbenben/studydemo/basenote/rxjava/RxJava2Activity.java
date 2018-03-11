package com.example.mbenben.studydemo.basenote.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.utils.ToastHelper;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MDove on 2018/3/10.
 */

public class RxJava2Activity extends BaseActivity {
    private static final String EXTRA_ACTION = "extra_action";
    private static final String TAG = "RxJava2Activity";
    //用于管理Disposable
    private CompositeDisposable mCompositeDisposable;

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
        mCompositeDisposable = new CompositeDisposable();

//        normal();
        mapAndflatMap();
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
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                //交给管理工具，统一处理
                mCompositeDisposable.add(d);
                disposable = d;
            }

            @Override
            public void onNext(Integer value) {
                if (value == 3) {
                    //断开连接，此后观察者将收不到数据
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                //处理Error情况
            }

            @Override
            public void onComplete() {
                //数据发生完毕
            }
        };
        //建立连接
        observable
                .subscribeOn(Schedulers.newThread())//被观察者在新线程发射数据（只会在第一次调用有效 ）
                .observeOn(AndroidSchedulers.mainThread())//观察者在主线程响应（每次调用都会切换）
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //在主线程，并且在观察者响应之前执行
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        //在io线程，并且在观察者响应之前执行
                    }
                })
                .subscribe(observer);
    }

    private void mapAndflatMap() {
        //Map，就是简单的类型处理，转化
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                //此时将被观察者发送的数据，进行转化，然后发送出去。
                return "我是转换后的1";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            }
        });

        // flatMap：在暴露的方法中会生成一个新的Observable对象，那么我们就可以重新对这个对象进行操作。
        // 比如：指定生成新Observable执行的全新线程；我们在Retrofit使用时，有可能一个接口的请求需要
        // 跟在前一个接口后边，那这个时候我们就可以这么操作。
        // 但是flatMap不保证观察者接受顺序，如果需要保证顺序可以使用Observable.concatMap()
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                LogUtils.d(TAG, "flatMap 生成Observable对象时 当前线程:" + Thread.currentThread().getName());
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        //进行大量耗时操作
                        final String string = integer.toString();
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                                emitter.onNext(string);
                                LogUtils.d(TAG, "flatMap apply方法执行 当前线程:" + Thread.currentThread().getName());
                            }
                        }).subscribeOn(Schedulers.io());//此转换操作转换成io线程执行
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            }
        });

        // ---#flatMap在Retrofit中的应用1#---
        final RxJava2DemoImpl impl = new RxJava2DemoImpl();//这只是个模拟，并非真的接口
        //模拟一个普通的登录操作
        impl.loginIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastHelper.debugToast(s);
                        //模拟登录成功，此时发起请求当前用户账号名操作
                    }
                }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return impl.getUserName();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ToastHelper.debugToast(s);
                        //模拟获取当前账号用户名成功的操作
                    }
                });
    }

    private void zip() {
        //注意这里的被观察者在不同线程创建，这样在俩个被观察者的第一个数据合并并使用后，才会发送后边数据
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("B");
                emitter.onNext("C");

                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String value) {
                //效果是观察者1和观察者2的第1个，第2个...一次合并发送并接收
                //而且这里只会合并3个，也就是说合并时以发送最少数据的被观察者为主
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 运行此方法可以造成OOM（MissingBackpressureException）
     */
    private void backPressure() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {    //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())//因为被观察者在子线程，因此它不会注意观察者是否消费事件，它只负责发送
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LogUtils.d(TAG, integer + "");
                    }
                });
        // 因为是异步的过程，所以被观察者只负责一股脑发送数据，而因为是异步所以这个数据被存起来，然后在发送给观察者的线程
        // 当存数据的容器满了，就会出现异常。
        // 解决方法也很多，比如：降低数据的发送数据（sleep）；降低数据的发送量
        // ---#官方的解决方案#---
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)//设置被压策略：直接抛异常
                // BackpressureStrategy.BUFFER)使用无限大的缓存，注意：如果此时观察者依旧不处理数据。仍会OOM
                // BackpressureStrategy.DROP)扔掉存不下的数据
                // BackpressureStrategy.LATEST)保留新的数据
                .subscribeOn(Schedulers.io())//同步线程中，如果观察者不调用request就会直接异常
                .observeOn(AndroidSchedulers.mainThread())//非同步线程，不调用request，被观察者有缓存的存在可以发送数据。但是观察者不会处理
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        //Flowable使用的是响应是拉取的思想，因此我们需要在这里调用request()，告诉被观察者，我需要多少数据
                        //这样就可以就可以从根本解决数据发送过快的问题。
                        //因为这里是观察者消耗完数据后，主动去告诉被观察者去发数据，而不是被观察者一股脑的发。
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscription.request(10);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        Flowable.interval(1, TimeUnit.MICROSECONDS)
                .onBackpressureDrop()  //加上背压策略
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
