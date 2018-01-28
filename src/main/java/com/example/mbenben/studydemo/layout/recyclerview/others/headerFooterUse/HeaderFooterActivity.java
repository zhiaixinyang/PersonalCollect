package com.example.mbenben.studydemo.layout.recyclerview.others.headerFooterUse;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.BannerAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.OnItemClickListener;
import com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse.CategoryItemDecoration;
import com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse.CategoryListAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse.ChannelListResult;
import com.example.mbenben.studydemo.layout.recyclerview.others.widget.BannerView;
import com.example.mbenben.studydemo.layout.recyclerview.others.widget.WrapRecyclerView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Darren on 2016/12/29.
 * Email: 240336124@qq.com
 * Description:  头部和底部添加
 *
 * 原作者项目GitHub:https://github.com/Shenmowen/RecyclerAnalysis
 */
public class HeaderFooterActivity extends AppCompatActivity implements OnItemClickListener {

    private WrapRecyclerView mRecyclerView;
    private OkHttpClient mOkHttpClient;
    private static Handler mHandler = new Handler();

    List<ChannelListResult.DataBean.CategoriesBean.CategoryListBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_recycler);
        mRecyclerView = (WrapRecyclerView) findViewById(R.id.recycler_view);
        // 设置显示分割 ListView样式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 添加分割线
        mRecyclerView.addItemDecoration(new CategoryItemDecoration(ContextCompat.getDrawable(this, R.drawable.category_list_divider)));
        mOkHttpClient = new OkHttpClient();
        requestListData();
    }

    /**
     * 请求列表数据
     */
    private void requestListData() {

        // 利用Okhttp去获取网络数据
        Request.Builder builder = new Request.Builder();
        builder.url("http://is.snssdk.com/2/essay/discovery/v3/?iid=6152551759&channel=360&aid=7" +
                "&app_name=joke_essay&version_name=5.7.0&ac=wifi&device_id=30036118478&device_brand=Xiaomi&update_version_code=5701&" +
                "manifest_version_code=570&longitude=113.000366&latitude=28.171377&device_platform=android");

        mOkHttpClient.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                // Gson 解析成对象
                final ChannelListResult channelList = new Gson().fromJson(result, ChannelListResult.class);
                // 获取列表数据
                final List<ChannelListResult.DataBean.CategoriesBean.CategoryListBean> categoryList =
                        channelList.getData().getCategories().getCategory_list();
                // 该方法不是在主线程中
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showListData(categoryList);
                        addBannerView(channelList.getData().getRotate_banner().getBanners());
                    }
                });
            }
        });
    }

    /**
     * 添加Banner轮播图
     */
    private void addBannerView(final List<ChannelListResult.DataBean.BannerBean> banners) {
        BannerView bannerView = (BannerView) LayoutInflater.from(this)
                .inflate(R.layout.layout_banner_view_others_recyclerview, mRecyclerView, false);

        bannerView.setAdapter(new BannerAdapter() {
            @Override
            public View getView(int position, View convertView) {
                if (convertView == null) {
                    convertView = new ImageView(HeaderFooterActivity.this);
                }
                ((ImageView) convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);

                Glide.with(HeaderFooterActivity.this).load(banners.get(position).banner_url.url_list.get(0).url).into((ImageView) convertView);
                return convertView;
            }

            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public String getBannerDesc(int position) {
                return banners.get(position).banner_url.title;
            }
        });

        mRecyclerView.addHeaderView(bannerView);
    }

    /**
     * 显示列表数据
     *
     * @param categoryList
     */
    private void showListData(List<ChannelListResult.DataBean.CategoriesBean.CategoryListBean> categoryList) {
        mData = categoryList;

        final CategoryListAdapter listAdapter = new CategoryListAdapter(this, categoryList);
        listAdapter.setOnItemClickListener(this);

        // 添加头部和底部 需要 包裹Adapter，才能添加头部和底部
        /*WrapRecyclerAdapter wrapRecyclerAdapter = new WrapRecyclerAdapter(listAdapter);
        mRecyclerView.setAdapter(wrapRecyclerAdapter);

        // 添加头部和底部
        wrapRecyclerAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_header_footer,mRecyclerView,false));
        wrapRecyclerAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_header_footer,mRecyclerView,false));
        wrapRecyclerAdapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.layout_header_footer,mRecyclerView,false));
        */

        mRecyclerView.setAdapter(listAdapter);
        // 只能写在setAdapter之后，否则没效果，也可以选择抛异常
        /*mRecyclerView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_header_footer,mRecyclerView,false));
        mRecyclerView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_header_footer,mRecyclerView,false));
        mRecyclerView.addFooterView(LayoutInflater.from(this).inflate(R.layout.layout_header_footer,mRecyclerView,false));*/
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "" + mData.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
