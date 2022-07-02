package com.ddplay.attractions_search.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ddplay.attractions_search.R;

public class DialogInfo extends Dialog  {
    private String msg;
    private IOBtnInfo btnInfoListener;
    private IOBtnGps btnGpsListener;

    public DialogInfo(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info);

        TextView tvMsg = findViewById(R.id.tv_msg);
        Button btnInfo = findViewById(R.id.btn_info);
        Button btnGps  = findViewById(R.id.btn_gps);

        if (msg != null) tvMsg.setText(msg);
        btnInfo.setOnClickListener(v -> btnInfoListener.btnInfo(this));
        btnGps.setOnClickListener(v -> btnGpsListener.btnGps(this));
    }
    // 設定功能
    public DialogInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }
    public DialogInfo setInfo(IOBtnInfo listener) {
        this.btnInfoListener = listener;
        return this;
    }
    public DialogInfo setGps(IOBtnGps listener) {
        this.btnGpsListener = listener;
        return this;
    }
    // 按鈕介面
    public interface IOBtnInfo {
        void btnInfo(DialogInfo dialog);
    }
    public interface IOBtnGps {
        void btnGps(DialogInfo dialog);
    }
}
