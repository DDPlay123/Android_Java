package com.ddplay.thrs.Data;

public class ObjectDailyTimetable {
    public dailyTrainInfo DailyTrainInfo;
    public static class dailyTrainInfo {
        private String TrainNo = "";
        private String Direction = "";
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
    }
    public originStopTime OriginStopTime;
    public static class originStopTime {
        private String DepartureTime = "";
        public String getDepartureTime() {
            return DepartureTime;
        }
        public void setDepartureTime(String departureTime) {
            DepartureTime = departureTime;
        }
    }
    public destinationStopTime DestinationStopTime;
    public static class destinationStopTime {
        private String ArrivalTime;
        public String getArrivalTime() {
            return ArrivalTime;
        }
        public void setArrivalTime(String arrivalTime) {
            ArrivalTime = arrivalTime;
        }
    }
}
