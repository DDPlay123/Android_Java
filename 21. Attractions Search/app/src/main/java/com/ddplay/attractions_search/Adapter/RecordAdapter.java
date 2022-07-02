package com.ddplay.attractions_search.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ddplay.attractions_search.Data.RecordData;
import com.ddplay.attractions_search.R;
import java.util.List;

@SuppressLint("ViewHolder")
public class RecordAdapter extends ArrayAdapter<RecordData> {
    private final Integer layout;

    public RecordAdapter(Context context, Integer layout, List<RecordData> data) {
        super(context, layout, data);
        this.layout = layout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(viewGroup.getContext(), layout, null);
        RecordData item = getItem(i);
        TextView tvName = v.findViewById(R.id.tv_name);
        TextView tvVicinity = v.findViewById(R.id.tv_vicinity);
        tvName.setText(item.getName());
        tvVicinity.setText(item.getVicinity());

        return v;
    }
}

