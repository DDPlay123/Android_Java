package com.ddplay.mediaplayer.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddplay.mediaplayer.Data.VideoDetail;
import com.ddplay.mediaplayer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {
    private final List<VideoDetail> data;
    private onItemClickListener mListener;

    public VideoListAdapter(List<VideoDetail> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(data.get(position).getThumbnails())
                .into(holder.imgVideo, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }
                });
        holder.tvTime.setText(data.get(position).getDuration().getMin() + ":" + data.get(position).getDuration().getSec());
        holder.tvLevel.setText("初級");
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvFavorite.setText("0");
        holder.tvViewer.setText(String.valueOf(data.get(position).getViewer()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgVideo;
        private final TextView tvTime;
        private final TextView tvLevel;
        private final TextView tvTitle;
        private final TextView tvFavorite;
        private final TextView tvViewer;
        private final ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView, onItemClickListener mListener) {
            super(itemView);
            imgVideo = itemView.findViewById(R.id.img_video);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvLevel = itemView.findViewById(R.id.tv_level);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvFavorite = itemView.findViewById(R.id.tv_favorite);
            tvViewer = itemView.findViewById(R.id.tv_viewer);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(v -> mListener.onItemClick(getAdapterPosition()));
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener Listener) {
        mListener = Listener;
    }
}
