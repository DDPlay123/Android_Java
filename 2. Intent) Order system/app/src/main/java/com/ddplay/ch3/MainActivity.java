package com.ddplay.ch3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // https://developer.android.com/training/basics/intents/result#java
    private TextView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button select = findViewById(R.id.select);
        menu = findViewById(R.id.menu);
        select.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecActivity.class);
            mStartForResult.launch(intent);
        });
    }
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String drink = intent.getStringExtra("drink");
                            String sweet = intent.getStringExtra("sweet");
                            String ice = intent.getStringExtra("ice");
                            menu.setText("飲料:" + drink + "\n\n甜度:" + sweet + "\n\n冰塊:" + ice);
                        }
                    }
                }
            });
}