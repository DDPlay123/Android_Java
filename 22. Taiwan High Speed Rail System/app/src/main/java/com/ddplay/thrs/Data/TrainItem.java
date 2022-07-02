package com.ddplay.thrs.Data;

public class TrainItem {
    private String TrainNo;
    private String Direction;
    private String DepartureTime;
    private String Duration;
    private String ArrivalTime;
    public TrainItem(String TrainNo, String Direction, String DepartureTime, String Duration, String ArrivalTime) {
        this.TrainNo = TrainNo;
        this.Direction = Direction;
        this.DepartureTime = DepartureTime;
        this.Duration = Duration;
        this.ArrivalTime = ArrivalTime;
    }

    public String getTrainNo() {
        return TrainNo;
    }

    public void setTrainNo(String trainNo) {
        TrainNo = trainNo;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }
}
