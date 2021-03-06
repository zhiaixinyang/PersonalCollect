package com.example.mbenben.studydemo.layout.recyclerview.banner.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.base.BaseActivity;
import com.example.mbenben.studydemo.layout.recyclerview.banner.view.RecyclerViewBannerBase;
import com.example.mbenben.studydemo.layout.recyclerview.banner.view.RecyclerViewBannerMz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 18/4/8.
 */

public class RlvBannerRlvActivity extends BaseActivity {
    private RecyclerViewBannerMz banner, banner2;

    private static final String ACTION_EXTRA = "action_extra";

    public static void start(Context context, String title) {
        Intent intent = new Intent(context, RlvBannerRlvActivity.class);
        intent.putExtra(ACTION_EXTRA, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(ACTION_EXTRA));
        setContentView(R.layout.activity_rlv_banner);
        banner = (RecyclerViewBannerMz) findViewById(R.id.banner);
        banner2 = (RecyclerViewBannerMz) findViewById(R.id.banner2);
        List<String> list = new ArrayList<>();
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/69427561.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/23738150.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/30127126.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/36125492.jpg");
        banner.initBannerImageView(list, new RecyclerViewBannerBase.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(RlvBannerRlvActivity.this, "clicked:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        banner2.initBannerImageView(list, new RecyclerViewBannerBase.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(RlvBannerRlvActivity.this, "clicked:" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }


    public void jump(View view) {
        RlvBannerNormalActivity.start(this, "基于RecyclerView的Banner");
    }
}
