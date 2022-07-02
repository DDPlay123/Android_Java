package com.ddplay.ch6;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        ListView listView = findViewById(R.id.listView);
        GridView gridView = findViewById(R.id.gridView);
        // 載入資料
        @SuppressLint("Recycle")
        TypedArray array = getResources().obtainTypedArray(R.array.image_list); //從 R 類別讀取圖檔
        List<Data> data_list = new ArrayList<>(); //儲存水果資訊
        List<String> count = new ArrayList<>(); //儲存購買數量資訊
        // 依序載入資料
        for (int i = 0; i < array.length(); i ++) {
            int photo = array.getResourceId(i, 0); //水果圖片 Id
            String name = "水果" + (i + 1); //水果名稱
            int price = (int)(Math.random() * (100 - 10 + 1)) + 10; // 建立價格範圍 10 ~ 100
            count.add((i + 1) + "個"); //新增可購買數量資訊
            data_list.add(new Data(photo, name, price));//新增水果資訊
        }
        array.recycle(); //釋放圖檔資源

        //建立 ArrayAdapter 物件，並傳入字串與 simple_list_item_1.xml
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, count));
        //建立 Adapter 物件，並傳入 adapter_horizontal 作為畫面
        listView.setAdapter(new Adapter(this, R.layout.adapter_horizontal, data_list));
        //設定橫向顯示列數
        gridView.setNumColumns(3);
        //建立 MyAdapter 物件，並傳入 adapter_vertical 作為畫面
        gridView.setAdapter(new Adapter(this, R.layout.adapter_vertical, data_list));
    }
}