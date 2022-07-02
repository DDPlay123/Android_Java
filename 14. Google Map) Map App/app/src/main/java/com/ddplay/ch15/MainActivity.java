package com.ddplay.ch15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            // 精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // 顯示目前位置與目前位置的按鈕
            googleMap.setMyLocationEnabled(true);
            // 加入標記
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(25.033611, 121.565000));
            markerOptions.title("台北 101");
            markerOptions.draggable(true);
            googleMap.addMarker(markerOptions);
            markerOptions.position(new LatLng(25.047924, 121.517081));
            markerOptions.title("台北車站");
            markerOptions.draggable(true);
            googleMap.addMarker(markerOptions);
            // 繪製線段
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(new LatLng(25.033611, 121.565000));
            polylineOptions.add(new LatLng(25.032435, 121.534905));
            polylineOptions.add(new LatLng(25.047924, 121.517081));
            polylineOptions.color(Color.RED);
            Polyline polyline = googleMap.addPolyline(polylineOptions);
            polyline.setWidth(20f);
            // 初始化地圖中心點及size
            googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            new LatLng(25.035, 121.54), 13f)
            );
        }
    }
}