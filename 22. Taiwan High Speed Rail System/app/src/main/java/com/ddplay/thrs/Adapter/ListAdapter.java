package com.ddplay.thrs.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ddplay.thrs.Data.StationData;
import com.ddplay.thrs.R;

import java.util.List;

public class ListAdapter  extends ArrayAdapter<StationData> {
    private final Integer layout;

    public ListAdapter(Context context, Integer layout, List<StationData> data) {
        super(context, layout, data);
        this.layout = layout;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(viewGroup.getContext(), layout, null);
        StationData item = getItem(i);
        TextView tvStation = v.findViewById(R.id.tv_station);
        TextView tvAddress = v.findViewById(R.id.tv_address);
        tvStation.setText(item.getStation());
        tvAddress.setText(item.getAddress());

        return v;
    }
}
