package com.ddplay.attractions_search.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 建Table
        sqLiteDatabase.execSQL("CREATE TABLE history(_id integer PRIMARY KEY AUTOINCREMENT, name text, vicinity text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 更新資料庫版本，再次執行onCreate
        sqLiteDatabase.execSQL("drop table if exists history");
        onCreate(sqLiteDatabase);
    }
}
