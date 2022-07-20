package com.ddplay.thrs.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.ddplay.thrs.API.THSR;
import com.ddplay.thrs.Data.ObjectStation;
import com.ddplay.thrs.Data.StationData;
import com.ddplay.thrs.Dialog.DialogLoading;
import com.ddplay.thrs.Dialog.DialogStation;
import com.ddplay.thrs.Dialog.DialogStationSearch;
import com.ddplay.thrs.Dialog.DialogWarn2;
import com.ddplay.thrs.Function.MyLocation;
import com.ddplay.thrs.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity  implements OnMapReadyCallback {
    DialogLoading dialogLoading = new DialogLoading(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 要求權限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        // 取得元件
        findView();
        // 載入地圖
        dialogLoading.startLoading();
        loadMap();
        // 起點
        edStart.setOnClickListener(v -> {
            DialogStation dialog = new DialogStation(this, data, edStart);
            dialog.show();
            // open keyboard
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        });
        // 終點
        edEnd.setOnClickListener(v -> {
            DialogStation dialog = new DialogStation(this, data, edEnd);
            dialog.show();
            // open keyboard
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        });
        // 起點 <--> 終點
        btnSwap.setOnClickListener(v -> {
            String temp = edStart.getText().toString();
            edStart.setText(edEnd.getText());
            edEnd.setText(temp);
        });
        // 站點搜尋
        btnStationSearch.setOnClickListener(v -> {
            DialogStationSearch dialog = new DialogStationSearch(this, data, myMap);
            dialog.show();
            // open keyboard
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        });
        // 路線規劃
        btnRouteSearch.setOnClickListener(v -> {
            if (edStart.getText() == "" || edEnd.getText() == "") {
                DialogWarn2 dialog = new DialogWarn2(this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setMsg("請選擇站點!!!")
                        .positive(Dialog::dismiss)
                        .close(Dialog::dismiss)
                        .show();
            } else if (edStart.getText() == edEnd.getText()) {
                DialogWarn2 dialog = new DialogWarn2(this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setMsg("站點重複!!!")
                        .positive(Dialog::dismiss)
                        .close(Dialog::dismiss)
                        .show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("start", edStart.getText().toString());
                bundle.putString("end", edEnd.getText().toString());
                Intent intent = new Intent(this, StationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            myMap = googleMap;
            // 取得資料
            getData();
            // 顯示目前位置與目前位置的按鈕
            myMap.setMyLocationEnabled(true);
            // 關閉原生定位鈕
            myMap.getUiSettings().setMyLocationButtonEnabled(false);
            // 關閉原生按鈕
            myMap.getUiSettings().setMapToolbarEnabled(false);
            // 當前位置
            btnMyLocation.setOnClickListener(v -> {
                float myLat = new MyLocation(this).findLocation().getLatitude();
                float myLng = new MyLocation(this).findLocation().getLongitude();
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLng), 15f));});
            // 初始化地圖中心點及size
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.805857418790836, 120.9195094280048), 7f));
            // Menu
            myMap.setOnMarkerClickListener(marker -> {
                if (!marker.isInfoWindowShown()) {
                    PopupMenu popupMenu = new PopupMenu(this, btnMyLocation);
                    popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                    popupMenu.getMenu().getItem(0).setTitle(marker.getTitle());
                    popupMenu.setOnMenuItemClickListener(item -> {
                        switch (item.getItemId()) {
                            case R.id.StationName:
                                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude), 20f));
                                break;
                            case R.id.SetOriginSite:
                                edStart.setText(marker.getTitle());
                                break;
                            case R.id.SetEndSite:
                                edEnd.setText(marker.getTitle());
                                break;
                            case R.id.NearRestaurant:
                                Bundle bundle = new Bundle();
                                bundle.putDouble("lat", marker.getPosition().latitude);
                                bundle.putDouble("lng", marker.getPosition().longitude);
                                Intent intent = new Intent(this, RestaurantActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case R.id.Cancel:
                                popupMenu.dismiss();
                                break;
                        }
                        return true;
                    });
                    popupMenu.show();
                }
                return true;
            });
        }
    }
    private TextView edStart;
    private TextView edEnd;
    private ImageButton btnSwap;
    private ImageButton btnMyLocation;
    private Button btnStationSearch;
    private Button btnRouteSearch;
    private void findView() {
        edStart = findViewById(R.id.ed_start_station);
        edEnd = findViewById(R.id.ed_end_station);
        btnSwap = findViewById(R.id.btn_swap);
        btnMyLocation = findViewById(R.id.btn_my_location);
        btnStationSearch = findViewById(R.id.btn_station_search);
        btnRouteSearch = findViewById(R.id.btn_route_search);
    }
    // 取得車站資料
    private final List<StationData> data = new ArrayList<>();
    private void getData() {
        Request request = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            request = THSR.getInstance().API("Station", null, null, null, null);
        }
        // GET Method
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.e("ERROR", "Data ERROR");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                ObjectStation[] object = new Gson().fromJson(json, ObjectStation[].class);
                PolylineOptions polylineOptions = new PolylineOptions();
                for (ObjectStation detail : object) {
                    data.add(new StationData(detail.StationName.getZh_tw() + "高鐵站",
                            detail.getStationAddress(), detail.StationPosition.getPositionLat(), detail.StationPosition.getPositionLon()));
                    runOnUiThread(()->{
                        // adder marker
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(new LatLng(detail.StationPosition.getPositionLat(), detail.StationPosition.getPositionLon()))
                                .title(detail.StationName.getZh_tw() + "高鐵站")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_thsr));
                        myMap.addMarker(markerOptions);
                        // adder polyline
                        polylineOptions
                                .add(new LatLng(detail.StationPosition.getPositionLat(), detail.StationPosition.getPositionLon()))
                                .color(Color.parseColor("#FF9E42"));
                        myMap.addPolyline(polylineOptions).setWidth(50f);
                        dialogLoading.endLoading();
                    });
                }
            }
        });
    }
    // 載入地圖
    private GoogleMap myMap;
    private void loadMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    // 要求權限
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0 && requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) finish();
            else loadMap();
        }
    }
}