package com.ddplay.ch14;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver); // 離開Activity時，註銷receiver
        super.onDestroy();
    }

    @SuppressLint("StaticFieldLeak")
    private static TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMsg = findViewById(R.id.tv_msg);
        Button btnMusic = findViewById(R.id.btn_music);
        Button btnNews = findViewById(R.id.btn_news);
        Button btnSport = findViewById(R.id.btn_sport);

        btnMusic.setOnClickListener(view -> register("music"));
        btnNews.setOnClickListener(view -> register("news"));
        btnSport.setOnClickListener(view -> register("sport"));
    }

    private static final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                tvMsg.setText(intent.getStringExtra("msg"));
            }
        }
    };

    private void register(String channel) {
        // 建立 IntentFilter 物件來指定接收的頻道，並註冊 Receiver
        IntentFilter intentFilter = new IntentFilter(channel);
        registerReceiver(receiver, intentFilter);
        // 啟動MyReceiver，並且傳送頻道Channel
        Intent intent = new Intent(this, MyService.class);
        startService(intent.putExtra("channel", channel));
    }
}