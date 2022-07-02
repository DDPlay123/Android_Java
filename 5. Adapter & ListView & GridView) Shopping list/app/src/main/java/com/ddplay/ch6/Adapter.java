package com.ddplay.ch6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

@SuppressLint("ViewHolder")
public class Adapter extends ArrayAdapter<Data> {
    private final Integer layout;

    public Adapter(Context context, Integer layout, List<Data> data) {
        super(context, layout, data);
        this.layout = layout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //依據傳入的 Layout 建立畫面
        View v = View.inflate(viewGroup.getContext(), layout, null);
        //將圖片指派給 ImageView 呈現
        ImageView imgPhoto = v.findViewById(R.id.img_photo);
        imgPhoto.setImageResource(getItem(i).getPhoto());
        //將訊息指派給 TextView 呈現，若是垂直排列則為名稱，否則為名稱及價格
        TextView tvMsg = v.findViewById(R.id.tv_msg);
        if (layout == R.layout.adapter_vertical) {
            tvMsg.setText(getItem(i).getName());
        } else {
            tvMsg.setText(getItem(i).getName() + " : " + getItem(i).getPrice() + "元");
        }
        return v;
    }
}
