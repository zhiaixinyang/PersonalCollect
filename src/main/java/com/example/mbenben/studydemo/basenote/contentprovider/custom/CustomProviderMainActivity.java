package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/4/12.
 */

public class CustomProviderMainActivity extends BaseActivity {
    private static final String ACTION_EXTRA = "action_extra";
    private CustomContentObserver observer;

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, CustomProviderMainActivity.class);
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
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_custom_provider_main);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(CustomContentProvider.INFO_ADD_CONTENT_KEY, "AAA");
                values.put(CustomContentProvider.INFO_ADD_NAME_KEY, "aaa");
                Uri uri = Uri.parse(CustomContentProvider.URI_INFO_ITEM);
                getContentResolver().insert(uri, values);
            }
        });
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(CustomContentProvider.URI_INFO_DIR + "/0");
                ContentValues values = new ContentValues();
                values.put(CustomContentProvider.INFO_ADD_CONTENT_KEY, "BBB");
                values.put(CustomContentProvider.INFO_ADD_NAME_KEY, "bbb");
                getContentResolver().update(uri, values, null, null);
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(CustomContentProvider.URI_INFO_DIR + "/1");
                getContentResolver().delete(uri, null, null);
            }
        });

        observer = new CustomContentObserver(new Handler());
        getContentResolver().registerContentObserver(Uri.parse(CustomContentProvider.URI_INFO_DIR), true, observer);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}
