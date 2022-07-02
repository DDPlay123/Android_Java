package com.ddplay.thrs.Data;

public class ObjectStation {
    public stationName StationName;
    public static class stationName {
        private String Zh_tw = "";
        public String getZh_tw() {
            return Zh_tw;
        }
        public void setZh_tw(String zh_tw) {
            Zh_tw = zh_tw;
        }
    }
    private String StationAddress = "";
    public String getStationAddress() {
        return StationAddress;
    }
    public void setStationAddress(String stationAddress) {
        StationAddress = stationAddress;
    }
    public stationPosition StationPosition;
    public static class stationPosition {
        private float PositionLat = 0.0f;
        private float PositionLon = 0.0f;
        public float getPositionLat() {
            return PositionLat;
        }
        public void setPositionLat(float positionLat) {
            PositionLat = positionLat;
        }
        public float getPositionLon() {
            return PositionLon;
        }
        public void setPositionLon(float positionLon) {
            PositionLon = positionLon;
        }
    }
}
