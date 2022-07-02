package com.ddplay.ch17;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private SQLiteDatabase database;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 取出Ch17_2要刪除的書名.
        return database.delete("library", "book = '" + selection + "'", null);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }
    // 2. Resolver要求新增資料，則取得它給予的資料並新增至資料庫
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 取出Ch17_2給予的 book 資料
        long rowId = database.insert("library", null, values);
        return Uri.parse("content://com.ddplay.ch17/" + rowId);
    }
    // 1. 當Resolver要求操作資料時，先開啟資料庫
    @Override
    public boolean onCreate() {
        Context context = getContext();
        // 取得資料庫
        database = new SQLite(context, "Library", null, 1).getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 取出Ch17_2查詢的書名，若沒有書名則搜尋全部書籍
        String query;
        if (selection == null) {
            query = null;
        } else {
            query = "book = " + selection;
        }
        // 將搜尋完成的 Cursor 回傳
        return database.query("library", null, query, null, null, null, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return database.update("library", values, "book = '" + selection + "'", null);
    }
}