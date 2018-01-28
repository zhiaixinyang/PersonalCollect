package com.example.mbenben.studydemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.basenote.db.DBHelper;

/**
 * Created by MBENBEN on 2017/3/28.
 */

public class SearchDBHepler extends SQLiteOpenHelper {
    public SearchDBHepler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SearchDBHepler(){
        super(App.getInstance().getContext(),"search.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="create table search (activity text primary key not null,title text); ";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
