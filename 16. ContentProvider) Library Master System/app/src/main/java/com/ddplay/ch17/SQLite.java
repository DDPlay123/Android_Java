package com.ddplay.ch17;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {
    public SQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 建Table
        sqLiteDatabase.execSQL("create table library(book text primary key, price interger not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 更新資料庫版本，再次執行onCreate
        sqLiteDatabase.execSQL("drop table if exists library");
        onCreate(sqLiteDatabase);
    }
}