package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.base.CommonAdapter;
import com.example.mbenben.studydemo.base.ViewHolder;
import com.example.mbenben.studydemo.basenote.contentprovider.ContentProviderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/4/11.
 */

public class CustomProviderActivity extends BaseActivity {
    private TextView mBtn;
    private RecyclerView mRlv;
    private List<ContentProviderInfoModel> mData;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, CustomProviderActivity.class);
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
        setContentView(R.layout.activity_custom_provider);
        mBtn = (TextView) findViewById(R.id.btn_load);
        mRlv = (RecyclerView) findViewById(R.id.rlv);

        mData = new ArrayList<>();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(CustomContentProvider.URI_INFO_DIR);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor == null) {
                    return;
                }
                while (cursor.moveToNext()) {
                    String content = cursor.getString(cursor.getColumnIndex(CustomContentProvider.TABLE_INFO_CONTENT));
                    String name = cursor.getString(cursor.getColumnIndex(CustomContentProvider.TABLE_INFO_NAME));
                    ContentProviderInfoModel model = new ContentProviderInfoModel(content, name);
                    mData.add(model);
                }
            }
        });

        mRlv.setAdapter(new CommonAdapter<ContentProviderInfoModel>(this, R.layout.item_custom_provider_info, mData) {
            @Override
            public void convert(ViewHolder holder, ContentProviderInfoModel model) {
                holder.setText(R.id.tv_content, model.mContent);
                holder.setText(R.id.tv_name, model.mName);
            }
        });
    }
}
