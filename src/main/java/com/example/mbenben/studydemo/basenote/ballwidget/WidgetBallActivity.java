package com.example.mbenben.studydemo.basenote.ballwidget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.basenote.aidltest.IBallBinder;
import com.example.mbenben.studydemo.basenote.aidltest.IBallCallBack;
import com.example.mbenben.studydemo.basenote.ballwidget.service.BallWidgetService;
import com.example.mbenben.studydemo.basenote.ballwidget.widget.BallWidgetHelper;
import com.example.mbenben.studydemo.utils.ToastHelper;
import com.example.mbenben.studydemo.utils.permission.PermissionUtils;
import com.example.mbenben.studydemo.view.jbox.JBoxDemoActivity;

/**
 * Created by MDove on 2018/4/19.
 */

public class WidgetBallActivity extends BaseActivity {
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 100;
    private static final String ACTION_EXTRA = "action_extra";
    private String mTitle;
    private ServiceConnection mConn;
    private IBallBinder mBallBinder;

    private IBallCallBack mBallCallBack = new IBallCallBack.Stub() {
        @Override
        public void onBallClicked() {
            ToastHelper.debugToast("悬浮球被被点击");
        }
    };

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, WidgetBallActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = TextUtils.isEmpty(mTitle) ? getIntent().getStringExtra(ACTION_EXTRA) : mTitle;
        setTitle(mTitle);
        setContentView(R.layout.activity_widget_ball);
        bindService();
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBall();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case OVERLAY_PERMISSION_REQUEST_CODE: {
                checkOverlaysPermission(true);
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConn != null) {
            unbindService(mConn);
        }
    }

    private void bindService() {
        Intent binder = new Intent(this, BallWidgetService.class);
        bindService(binder, mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBallBinder = IBallBinder.Stub.asInterface(service);
                try {
                    mBallBinder.registerCallback(getPackageName(), mBallCallBack);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                try {
                    if (mBallBinder != null) {
                        mBallBinder.unregisterCallback(mBallCallBack);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    mBallBinder = null;
                }
            }
        }, Context.BIND_IMPORTANT);
    }

    private void initBall() {
        boolean overlaysPermission = BallWidgetHelper.checkOverlaysPermission(this);
        if (overlaysPermission) {
            BallWidgetService.showWeatherBall(this);
        } else {
            checkOverlaysPermission(false);
        }
    }

    private void checkOverlaysPermission(boolean result) {
        boolean overlaysPermission = BallWidgetHelper.checkOverlaysPermission(this);
        if (!overlaysPermission && !result) {
            PermissionUtils.openOverlayPermissionActivity(this, getPackageName(),
                    OVERLAY_PERMISSION_REQUEST_CODE);
        } else if (!overlaysPermission) {
            ToastHelper.shortToast("未授予悬浮权限");
        } else if (result) {
            BallWidgetService.showWeatherBall(this);
        }
    }
}
