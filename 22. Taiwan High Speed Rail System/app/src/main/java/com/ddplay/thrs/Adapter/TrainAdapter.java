package com.ddplay.thrs.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddplay.thrs.Data.TrainItem;
import com.ddplay.thrs.R;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder> {
    private final List<TrainItem> data;
    private onItemClickListener mListener;
    public TrainAdapter(List<TrainItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainAdapter.ViewHolder holder, int position) {
        holder.tvTrainDirection.setText(data.get(position).getDirection());
        holder.tvTrainNumber.setText(data.get(position).getTrainNo());
        holder.tvStartTime.setText(data.get(position).getDepartureTime());
        holder.tvDuration.setText(data.get(position).getDuration());
        holder.tvArrivalTime.setText(data.get(position).getArrivalTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTrainDirection;
        private final TextView tvTrainNumber;
        private final TextView tvStartTime;
        private final TextView tvDuration;
        private final TextView tvArrivalTime;
        public ViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            tvTrainDirection = itemView.findViewById(R.id.tv_train_direction);
            tvTrainNumber = itemView.findViewById(R.id.tv_train_number);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvArrivalTime = itemView.findViewById(R.id.tv_arrival_time);

            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener Listener) {
        mListener = Listener;
    }
}
