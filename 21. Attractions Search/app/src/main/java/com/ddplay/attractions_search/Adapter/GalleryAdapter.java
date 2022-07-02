package com.ddplay.attractions_search.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ddplay.attractions_search.Data.Gallery;
import com.ddplay.attractions_search.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GalleryAdapter extends ArrayAdapter<Gallery> {
    private final int layout;

    public GalleryAdapter(@NonNull Context context, int layout, List<Gallery>data) {
        super(context, layout, data);
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View v = View.inflate(parent.getContext(), layout, null);
        Gallery item = getItem(position);
        ImageView photo = v.findViewById(R.id.img_photo);
        ProgressBar progressBar = v.findViewById(R.id.progressBar);
        Picasso.get().load(item.getPhoto()).into(photo, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }
}
