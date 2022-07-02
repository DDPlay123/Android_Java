package com.ddplay.ch17_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final Uri uri = Uri.parse("content://com.ddplay.ch17");

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editBook = findViewById(R.id.edit_book);
        EditText editPrice = findViewById(R.id.edit_price);
        Button btnInsert = findViewById(R.id.btn_insert);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnDelete = findViewById(R.id.btn_delete);
        Button btnQuery = findViewById(R.id.btn_query);
        ListView listView = findViewById(R.id.listView);

        // 宣告 Adapter並連結listView
        List<String> items = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        btnInsert.setOnClickListener(view -> {
            if (editBook.length() == 0 || editPrice.length() == 0) {
                toast("請填入書籍及價格");
            } else {
                // 建立ContentValues物件，用於存放要新增的資料
                ContentValues values = new ContentValues();
                values.put("book", editBook.getText().toString());
                values.put("price", editPrice.getText().toString());
                // 透過 Resolver 向 Provider 新增一筆書籍紀錄，並取得該紀錄的 Uri
                if (getContentResolver().insert(uri, values) == null) {
                    toast("新增失敗");
                } else {
                    toast("成功新增:書籍" + editBook.getText().toString() + ",價格" + editPrice.getText().toString());
                }
            }
        });
        btnUpdate.setOnClickListener(view -> {
            if (editBook.length() == 0 || editPrice.length() == 0) {
                toast("請填入書籍及需修改的價格");
            } else {
                ContentValues values = new ContentValues();
                values.put("price", editPrice.getText().toString());
                if (getContentResolver().update(uri, values, editBook.getText().toString(), null) == 0) {
                    toast("更新失敗");
                } else {
                    toast("成功更新:書籍" + editBook.getText().toString() + ",價格" + editPrice.getText().toString());
                }
            }
        });
        btnDelete.setOnClickListener(view -> {
            if (editBook.length() == 0) {
                toast("請填入需刪除的書籍");
            } else {
                if (getContentResolver().delete(uri, editBook.getText().toString(), null) == 0) {
                    toast("刪除失敗");
                } else {
                    toast("成功刪除:書籍" + editBook.getText().toString());
                }
            }
        });
        btnQuery.setOnClickListener(view -> {
            String query;
            if (editBook.length() == 0) {
                query = null;
            } else {
                query = "'" + editBook.getText().toString() + "'";
            }
            Cursor cursor = getContentResolver().query(uri, null, query, null, null);
            cursor.moveToFirst(); // 從第一筆開始輸出
            items.clear(); // 清空舊資料
            toast("共有" + cursor.getCount() + "筆");
            for (int i = 0; i < cursor.getCount(); i ++) {
                // 加入新資料到items
                items.add("書籍:" + cursor.getString(0) + "價格:" + cursor.getString(1));
                cursor.moveToNext();
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        });
    }
}