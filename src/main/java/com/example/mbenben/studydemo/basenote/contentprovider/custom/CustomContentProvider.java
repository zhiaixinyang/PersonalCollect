package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.greendao.ContentProviderInfoDao;
import com.example.mbenben.studydemo.greendao.DaoSession;
import com.example.mbenben.studydemo.greendao.utils.DaoManager;

/**
 * Created by MDove on 2018/4/11.
 */

public class CustomContentProvider extends ContentProvider {
    public static final int TABLE_DIR = 0;
    public static final int TABLE_ITEM = 1;
    public static final String TABLE_NAME = "CONTENT_PROVIDER_INFO";
    public static final String URI_INFO_DIR = "content://com.example.mbenben.studydemo.provider/CONTENT_PROVIDER_INFO";
    public static final String TABLE_INFO_CONTENT = "M_CONTENT";
    public static final String TABLE_INFO_NAME = "M_NAME";

    private static UriMatcher sUriMatcher;
    private ContentProviderInfoDao mDao;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI("com.example.mbenben.studydemo.provider", TABLE_NAME, TABLE_DIR);
        sUriMatcher.addURI("com.example.mbenben.studydemo.provider", TABLE_NAME + "/#", TABLE_ITEM);
    }

    @Override
    public boolean onCreate() {
        if (App.getDaoSession() == null) {
            DaoManager daoManager = DaoManager.getInstance();
            daoManager.init(getContext());
            App.mDaoManager = daoManager;
            DaoSession daoSession = daoManager.getDaoMaster().newSession();
            App.mDaoSession = daoSession;
            mDao = daoSession.getContentProviderInfoDao();
        } else {
            mDao = App.getDaoSession().getContentProviderInfoDao();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TABLE_DIR: {
                cursor = mDao.queryBuilder().buildCursor().query();
                break;
            }
            case TABLE_ITEM: {
                String content = uri.getPathSegments().get(1);
                cursor = mDao.queryBuilder().where(ContentProviderInfoDao.Properties.MContent.eq(content)).buildCursor().query();
                break;
            }
            default: {
                break;
            }
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TABLE_DIR: {
                return "vnd.android.cursor.dir/vnd.com.example.mbenben.studydemo.provider." + TABLE_NAME;
            }
            case TABLE_ITEM: {
                return "vnd.android.cursor.item/vnd.com.example.mbenben.studydemo.provider." + TABLE_NAME;
            }
            default: {
                return null;
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
