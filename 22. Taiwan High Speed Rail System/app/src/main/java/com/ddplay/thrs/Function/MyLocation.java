package com.ddplay.thrs.Function;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ddplay.thrs.Data.LatLng;

public class MyLocation {
    private final Context context;
    private float latitude = 0.0f;
    private float longitude = 0.0f;

    public MyLocation(Context context) {
        this.context = context;
    }

    // 尋找經緯度
    @SuppressLint("MissingPermission")
    public LatLng findLocation() {
        String defGPS;
        // defGPS = LocationManager.GPS_PROVIDER; //GPS定位
        defGPS = LocationManager.NETWORK_PROVIDER; //網路定位
        // LocationManager可以用來獲取當前的位置，追蹤設備的移動路線
        LocationManager locationManager = (LocationManager) context.getSystemService(AppCompatActivity.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(defGPS, 1000, 0f, locationListener);
        Location location = locationManager.getLastKnownLocation(defGPS);
        if (location != null) {
            latitude = (float) location.getLatitude();
            longitude = (float) location.getLongitude();
        }
        return new LatLng(latitude, longitude);
    }

    // location監聽
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            latitude = (float) location.getLatitude();
            longitude = (float) location.getLongitude();
        }
        // Provider的轉態在可用、暫時不可用和無服務三個狀態直接切換時觸發此函數
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }
        // Provider被enable時觸發此函數，比如GPS被打開
        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }
        // Provider被disable時觸發此函數，比如GPS被關閉
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    };
}
