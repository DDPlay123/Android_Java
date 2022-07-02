package com.ddplay.ch18;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSearch = findViewById(R.id.btn_search);

        // GET Method
        btnSearch.setOnClickListener(view -> {
            // 1. 建立 Request.Builder 物件，藉由 url() 將網址傳入，再建立 Request 物件
            Request req = new Request.Builder()
                    .url("https://android-quiz-29a4c.web.app/")
                    .build();
            // 2. 建立 OkHttpClient 物件，藉由 newCall()發送請求，並在 enqueue()接收回傳
            new OkHttpClient().newCall(req).enqueue(new Callback() {
                // 失敗執行此方法
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(MainActivity.this, "查詢失敗", Toast.LENGTH_SHORT).show();
                }
                // 成功執行此方法
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    // 3. 使用 response.body?.string()取得 JSON 字串
                    String json = Objects.requireNonNull(response.body()).string();
                    // 4. 建立 Gson 並使用其 fromJson()方法，將 JSON 字串以 MyObject 格式輸出
                    MyObject myObject = new Gson().fromJson(json, MyObject.class);
                    // 5. 顯示結果
                    String[] items = new String[myObject.results.content.size()];
                    for (int i = 0; i < myObject.results.content.size(); i++) {
                        items[i] = "lat:" + myObject.results.content.get(i).getLat() +
                                    ", lng:" + myObject.results.content.get(i).getLng() +
                                    ", name:" + myObject.results.content.get(i).getName();
                    }
                    // 切換到主執行緒將畫面更新
                    runOnUiThread(() -> new AlertDialog.Builder(MainActivity.this)
                            .setTitle("空氣品質")
                            .setItems(items, null)
                            .show());
                }
            });
        });
    }
}