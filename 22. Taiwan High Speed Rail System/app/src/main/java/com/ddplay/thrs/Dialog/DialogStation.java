package com.ddplay.thrs.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.ddplay.thrs.Adapter.ListAdapter;
import com.ddplay.thrs.Data.StationData;
import com.ddplay.thrs.R;

import java.util.ArrayList;
import java.util.List;

public class DialogStation extends AlertDialog {
    private final List<StationData> data;
    private final TextView tv;

    public DialogStation(Context context, List<StationData> data, TextView tv) {
        super(context);
        this.data = data;
        this.tv = tv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_search);

        ImageButton btnClear = findViewById(R.id.btn_clear);
        EditText edStation = findViewById(R.id.ed_station);
        ListView listView = findViewById(R.id.listView);

        ListAdapter adapter = new ListAdapter(getContext(), R.layout.item_search, data);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            tv.setText(data.get(position).getStation());
            dismiss();
        });

        btnClear.setOnClickListener(v -> edStation.setText(""));
        edStation.setSingleLine(true);

        edStation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<StationData> temp = new ArrayList<>();
                String string = s.toString();
                data.forEach(stationData -> {
                    if (stationData.getAddress().contains(string)) {
                        temp.add(stationData);
                    }
                });
                ListAdapter adapter = new ListAdapter(getContext(), R.layout.item_search, temp);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    tv.setText(temp.get(position).getStation());
                    dismiss();
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dismiss();
        return super.onTouchEvent(event);
    }
}
