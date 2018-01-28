package com.example.mbenben.studydemo.layout.recyclerview.others.commonAdapterUse;

import android.content.Context;
import android.text.Html;
import android.view.View;

import com.example.mbenben.studydemo.R;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.CommonRecyclerAdapter;
import com.example.mbenben.studydemo.layout.recyclerview.others.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Darren on 2016/12/28.
 * Email: 240336124@qq.com
 * Description: 利用万能通用的Adapter改造后的列表
 */
public class CategoryListAdapter extends CommonRecyclerAdapter<ChannelListResult.DataBean.CategoriesBean.CategoryListBean> {

    public CategoryListAdapter(Context context, List<ChannelListResult.DataBean.
            CategoriesBean.CategoryListBean> datas) {
        super(context, datas, R.layout.item_channel_list_others_recyclerview);
    }

    @Override
    public void convert(ViewHolder holder, ChannelListResult.DataBean.CategoriesBean.CategoryListBean item) {
        // 显示数据
        String str = item.getSubscribe_count() + " 订阅 | " +
                "总帖数 <font color='#FF678D'>" + item.getTotal_updates() + "</font>";
        holder.setText(R.id.channel_text, item.getName())
                .setText(R.id.channel_topic, item.getIntro())
                .setText(R.id.channel_update_info, Html.fromHtml(str));

        // 是否是最新
        if (item.isIs_recommend()) {
            holder.setViewVisibility(R.id.recommend_label, View.VISIBLE);
        } else {
            holder.setViewVisibility(R.id.recommend_label, View.GONE);
        }
        // 加载图片
        holder.setImageByUrl(R.id.channel_icon, new GlideImageLoader(item.getIcon_url()));
    }
}
