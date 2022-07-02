package com.ddplay.ch14;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    private static String channel = "";
    private void broadcast(String msg) {
        sendBroadcast(new Intent(channel).putExtra("msg", msg));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            channel = intent.getStringExtra("channel");
        }
        String ch;
        switch (channel) {
            case "music":
                ch = "音樂頻道";
                break;
            case "news":
                ch = "新聞頻道";
                break;
            case "sport":
                ch = "運動頻道";
                break;
            default:
                ch = "頻道錯誤";
                break;
        }
        broadcast(ch);
        new Thread(()->{
            try {
                Thread.sleep(3000);
                String c;
                switch (channel) {
                    case "music":
                        c = "播放音樂廣播";
                        break;
                    case "news":
                        c = "播放新聞廣播";
                        break;
                    case "sport":
                        c = "播放運動廣播";
                        break;
                    default:
                        c = "頻道錯誤";
                        break;
                }
                broadcast(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}