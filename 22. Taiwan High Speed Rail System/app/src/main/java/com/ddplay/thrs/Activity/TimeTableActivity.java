package com.ddplay.thrs.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ddplay.thrs.API.THSR;
import com.ddplay.thrs.Adapter.TrainNoAdapter;
import com.ddplay.thrs.Data.ObjectTrainNumber;
import com.ddplay.thrs.Data.TrainNoItem;
import com.ddplay.thrs.Dialog.DialogLoading;
import com.ddplay.thrs.R;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("SetTextI18n")
public class TimeTableActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        findView();

        dialogLoading.startLoading();
        String trainNo = getIntent().getStringExtra("trainNo");
        String start = getIntent().getStringExtra("start");
        String end = getIntent().getStringExtra("end");
        tvTimeTable.setText(trainNo + "時刻表");
        getData(trainNo, start, end);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getData(String trainNo, String start, String end) {
        Request request = new THSR().API("TrainNumber", null, null, null, trainNo);
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
                ObjectTrainNumber[] object = new Gson().fromJson(json, ObjectTrainNumber[].class);
                List<TrainNoItem> data = new ArrayList<>();
                // 垂直顯示
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TimeTableActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                for (ObjectTrainNumber detail : object) {
                    for (ObjectTrainNumber.stopTimes stopTime : detail.StopTimes) {
                        data.add(new TrainNoItem(
                                stopTime.getStopSequence(),
                                stopTime.StationName.getZh_tw(),
                                stopTime.getDepartureTime()
                        ));
                    }
                }
                runOnUiThread(() -> {
                    recycleView.setLayoutManager(linearLayoutManager);
                    TrainNoAdapter adapter = new TrainNoAdapter(data, start, end);
                    recycleView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    dialogLoading.endLoading();
                    adapter.setOnItemClickListener(position -> {
                        // do...
                    });
                });
            }
        });
    }

    TextView tvTimeTable;
    RecyclerView recycleView;
    DialogLoading dialogLoading;
    private void findView() {
        tvTimeTable = findViewById(R.id.tv_time_table);
        recycleView = findViewById(R.id.recycleView);
        dialogLoading = new DialogLoading(this);
    }

    public void back(View view) {
        super.onBackPressed();
    }
}