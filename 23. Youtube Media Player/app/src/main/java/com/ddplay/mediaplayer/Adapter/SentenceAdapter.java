package com.ddplay.mediaplayer.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddplay.mediaplayer.Data.Sentence;
import com.ddplay.mediaplayer.R;

import java.util.List;

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.ViewHolder> {
    private final List<Sentence> data;
    private int item = 0;
    private VideoListAdapter.onItemClickListener mListener;

    public void tagItem(int position) {
        this.item = position;
    }

    public SentenceAdapter(List<Sentence> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SentenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sentence, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SentenceAdapter.ViewHolder holder, int position) {
        holder.tvSentence.setText(data.get(position).getContent());
        holder.tvPosition.setText(String.valueOf(data.get(position).getPosition()));
        if (position == item) holder.background.setBackgroundColor(Color.parseColor("#C0C0C0"));
        else holder.background.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSentence;
        private final TextView tvPosition;
        private final LinearLayout background;
        public ViewHolder(@NonNull View itemView, VideoListAdapter.onItemClickListener mListener) {
            super(itemView);
            tvSentence = itemView.findViewById(R.id.tv_sentence);
            tvPosition = itemView.findViewById(R.id.tv_position);
            background = itemView.findViewById(R.id.linearLayout);

            itemView.setOnClickListener(v -> mListener.onItemClick(getAdapterPosition()));
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(VideoListAdapter.onItemClickListener Listener) {
        mListener = Listener;
    }
}
