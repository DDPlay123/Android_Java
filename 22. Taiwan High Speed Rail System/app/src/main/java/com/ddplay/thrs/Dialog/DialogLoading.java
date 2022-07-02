package com.ddplay.thrs.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ddplay.thrs.R;

public class DialogLoading {
    private final Activity activity;
    AlertDialog dialog;
    public DialogLoading(Activity activity) {
        this.activity = activity;
    }
    public void startLoading() {
        // set View
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_loading, null);
        // set Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    public void endLoading() {
        dialog.dismiss();
    }
}
