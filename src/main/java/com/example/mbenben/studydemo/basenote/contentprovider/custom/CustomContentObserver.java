package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.utils.log.LogUtils;

/**
 * Created by MDove on 2018/4/12.
 */

public class CustomContentObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public CustomContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Toast.makeText(App.getInstance().getContext(), "我再另一个进程监听到内容提供者数据发生变化", Toast.LENGTH_SHORT).show();
    }
}
