package com.ddplay.ch19;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

// 建立一個類別，使其繼承 FirebaseMessagingService 類別
public class MyMessageService extends FirebaseMessagingService {
    // 取得新的 Token 時被調用，通常會於第一次啟動應用程式時進入
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e("New Token", token);
    }
    // 應用程式正呈現於螢幕且收到推播通知時進入
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        // 藉由迴圈將通知附帶的資料取出
        for (Map.Entry<String, String> entry : message.getData().entrySet()) {
            Log.e("_Data", "Key:" + entry.getKey() + ", Value:" + entry.getValue());
        }
    }
}
