package com.example.mbenben.studydemo.basenote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MBENBEN on 2017/2/23.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME="download.db";
    private static int DB_VERSION=1;
    private String SQL_CREATE="create table thread_table(_id integer primary key autoincrement," +
            "thread_id integer,url text,start_length integer ,stop_length integer,finish_length integer)";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
