package com.example.mbenben.studydemo.view.douyinpng;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.view.bubbleView.BubbleViewActivity;

import io.reactivex.ObservableTransformer;

/**
 * Created by MDove on 2018/10/9.
 *
 * 原项目GitHub：https://github.com/meiniepan/Pic2Ascii
 */

public class DouYinPngActivity extends BaseActivity {
    ImageView imageView;
    private Bitmap bitmap;
    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, DouYinPngActivity.class);
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
        setContentView(R.layout.activity_douyin_png);

        imageView = (ImageView) findViewById(R.id.image);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.b4);
    }

    public void doPick(View view) {
        bitmap = DouYinPngUtils.createAsciiPic(BitmapFactory.decodeResource(getResources(), R.mipmap.b4), DouYinPngActivity.this);
        imageView.setImageBitmap(bitmap);
    }

    public void doPick2(View view) {
        bitmap = DouYinPngUtils.createAsciiPicColor(BitmapFactory.decodeResource(getResources(), R.mipmap.b4), DouYinPngActivity.this);
        imageView.setImageBitmap(bitmap);
    }

    public void doSave(View view) {
        DouYinPngUtils.saveBitmap2file(bitmap, DouYinPngActivity.this);
    }
}
