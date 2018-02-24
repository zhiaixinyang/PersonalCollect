package com.example.mbenben.studydemo.view.bezier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MDove on 2017/1/12.
 * <p>
 * <p>
 * https://github.com/AlanCheen/PeriscopeLayout
 */

public class GiftBezierActivity extends BaseActivity {
    @BindView(R.id.view_gift)
    GiftBezierView gift;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, GiftBezierActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_beziergift);
        ButterKnife.bind(this);
        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gift.addHeart();
            }
        });
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }
}
