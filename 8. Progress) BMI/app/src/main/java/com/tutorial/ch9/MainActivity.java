package com.tutorial.ch9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({"SetTextI18n", "DefaultLocale"})
public class MainActivity extends AppCompatActivity {
    TextView tvWeight;
    TextView tvFat;
    TextView tvBmi;
    LinearLayout llProgress;
    ProgressBar progressBar;
    TextView tvProgressBar;
    int progress = 0;
    double standWeight;
    double bodyFat;
    double bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edHeight = findViewById(R.id.edit_height);
        EditText edWeight = findViewById(R.id.edit_weight);
        TextView edAge = findViewById(R.id.edit_age);
        RadioButton rgBoy = findViewById(R.id.rg_boy);
        Button btnCalculate = findViewById(R.id.btn_calculate);
        tvWeight = findViewById(R.id.tv_weight);
        tvFat = findViewById(R.id.tv_fat);
        tvBmi = findViewById(R.id.tv_bmi);
        llProgress = findViewById(R.id.ll_progress);
        progressBar = findViewById(R.id.progressBar2);
        tvProgressBar = findViewById(R.id.tv_progress);

        btnCalculate.setOnClickListener(view -> {
            if (edHeight.length() < 1 || edWeight.length() < 1 || edAge.length() < 1) {
                Toast.makeText(this, "請輸入身高、體重及年齡", Toast.LENGTH_SHORT).show();
            } else {
                float height = Float.parseFloat(String.valueOf(edHeight.getText()));
                float weight = Float.parseFloat(String.valueOf(edWeight.getText()));
                float age = Float.parseFloat(String.valueOf(edAge.getText()));

                bmi = weight / Math.pow((height / 100), 2);

                if (rgBoy.isChecked()){
                    standWeight = (height - 80) * 0.7;
                    bodyFat = (1.39 * bmi + 0.16 * age - 19.34);
                }else{
                    standWeight = (height - 70) * 0.6;
                    bodyFat = (1.39 * bmi + 0.16 * age - 9);
                }

                llProgress.setVisibility(View.VISIBLE);

                new Thread(() -> {
                    progress = 0;
                    while (progress < 100) {
                        try {
                            Thread.sleep(10);
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                            progress += 1;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                progressBar.setProgress(progress);
                tvProgressBar.setText(progress + "%");
            }
            if (progress >= 100) {
                llProgress.setVisibility(View.GONE);
                tvWeight.setText("標準體重\n" + String.format("%.2f", standWeight));
                tvFat.setText("體脂肪\n" + String.format("%.2f", bodyFat));
                tvBmi.setText("BMI\n" + String.format("%.2f", bmi));
            }
        }
    };
}