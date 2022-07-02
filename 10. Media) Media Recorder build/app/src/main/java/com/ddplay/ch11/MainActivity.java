package com.ddplay.ch11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tvText;
    private Button startRecord;
    private Button stopRecord;
    private Button playRecord;
    private Button stopPlay;

    private final MediaRecorder mediaRecorder = new MediaRecorder();
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private File folder;
    private String fileName;

    @Override
    protected void onDestroy() {
        mediaRecorder.release(); //釋放錄音器佔用資源
        mediaPlayer.release(); //釋放播放器佔用資源
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.text);
        startRecord = findViewById(R.id.start_record);
        stopRecord = findViewById(R.id.stop_record);
        playRecord = findViewById(R.id.play_record);
        stopPlay = findViewById(R.id.stop_play_record);

        // mkdir()創一個資料夾，mkdirs()創多個。
        folder = new File(getFilesDir().getAbsolutePath() + "/record");
        if (!folder.exists()) //noinspection ResultOfMethodCallIgnored
            folder.mkdir();
        // 錄音權限
        String permission = Manifest.permission.RECORD_AUDIO;
        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        } else {
            button();
        }
    }
    // Button function
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void button() {
        startRecord.setOnClickListener(view -> {
            fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //目前時間
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //聲音來源為麥克風
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); //設定輸出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); //設定編碼器
            mediaRecorder.setOutputFile(new File(folder, fileName).getAbsolutePath()); //設定輸出路徑
            try {
                mediaRecorder.prepare(); //準備錄音
                mediaRecorder.start(); //開始錄音
                tvText.setText("錄音中...");
                startRecord.setEnabled(false);
                stopRecord.setEnabled(true);
                playRecord.setEnabled(false);
                stopPlay.setEnabled(false);
            } catch (IOException e) {
                e.printStackTrace();
                tvText.setText("發生錯誤!!!");
                startRecord.setEnabled(true);
                stopRecord.setEnabled(false);
                playRecord.setEnabled(false);
                stopPlay.setEnabled(false);
            }
        });
        stopRecord.setOnClickListener(view -> {
            try {
                File file = new File(folder, fileName);
                mediaRecorder.stop();
                tvText.setText("已儲存至" + file.getAbsolutePath()); //定義錄音檔案
                startRecord.setEnabled(true);
                stopRecord.setEnabled(false);
                playRecord.setEnabled(true);
                stopPlay.setEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
                mediaRecorder.reset(); //重置錄音器
                tvText.setText("錄音失敗!!!");
                startRecord.setEnabled(true);
                stopRecord.setEnabled(false);
                playRecord.setEnabled(false);
                stopPlay.setEnabled(false);
            }
        });
        playRecord.setOnClickListener(view -> {
            File file = new File(folder, fileName);
            try {
                mediaPlayer.setDataSource(getApplicationContext(), Uri.fromFile(file)); //設定音訊來源
                mediaPlayer.setVolume(1f, 1f); //設定左右聲道音量
                mediaPlayer.prepare(); //準備播放
                mediaPlayer.start(); //開始播放
                tvText.setText("播放中...");
                startRecord.setEnabled(false);
                stopRecord.setEnabled(false);
                playRecord.setEnabled(false);
                stopPlay.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
                tvText.setText("發生錯誤!!!");
                startRecord.setEnabled(true);
                stopRecord.setEnabled(false);
                playRecord.setEnabled(true);
                stopPlay.setEnabled(false);
            }
        });
        //設定播放器播放完畢的監聽器
        mediaPlayer.setOnCompletionListener(it -> {
            it.reset(); //重置播放器
            tvText.setText("播放結束!!!");
            startRecord.setEnabled(true);
            stopRecord.setEnabled(false);
            playRecord.setEnabled(true);
            stopPlay.setEnabled(false);
        });
        stopPlay.setOnClickListener(view -> {
            mediaPlayer.stop();
            mediaPlayer.reset();
            tvText.setText("播放結束!!!");
            startRecord.setEnabled(true);
            stopRecord.setEnabled(false);
            playRecord.setEnabled(true);
            stopPlay.setEnabled(false);
        });
    }
    // 要求權限的方法
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 判斷是否有結果且識別標籤相同
        if (requestCode == 0) {
            // 取出結果並判斷是否允許權限
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish();//若拒絕給予錄音權限，則關閉應用程式
            } else {
                button();
            }
        }
    }
}