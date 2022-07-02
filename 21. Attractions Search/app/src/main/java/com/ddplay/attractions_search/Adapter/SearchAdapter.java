package com.ddplay.attractions_search.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ddplay.attractions_search.Data.DetailData;
import com.ddplay.attractions_search.R;
import java.util.List;

@SuppressLint("ViewHolder")
public class SearchAdapter extends ArrayAdapter<DetailData> {
    private final Integer layout;

    public SearchAdapter(Context context, Integer layout, List<DetailData> data) {
        super(context, layout, data);
        this.layout = layout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(viewGroup.getContext(), layout, null);
        DetailData item = getItem(i);
        TextView tvName = v.findViewById(R.id.tv_name);
        TextView tvVicinity = v.findViewById(R.id.tv_vicinity);
        tvName.setText(item.getName());
        tvVicinity.setText(item.getVicinity());

        return v;
    }
}
