package com.ddplay.thrs.Data;

public class TrainNoItem {
    private String StopSequence;
    private String Zh_tw;
    private String DepartureTime;
    public TrainNoItem(String StopSequence, String Zh_tw, String DepartureTime) {
        this.StopSequence = StopSequence;
        this.Zh_tw = Zh_tw;
        this.DepartureTime = DepartureTime;
    }

    public String getStopSequence() {
        return StopSequence;
    }

    public void setStopSequence(String stopSequence) {
        StopSequence = stopSequence;
    }

    public String getZh_tw() {
        return Zh_tw;
    }

    public void setZh_tw(String zh_tw) {
        Zh_tw = zh_tw;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }
}
