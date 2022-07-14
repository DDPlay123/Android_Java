package com.ddplay.thrs.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.ddplay.thrs.Adapter.RestaurantAdapter;
import com.ddplay.thrs.Adapter.TrainAdapter;
import com.ddplay.thrs.Data.ObjectRestaurant;
import com.ddplay.thrs.Data.RestaurantItem;
import com.ddplay.thrs.Dialog.DialogLoading;
import com.ddplay.thrs.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        findView();
        dialogLoading.startLoading();

        Double lat = getIntent().getDoubleExtra("lat", 0.0);
        Double lng = getIntent().getDoubleExtra("lng", 0.0);

        getData(lat, lng);
    }

    private void getData(Double lat, Double lng) {
        // URL
        String url = "https://api-dev.bluenet-ride.com/v2_0/lineBot/restaurant/get/";
        // Body
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String param = "{\"lastIndex\":-1," +
                       "\"count\":15," +
                       "\"type\":[7]," +
                       "\"lat\":"+lat+"," +
                       "\"lng\":"+lng+"," +
                       "\"range\":\"2000\"," + // 2 km
                       "\"money\":0}";         // Any
        RequestBody body = RequestBody.create(param, mediaType);
        // Request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        // Call OkHttp
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                ObjectRestaurant details = new Gson().fromJson(json, ObjectRestaurant.class);
                List<RestaurantItem> data = new ArrayList();
                // Vertical
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                for (ObjectRestaurant.Results.Content it : details.results.content) {
                    try {
                        float[] distance = new float[1];
                        Location.distanceBetween(lat, lng, it.lat, it.lng, distance);
                        data.add(new RestaurantItem(
                                it.name,
                                it.vicinity,
                                it.lat,
                                it.lng,
                                (distance[0]/1000),
                                it.rating,
                                it.reviewsNumber,
                                it.photo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(() -> {
                    recyclerView.setLayoutManager(linearLayoutManager);
                    RestaurantAdapter adapter = new RestaurantAdapter(data);
                    recyclerView.setAdapter(adapter);
                    dialogLoading.endLoading();
                    adapter.setOnItemClickListener(position -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + data.get(position).lat + "," + data.get(position).lng));
                        startActivity(intent);
                    });
                });
            }
        });
    }

    private RecyclerView recyclerView;
    private DialogLoading dialogLoading;

    private void findView() {
        recyclerView = findViewById(R.id.recycleView);
        dialogLoading = new DialogLoading(this);
    }

    public void back(View view) {
        super.onBackPressed();
    }
}