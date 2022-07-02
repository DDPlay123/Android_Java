package com.ddplay.thrs.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ddplay.thrs.Data.TrainNoItem;
import com.ddplay.thrs.R;
import java.util.List;

@SuppressLint("SetTextI18n")
public class TrainNoAdapter extends RecyclerView.Adapter<TrainNoAdapter.ViewHolder> {
    private final List<TrainNoItem> data;
    private final String start;
    private final String end;
    private onItemClickListener mListener;
    public TrainNoAdapter(List<TrainNoItem> data, String start, String end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_table, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvStation.setText(data.get(position).getStopSequence() + " ." + data.get(position).getZh_tw() + "高鐵站");
        holder.tvStartTime.setText(data.get(position).getDepartureTime());
        if (start.equals(data.get(position).getZh_tw() + "高鐵站") || end.equals(data.get(position).getZh_tw() + "高鐵站")) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#EDCC39"));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvStation;
        private final TextView tvStartTime;
        private final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView, onItemClickListener listener) {
            super(itemView);
            tvStation = itemView.findViewById(R.id.tv_station);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            linearLayout = itemView.findViewById(R.id.linearLayout);

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
