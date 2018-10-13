package com.example.mbenben.studydemo.basenote.ballwidget.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import android.view.WindowManager;

import com.example.mbenben.studydemo.basenote.aidltest.IBallBinder;
import com.example.mbenben.studydemo.basenote.aidltest.IBallCallBack;
import com.example.mbenben.studydemo.basenote.ballwidget.widget.BallWidgetHelper;
import com.example.mbenben.studydemo.basenote.ballwidget.widget.FloatWeatherWidget;
import com.example.mbenben.studydemo.basenote.ballwidget.widget.WidgetBall;
import com.example.mbenben.studydemo.basenote.ballwidget.widget.config.WidgetBallSp;
import com.example.mbenben.studydemo.utils.NavigationBarUtils;
import com.example.mbenben.studydemo.utils.StatusBarUtils;

/**
 * @author MDove on 2018/4/19.
 */
public class BallWidgetService extends Service {
    public static final String ACTION_SHOW_BALL = "action.SHOW_BALL";
    public static final String ACTION_HIDE_BALL = "action.HIDE_BALL";

    public static final String TAG = "BallWidgetService";

    private WidgetBall mWeatherBall;
    private FloatWeatherWidget mFloatWidget;
    private WindowManager mWindowManager;
    private Point mScreenSize;
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    private IBinder mBallBinder;

    private final RemoteCallbackList<IBallCallBack> mCallbacks = new RemoteCallbackList<IBallCallBack>() {
        @Override
        public void onCallbackDied(IBallCallBack callback, Object cookie) {
            super.onCallbackDied(callback, cookie);
        }
    };

    public static void showWeatherBall(@NonNull Context context) {
        Intent service = new Intent(context, BallWidgetService.class);
        service.setAction(ACTION_SHOW_BALL);
        try {
            context.startService(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideWeatherBall(@NonNull Context context) {
        Intent service = new Intent(context, BallWidgetService.class);
        service.setAction(ACTION_HIDE_BALL);
        context.startService(service);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBallBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBallBinder = new BallBinder();
        mOrientation = getResources().getConfiguration().orientation;

        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        this.mScreenSize = new Point();
        this.mWindowManager.getDefaultDisplay().getSize(mScreenSize);
        this.mScreenSize.y -= StatusBarUtils.getStatusBarHeight(this);

        mWeatherBall = new WidgetBall(this);
        mFloatWidget = new FloatWeatherWidget(this, mWeatherBall, null);
        mFloatWidget.setOnWidgetStateChangedListener(mOnWidgetStateChangedListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mOrientation != newConfig.orientation) {
            if (mFloatWidget.isShown()) {
                mFloatWidget.onConfigurationChanged(mOrientation, newConfig.orientation);
            }
            mOrientation = newConfig.orientation;
        }
    }

    private FloatWeatherWidget.OnWidgetStateChangedListener mOnWidgetStateChangedListener = new FloatWeatherWidget.OnWidgetStateChangedListener() {
        @Override
        public void onWidgetStateChanged(@NonNull FloatWeatherWidget.State state) {
            if (state == FloatWeatherWidget.State.REMOVED) {
            }
        }

        @Override
        public void onWidgetPositionChanged(int x, int y) {
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                WidgetBallSp.saveWeatherWidgetPosition(x, y);
            } else if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                WidgetBallSp.saveWeatherWidgetPosition4Landscape(x, y);
            }
        }

        @Override
        public void onWidgetClicked() {
            notifyBallClicked();
        }
    };

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            handleCommandIntent(intent);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFloatWidget.hide();
        mCallbacks.kill();
        if (WidgetBallSp.isBallEnable()) {
            startService(new Intent(this, BallWidgetService.class));
        }
    }

    private void handleCommandIntent(Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            if (BallWidgetHelper.checkOverlaysPermission(this) && WidgetBallSp.isBallEnable()) {
                showBall();
            }
            return;
        }
        switch (action) {
            case ACTION_SHOW_BALL: {
                if (BallWidgetHelper.checkOverlaysPermission(this) && WidgetBallSp.isBallEnable()) {
                    showBall();
                }
                break;
            }
            case ACTION_HIDE_BALL: {
                if (BallWidgetHelper.checkOverlaysPermission(this)) {
                    hideBallInternal(false);
                }
                break;
            }
        }
    }


    private void notifyBallClicked() {
        int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IBallCallBack cb = mCallbacks.getBroadcastItem(i);
            try {
                cb.onBallClicked();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbacks.finishBroadcast();
    }

    private void setFloatWidgetShowFixPosition() {
        if (BallWidgetHelper.checkOverlaysPermission(this)) {
            int positionX = WidgetBallSp.getPositionX();
            int positionY = WidgetBallSp.getPositionY();
            if (positionY >= mScreenSize.y - NavigationBarUtils.getNavigationBarHeight(this) - 100) {
                positionY = -1;
            }
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                positionX = WidgetBallSp.getLandscapePositionX();
                positionY = WidgetBallSp.getLandscapePositionY();
                if (positionY >= mScreenSize.y - NavigationBarUtils.getNavigationBarHeight(this) - 100) {
                    positionY = -1;
                }
            }
            mFloatWidget.show(positionX, positionY);
        }
    }

    private void hideBallInternal(boolean disable) {
        mFloatWidget.hide();
        if (disable) {
            WidgetBallSp.setBallEnable(false);
        }
    }

    @UiThread
    public void showBall() {
        mWeatherBall.showWeather();
        setFloatWidgetShowFixPosition();
    }

    @UiThread
    public void closeBall(boolean tmp) throws RemoteException {
        hideBallInternal(!tmp);
    }

    private class BallBinder extends IBallBinder.Stub {

        @Override
        public void registerCallback(String pkg, IBallCallBack cb) {
            if (cb != null) {
                mCallbacks.register(cb, pkg);
            }
        }

        @Override
        public void unregisterCallback(IBallCallBack cb) {
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
