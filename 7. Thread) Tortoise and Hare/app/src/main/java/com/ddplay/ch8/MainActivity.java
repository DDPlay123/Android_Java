package com.ddplay.ch8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {
    private static SeekBar sbRabbit;
    private static SeekBar sbTurtle;
    private static int disRabbit = 0;
    private static int disTurtle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbRabbit = findViewById(R.id.sb_rabbit);
        sbTurtle = findViewById(R.id.sb_turtle);
        Button btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(view -> {
            disRabbit = 0;
            disTurtle = 0;
            sbRabbit.setProgress(0);
            sbTurtle.setProgress(0);
            rabbit();
            turtle();
        });
    }

    private void turtle() {
        class MyTimerTask extends TimerTask {
            @Override
            public void run() {
                disTurtle += 1;
                sbTurtle.setProgress(disTurtle);
                if (disTurtle >= 100 || disRabbit >= 100) this.cancel();
                if (disTurtle >= 100 && disRabbit <100) runOnUiThread(() -> toast("烏龜勝!!!"));
            }
        }
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 100);
    }

    private void rabbit() {
        new Thread(() -> {
            Boolean[] sleep = {true, true, false};
            while (disRabbit < 100 && disTurtle < 100) {
                try {
                    Thread.sleep(100);
                    if (sleep[(int) (Math.random() * 3)]) {
                        Thread.sleep(300);
                    }
                    disRabbit += 3;
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) sbRabbit.setProgress(disRabbit);
            if (disRabbit >= 100 && disTurtle <100) toast("兔子勝!!!");
        }
    };

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}