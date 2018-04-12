package com.example.mbenben.studydemo.basenote.contentprovider.custom;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.mbenben.studydemo.App;
import com.example.mbenben.studydemo.greendao.ContentProviderInfoDao;
import com.example.mbenben.studydemo.greendao.DaoSession;
import com.example.mbenben.studydemo.greendao.entity.ContentProviderInfo;
import com.example.mbenben.studydemo.greendao.utils.DaoManager;
import com.example.mbenben.studydemo.utils.log.LogUtils;

import java.util.List;

/**
 * Created by MDove on 2018/4/11.
 */

public class CustomContentProvider extends ContentProvider {
    public static final int TABLE_DIR = 0;
    public static final int TABLE_ITEM = 1;
    public static final int TABLE_QUERY = 2;
    public static final String TABLE_NAME = "CONTENT_PROVIDER_INFO";
    public static final String INFO_ADD_CONTENT_KEY = "info_add_content_key";
    public static final String INFO_ADD_NAME_KEY = "info_add_name_key";
    public static final String URI_INFO_DIR = "content://com.example.mbenben.studydemo.provider/CONTENT_PROVIDER_INFO";
    public static final String URI_INFO_ITEM = "content://com.example.mbenben.studydemo.provider/CONTENT_PROVIDER_INFO/#";
    public static final String URI_INFO_QUERY = "content://com.example.mbenben.studydemo.provider/DAO_QUERY";
    public static final String TABLE_INFO_CONTENT = "M_CONTENT";
    public static final String TABLE_INFO_NAME = "M_NAME";

    public static final String TABLE_SELECTION_ID = "table_selection_id";
    public static final String TABLE_SELECTION_CONTENT = "table_selection_content";
    public static final String TABLE_SELECTION_NAME = "table_selection_name";

    private static UriMatcher sUriMatcher;
    private ContentProviderInfoDao mDao;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI("com.example.mbenben.studydemo.provider", TABLE_NAME, TABLE_DIR);
        sUriMatcher.addURI("com.example.mbenben.studydemo.provider", "CONTENT_PROVIDER_INFO/#", TABLE_ITEM);
        sUriMatcher.addURI("com.example.mbenben.studydemo.provider", "DAO_QUERY", TABLE_QUERY);
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
            case TABLE_QUERY: {
                switch (selection) {
                    case TABLE_SELECTION_ID: {
                        long id = Long.valueOf(selectionArgs[0]);
                        cursor = mDao.queryBuilder().where(ContentProviderInfoDao.Properties.Id.eq(id)).buildCursor().query();
                        break;
                    }
                    case TABLE_INFO_CONTENT: {
                        break;
                    }
                }
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
            case TABLE_QUERY: {
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
        int match = sUriMatcher.match(uri);
        switch (match) {
            case TABLE_ITEM:
            case TABLE_DIR: {
                String content = values.getAsString(INFO_ADD_CONTENT_KEY);
                String name = values.getAsString(INFO_ADD_NAME_KEY);
                ContentProviderInfo info = new ContentProviderInfo();
                info.setMContent(content);
                info.setMName(name);
                mDao.insert(info);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String uriString = uri.getPathSegments().get(1);
        long id = Long.valueOf(uriString);
        List<ContentProviderInfo> data = mDao.queryBuilder().where(ContentProviderInfoDao.Properties.Id.eq(id)).list();
        if (data == null || data.size() <= 0) {
            return 0;
        }
        ContentProviderInfo info = data.get(0);
        mDao.delete(info);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String uriString = uri.getPathSegments().get(1);
        long id = Long.valueOf(uriString);
        List<ContentProviderInfo> data = mDao.queryBuilder().where(ContentProviderInfoDao.Properties.Id.eq(id)).list();
        if (data == null || data.size() <= 0) {
            return 0;
        }
        ContentProviderInfo info = data.get(0);
        String content = values.getAsString(INFO_ADD_CONTENT_KEY);
        String name = values.getAsString(INFO_ADD_NAME_KEY);
        info.setMName(name);
        info.setMContent(content);
        mDao.update(info);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
