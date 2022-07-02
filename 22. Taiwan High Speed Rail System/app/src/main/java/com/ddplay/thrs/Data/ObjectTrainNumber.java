package com.ddplay.thrs.Data;

public class ObjectTrainNumber {
    public stopTimes[] StopTimes;
    public static class stopTimes {
        private String StopSequence = "";
        private String DepartureTime = "";
        public stationName StationName;

        public String getStopSequence() {
            return StopSequence;
        }

        public void setStopSequence(String stopSequence) {
            StopSequence = stopSequence;
        }

        public String getDepartureTime() {
            return DepartureTime;
        }

        public void setDepartureTime(String departureTime) {
            DepartureTime = departureTime;
        }

        public static class stationName {
            private String Zh_tw = "";

            public String getZh_tw() {
                return Zh_tw;
            }

            public void setZh_tw(String zh_th) {
                Zh_tw = zh_th;
            }
        }
    }
}
