package com.ddplay.ch7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        EditText editName = findViewById(R.id.edit_name);
        EditText editPhone = findViewById(R.id.edit_phone);
        Button btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(view -> {
            if (editName.length() == 0)  Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show();
            else if (editPhone.length() == 0) Toast.makeText(this, "請輸入電話", Toast.LENGTH_SHORT).show();
            else {
                getIntent().putExtra("name", editName.getText().toString());
                getIntent().putExtra("phone", editPhone.getText().toString());
                setResult(Activity.RESULT_OK, getIntent());
                finish();
            }
        });
    }
}