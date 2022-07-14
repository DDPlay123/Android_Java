package com.ddplay.thrs.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddplay.thrs.Data.RestaurantItem;
import com.ddplay.thrs.Data.TrainItem;
import com.ddplay.thrs.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private final List<RestaurantItem> data;
    private TrainAdapter.onItemClickListener mListener;
    public RestaurantAdapter(List<RestaurantItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(data.get(position).photo)
                .into(holder.imgPicture, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }
                });
        holder.tvTitle.setText(data.get(position).name);
        holder.tvAddress.setText(data.get(position).vicinity);
        holder.tvDistance.setText(String.format("距離高鐵站：%.2f公里", data.get(position).distance));
        holder.tvEvaluate.setText("評價：" + data.get(position).rating + "（" + data.get(position).reviewsNumber + "則評論）");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPicture;
        private final ProgressBar progressBar;
        private final TextView tvTitle;
        private final TextView tvAddress;
        private final TextView tvDistance;
        private final TextView tvEvaluate;

        public ViewHolder(@NonNull View itemView, TrainAdapter.onItemClickListener listener) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.img_picture);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            tvEvaluate = itemView.findViewById(R.id.tv_evaluate);

            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(TrainAdapter.onItemClickListener Listener) {
        mListener = Listener;
    }
}
