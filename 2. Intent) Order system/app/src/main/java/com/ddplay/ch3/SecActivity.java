package com.ddplay.ch3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SecActivity extends AppCompatActivity {
    String sweet;
    String ice;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        EditText editDrink = findViewById(R.id.edit_drink);
        RadioGroup selectSweet = findViewById(R.id.select_sweet);
        RadioGroup selectIce = findViewById(R.id.select_ice);
        Button btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(view -> {
            if (editDrink.length() == 0) {
                Toast.makeText(this, "請輸入飲料名稱", Toast.LENGTH_SHORT).show();
            } else {
                switch (selectSweet.getCheckedRadioButtonId()) {
                    case R.id.no_sugar:
                        sweet = "無糖";
                        break;
                    case R.id.low_sugar:
                        sweet = "微糖";
                        break;
                    case R.id.half_sugar:
                        sweet = "半糖";
                        break;
                    case R.id.full_sugar:
                        sweet = "全糖";
                        break;
                }
                switch (selectIce.getCheckedRadioButtonId()) {
                    case R.id.no_ice:
                        ice = "去冰";
                        break;
                    case R.id.low_ice:
                        ice = "微冰";
                        break;
                    case R.id.less_ice:
                        ice = "少冰";
                        break;
                    case R.id.full_ice:
                        ice = "正常冰";
                        break;
                }
                getIntent().putExtra("drink", editDrink.getText().toString());
                getIntent().putExtra("sweet",  sweet);
                getIntent().putExtra("ice", ice);
                setResult(Activity.RESULT_OK, getIntent());
                finish();
            }
        });
    }
}