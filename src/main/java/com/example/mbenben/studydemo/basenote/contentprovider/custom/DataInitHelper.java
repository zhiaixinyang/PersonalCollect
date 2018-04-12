package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.greendao.ContentProviderInfoDao;
import com.example.mbenben.studydemo.greendao.entity.ContentProviderInfo;

import java.util.List;

/**
 * Created by MDove on 2018/4/11.
 */

public class DataInitHelper {
    public static void initData() {
        ContentProviderInfoDao dao = App.getDaoSession().getContentProviderInfoDao();
        List<ContentProviderInfo> mData = dao.queryBuilder().list();
        if (mData == null || mData.size() <= 0) {
            for (int i = 0; i < 30; i++) {
                ContentProviderInfo contentProviderInfo = new ContentProviderInfo();
                contentProviderInfo.setId(Long.valueOf(i));
                contentProviderInfo.setMContent("我是一条数据：" + i);
                contentProviderInfo.setMName("我叫：" + i);
                dao.insert(contentProviderInfo);
            }
        }
    }
}
