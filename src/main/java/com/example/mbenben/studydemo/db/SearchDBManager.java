package com.example.mbenben.studydemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2017/3/28.
 */

public class SearchDBManager {
    private SearchDBHepler searchDBHepler;
    private SQLiteDatabase database;

    public SearchDBManager(){
        searchDBHepler=new SearchDBHepler();

    }

    public void insert(SearchBean searchBean){
        database = searchDBHepler.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("activity",searchBean.getActivity());
        values.put("title",searchBean.getTitle());
        long insertLine = database.insert("search", null, values);
        if (insertLine>0){
            Log.d("aaaa","当前数据行数："+insertLine);
        }
        database.close();
    }

    public List<SearchBean> query(String queryStr){
        List<SearchBean> data=new ArrayList<>();
        database = searchDBHepler.getWritableDatabase();
        String[] selectioinArgs = {"%"+queryStr+"%"};//注意：这里没有单引号
        String sql = "select * from search where title like ? ";
        Cursor cursor = database.rawQuery(sql,selectioinArgs);
            if(cursor.moveToFirst()) {
                if (cursor.moveToNext()) {
                    String activity = cursor.getString(cursor.getColumnIndex("activity"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));

                    SearchBean searchBean = new SearchBean();
                    searchBean.setTitle(title);
                    searchBean.setActivity(activity);
                    data.add(searchBean);
                    cursor.moveToNext();
                }
            }
        cursor.close();
        return data;
    }
}
