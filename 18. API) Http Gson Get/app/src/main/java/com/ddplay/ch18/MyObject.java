package com.ddplay.ch18;

import java.util.List;

public class MyObject {
    public Result results;
    public static class Result {
        public List<Record> content;
        public static class Record {
            private float lat = 0.0f;
            private float lng = 0.0f;
            private String name = "";

            public float getLat() {
                return lat;
            }

            public void setLat(float lat) {
                this.lat = lat;
            }

            public float getLng() {
                return lng;
            }

            public void setLng(float lng) {
                this.lng = lng;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
