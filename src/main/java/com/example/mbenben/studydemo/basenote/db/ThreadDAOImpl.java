package com.example.mbenben.studydemo.basenote.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mbenben.studydemo.basenote.service.download.ThreadBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class ThreadDAOImpl implements ThreadDAO {
    private DBHelper dbHelper;

    public ThreadDAOImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadBean threadBean) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.execSQL("insert table thread_table(thread_id,url,start_length,stop_length," +
                        "finish_length) value (?,?,?,?,?)",
                new Object[]{threadBean.getId(), threadBean.getUrl(), threadBean.getStartLength(),
                        threadBean.getEndLength(), threadBean.getFinshedLength()});
        writableDatabase.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.execSQL("delete from thread_table where url = ? and thread_id = ?",
                new Object[]{url,thread_id});
        writableDatabase.close();
    }

    @Override
    public void updataThread(String url, int thread_id, int finished_length) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        writableDatabase.execSQL("update thread_table set finish_length =? where url = ? and thread_id = ?",
                new Object[]{finished_length,url,thread_id});
        writableDatabase.close();
    }

    @Override
    public List<ThreadBean> getThreadList(String url) {
        List<ThreadBean> list=new ArrayList<>();
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from thread_table where url =?",
                                                        new String[]{url});
        while (cursor.moveToNext()){
            ThreadBean threadBean=new ThreadBean();
            threadBean.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadBean.setStartLength(cursor.getInt(cursor.getColumnIndex("start_length")));
            threadBean.setEndLength(cursor.getInt(cursor.getColumnIndex("stop_length")));
            threadBean.setFinshedLength(cursor.getInt(cursor.getColumnIndex("finish_length")));
            list.add(threadBean);
        }
        cursor.close();
        writableDatabase.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from thread_table where url= ? and thread_id =?",
                new String[]{url, thread_id + ""});
        boolean isExists=cursor.moveToNext();
        cursor.close();
        writableDatabase.close();
        return isExists;
    }
}
