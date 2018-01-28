package com.example.mbenben.studydemo.basenote.service.downloadfile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * create by luoxiaoke on 2016/4/29 16:10.
 * use for 数据库操作类
 */
public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start long,end long,finished long)";
    private static final String SQL_DROP = "drop table if exists thread_info";
    private static final int VERSION = 1;


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
