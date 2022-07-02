package com.ddplay.thrs.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ddplay.thrs.R;

public class DialogWarn extends Dialog {
    private String message;
    private IOnCancelListener negativeListener;
    private IOnConfirmListener positiveListener;
    private IOnCloseListener closeListener;

    public DialogWarn(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warn);

        TextView tvMsg = findViewById(R.id.tv_msg);
        Button btnNegative = findViewById(R.id.btn_negative);
        Button btnPositive = findViewById(R.id.btn_positive);
        ImageButton btnClose = findViewById(R.id.btn_close);

        if (message != null) tvMsg.setText(message);
        btnNegative.setOnClickListener(v -> negativeListener.negative(this));
        btnPositive.setOnClickListener(v -> positiveListener.positive(this));
        btnClose.setOnClickListener(v -> closeListener.close(this));
    }
    // 設定功能
    public DialogWarn setMsg(String msg) {
        this.message = msg;
        return this;
    }
    public DialogWarn negative(IOnCancelListener listener) {
        this.negativeListener = listener;
        return this;
    }
    public DialogWarn positive(IOnConfirmListener listener) {
        this.positiveListener = listener;
        return this;
    }
    public DialogWarn close(IOnCloseListener listener) {
        this.closeListener = listener;
        return this;
    }
    // 按鈕介面
    public interface IOnCancelListener {
        void negative(DialogWarn dialog);
    }
    public interface IOnConfirmListener {
        void positive(DialogWarn dialog);
    }
    public interface IOnCloseListener {
        void close(DialogWarn dialog);
    }
}
