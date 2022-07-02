package com.ddplay.ch16;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close(); // 關閉資料庫
    }
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
        // 取得資料庫
        database = new SQLite(this, "Library", null, 1).getWritableDatabase();
        // 宣告 Adapter並連結listView
        List<String> items = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        btnInsert.setOnClickListener(view -> {
            if (editBook.length() == 0 || editPrice.length() == 0) {
                toast("請填入書籍及價格");
            } else {
                try {
                    database.execSQL("insert into library(book,price) values(?,?)",
                            new String[] {editBook.getText().toString(), editPrice.getText().toString()});
                    toast("成功新增:書籍" + editBook.getText().toString() + ",價格" + editPrice.getText().toString());
                    editBook.setText("");
                    editPrice.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("新增失敗");
                }
            }
        });
        btnUpdate.setOnClickListener(view -> {
            if (editBook.length() == 0 || editPrice.length() == 0) {
                toast("請填入書籍及需修改的價格");
            } else {
                try {
                    database.execSQL("update Library set price = "
                            + editPrice.getText().toString()
                            + " where book = '"
                            + editBook.getText().toString() + "'");
                    toast("成功更新:書籍" + editBook.getText().toString() + ",價格" + editPrice.getText().toString());
                    editBook.setText("");
                    editPrice.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("更新失敗");
                }
            }
        });
        btnDelete.setOnClickListener(view -> {
            if (editBook.length() == 0) {
                toast("請填入需刪除的書籍");
            } else {
                try {
                    database.execSQL("delete from Library where book = '" + editBook.getText().toString() + "'");
                    toast("成功刪除:書籍" + editBook.getText().toString());
                    editBook.setText("");
                    editPrice.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("刪除失敗");
                }
            }
        });
        btnQuery.setOnClickListener(view -> {
            String query;
            if (editBook.length() == 0) {
                query = "select * from library";
            } else {
                query = "select * from library where book = '" + editBook.getText().toString() + "'";
            }
            Cursor cursor = database.rawQuery(query, null);
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