package com.ddplay.ch5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button presetToast = findViewById(R.id.toast);
        Button customToast = findViewById(R.id.custom);
        Button buttonSnackBar = findViewById(R.id.snackbar);
        Button buttonAlertdialog = findViewById(R.id.dialog1);
        Button tableAlertdialog = findViewById(R.id.dialog2);
        Button singleButtonAlertdialog = findViewById(R.id.dialog3);

        String[] items = {"選項 1", "選項 2", "選項 3", "選項 4", "選項 5"};

        presetToast.setOnClickListener(view ->
                Toast.makeText(this, "預設Toast", Toast.LENGTH_SHORT).show());
        customToast.setOnClickListener(view -> {
            Toast toast = new Toast(this);
            toast.setGravity(Gravity.BOTTOM, 0, 50);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(getLayoutInflater().inflate(R.layout.custom_toast, null));
            toast.show();
        });
        buttonSnackBar.setOnClickListener(view ->
            Snackbar.make(view, "按鈕式SnackBar", Snackbar.LENGTH_LONG)
                .setAction("按鈕", v ->
                        Toast.makeText(this, "已回應", Toast.LENGTH_SHORT).show())
                .setActionTextColor(Color.RED).show());
        buttonAlertdialog.setOnClickListener(view -> new AlertDialog.Builder(this)
                .setTitle("按鈕式AlertDialog")
                .setMessage("AlertDialog 內容")
                .setPositiveButton("右按鈕", (dialogInterface, i) ->
                        Toast.makeText(this, "右按鈕", Toast.LENGTH_SHORT).show())
                .setNeutralButton("左按鈕", (dialogInterface, i) ->
                        Toast.makeText(this, "左按鈕", Toast.LENGTH_SHORT).show())
                .setNegativeButton("中按鈕", (dialogInterface, i) ->
                        Toast.makeText(this, "中按鈕", Toast.LENGTH_SHORT).show())
                .show());
        tableAlertdialog.setOnClickListener(view -> new AlertDialog.Builder(this)
                .setTitle("列表式AlertDialog")
                .setItems(items, (dialogInterface, i) ->
                        Toast.makeText(this, "你選的是" + items[i], Toast.LENGTH_SHORT).show()
                ).show());
        singleButtonAlertdialog.setOnClickListener(view -> {
            AtomicInteger position = new AtomicInteger();
            new AlertDialog.Builder(this)
                    .setTitle("單選式AlertDialog")
                    .setSingleChoiceItems(items, 0, (dialogInterface, i) -> position.set(i))
                    .setPositiveButton("確定", (dialogInterface, i) ->
                            Toast.makeText(this, "你選的是" + items[position.get()], Toast.LENGTH_SHORT).show()
                    ).show();
        });
    }
}