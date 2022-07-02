package com.ddplay.thrs.Data;

public class StationData {
    private String station;
    private String address;
    private float latitude;
    private float longitude;
    public StationData(String station, String address, float latitude, float longitude) {
        this.station = station;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
