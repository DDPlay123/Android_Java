package com.ddplay.ch19;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.Button);

        button.setOnClickListener(view -> {
            // 1. 建立通知管理物件
            NotificationManagerCompat nm = NotificationManagerCompat.from(this);
            // 2. 如果 Android是8.0以上，需先建立通知頻道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 設定頻道id，名稱及訊息優先權
                String name = "My Channel";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("Ch19", name, importance);
                // 建立頻道
                nm.createNotificationChannel(channel);
            }
            // 3. 建立Intent、PendingIntent，點選通知即可進到App
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
            // 4. 定義通知訊息並發送
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Ch19")
                    .setSmallIcon(android.R.drawable.btn_star_big_on)
                    .setContentTitle("折價券") // 標題
                    .setContentText("您還有一張五折折價券，滿額消費即贈現金回饋") // 內容
                    .setContentIntent(pendingIntent) // 當通知被點選後的動作
                    .setAutoCancel(true) // 通知被點選後，自動消失
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT); // 通知優先權
            nm.notify(0, builder.build());
        });
    }
}