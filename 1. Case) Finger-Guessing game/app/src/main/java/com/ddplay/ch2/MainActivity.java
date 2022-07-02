package com.ddplay.ch2;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editName = findViewById(R.id.edit_name);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button btnMora = findViewById(R.id.mora);
        TextView tvPlayerName = findViewById(R.id.player_name);
        TextView tvWinner = findViewById(R.id.winner);
        TextView tvPlayerMora = findViewById(R.id.player_mora);
        TextView tvPcMora = findViewById(R.id.pc_mora);

        btnMora.setOnClickListener(view -> {
            int rand = (int)(Math.random() * 3);
            String selectMora = "布";
            switch(radioGroup.getCheckedRadioButtonId()) {
                case R.id.paper:
                    selectMora = "布";
                    break;
                case R.id.scissor:
                    selectMora = "剪刀";
                    break;
                case R.id.stone:
                    selectMora = "石頭";
                    break;
            }
            String pcMora = "布";
            switch (rand) {
                case 0:
                    pcMora = "布";
                    break;
                case 1:
                    pcMora = "剪刀";
                    break;
                case 2:
                    pcMora = "石頭";
                    break;
            }
            if (editName.length() == 0) {
                Toast.makeText(this, "請輸入你的名字!!!", Toast.LENGTH_SHORT).show();
                tvPlayerName.setText("名稱");
                tvWinner.setText("贏家");
                tvPlayerMora.setText("玩家出拳");
                tvPcMora.setText("電腦出拳");
            } else {
                tvPlayerName.setText("名稱\n" + editName.getText());
                tvPlayerMora.setText("玩家出拳\n" + selectMora);
                tvPcMora.setText("電腦出拳\n" + pcMora);
                if (selectMora.equals("布") && rand == 2 ||
                    selectMora.equals("剪刀") && rand == 0 ||
                    selectMora.equals("石頭") && rand == 1) {
                    tvWinner.setText("贏家\n" + editName.getText());
                    Toast.makeText(this, "Win!!!", Toast.LENGTH_SHORT).show();
                }else if (selectMora.equals("布") && rand == 1 ||
                          selectMora.equals("剪刀") && rand == 2 ||
                          selectMora.equals("石頭") && rand == 0) {
                    tvWinner.setText("贏家\nPC");
                    Toast.makeText(this, "Loss!!!", Toast.LENGTH_SHORT).show();
                } else {
                    tvWinner.setText("贏家\nTie");
                    Toast.makeText(this, "Tie!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}