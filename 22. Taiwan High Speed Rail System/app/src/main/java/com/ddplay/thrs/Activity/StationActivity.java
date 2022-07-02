package com.ddplay.thrs.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ddplay.thrs.API.THSR;
import com.ddplay.thrs.Adapter.TrainAdapter;
import com.ddplay.thrs.Data.ObjectDailyTimetable;
import com.ddplay.thrs.Data.TrainItem;
import com.ddplay.thrs.Dialog.DialogLoading;
import com.ddplay.thrs.Function.GetTime;
import com.ddplay.thrs.Function.StationID;
import com.ddplay.thrs.R;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StationActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        findView();

        dialogLoading.startLoading();
        String start = getIntent().getStringExtra("start");
        String end = getIntent().getStringExtra("end");
        tvStartStation.setText(start);
        tvEndStation.setText(end);
        String startId = new StationID().dict(start);
        String endId = new StationID().dict(end);
        String time = new GetTime().getToday();

        getData(startId, endId, time, start, end);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getData(String OriginStationID, String DestinationStationID,
                         String TrainDate, String Start, String End) {
        Request request = new THSR().API("DailyTimetable", OriginStationID, DestinationStationID, TrainDate, null);
        // GET Method
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                ObjectDailyTimetable[] object = new Gson().fromJson(json, ObjectDailyTimetable[].class);
                List<TrainItem> data = new ArrayList<>();
                // 垂直顯示
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StationActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                for (ObjectDailyTimetable detail : object) {
                    try {
                        if (new GetTime().compareTime(detail.OriginStopTime.getDepartureTime())) {
                            data.add(new TrainItem(
                                    // 車次
                                    detail.DailyTrainInfo.getTrainNo(),
                                    // 方向
                                    new StationID().dict(detail.DailyTrainInfo.getDirection()),
                                    // 出發時間
                                    detail.OriginStopTime.getDepartureTime(),
                                    // 出發時間
                                    new GetTime().diffTime(detail.OriginStopTime.getDepartureTime(), detail.DestinationStopTime.getArrivalTime()),
                                    // 到站時間
                                    detail.DestinationStopTime.getArrivalTime()
                                    )
                            );
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(() -> {
                        recycleView.setLayoutManager(linearLayoutManager);
                        TrainAdapter adapter = new TrainAdapter(data);
                        recycleView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        dialogLoading.endLoading();
                        adapter.setOnItemClickListener(position -> {
                            Bundle bundle = new Bundle();
                            bundle.putString("trainNo", data.get(position).getTrainNo());
                            bundle.putString("start", Start);
                            bundle.putString("end", End);
                            Intent intent = new Intent(StationActivity.this, TimeTableActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        });
                    });
                }
            }
        });
    }

    TextView tvStartStation;
    TextView tvEndStation;
    RecyclerView recycleView;
    DialogLoading dialogLoading;
    private void findView() {
        tvStartStation = findViewById(R.id.tv_start_station);
        tvEndStation = findViewById(R.id.tv_end_station);
        recycleView = findViewById(R.id.recycleView);
        dialogLoading = new DialogLoading(this);
    }

    public void back(View view) {
        super.onBackPressed();
    }
}