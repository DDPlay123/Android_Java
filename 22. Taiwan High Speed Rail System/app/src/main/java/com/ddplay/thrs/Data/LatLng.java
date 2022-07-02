package com.ddplay.thrs.Data;

public class LatLng {
    private float Latitude;
    private float Longitude;

    public LatLng(float Latitude, float Longitude) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}
