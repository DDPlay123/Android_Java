package com.ddplay.thrs.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.ddplay.thrs.R;

public class DialogWarn2 extends Dialog {
    private String message;
    private IOnConfirmListener positiveListener;
    private IOnCloseListener closeListener;

    public DialogWarn2(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warn_2);

        TextView tvMsg = findViewById(R.id.tv_msg);
        Button btnPositive = findViewById(R.id.btn_positive);
        ImageButton btnClose = findViewById(R.id.btn_close);

        if (message != null) tvMsg.setText(message);
        btnPositive.setOnClickListener(v -> positiveListener.positive(this));
        btnClose.setOnClickListener(v -> closeListener.close(this));
    }
    // 設定功能
    public DialogWarn2 setMsg(String msg) {
        this.message = msg;
        return this;
    }
    public DialogWarn2 positive(IOnConfirmListener listener) {
        this.positiveListener = listener;
        return this;
    }
    public DialogWarn2 close(IOnCloseListener listener) {
        this.closeListener = listener;
        return this;
    }
    // 按鈕介面
    public interface IOnConfirmListener {
        void positive(DialogWarn2 dialog);
    }
    public interface IOnCloseListener {
        void close(DialogWarn2 dialog);
    }
}

