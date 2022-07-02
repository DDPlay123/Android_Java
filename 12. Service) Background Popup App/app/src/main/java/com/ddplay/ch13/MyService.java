package com.ddplay.ch13;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Intent intent = new Intent(this, SecActivity.class);
                // 加入 Flag 表示要產生一個新的 Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        // START_STICKY : Service 被殺掉，系統會重啟，但是 Intent 會是 null。
        // START_NOT_STICKY : Service 被系統殺掉，不會重啟。
        // START_REDELIVER_INTENT : Service 被系統殺掉，重啟且 Intent 會重傳。
        return START_NOT_STICKY; //Service 終止後不再重啟
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // 由於是啟動 Service，因此 onBind 會是 return null，
        // 由於 Service 是運作在 UI Thread 上面，
        // 因此，你必須將長任務開個 Thread 並且執行在 onStartCommand 方法內。
        return null;
    }
}