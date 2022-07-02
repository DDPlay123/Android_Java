package com.ddplay.attractions_search.Data;

import java.util.ArrayList;
import java.util.List;

public class ObjectData {
    public Result results;
    public static class Result {
        public List<Record> content;
        public static class Record {
            private float lat = 0.0f;
            private float lng = 0.0f;
            private String name = "";
            private String vicinity = "";
            private String photo = "";
            private int star = 0;
            private String[] landscape = new String[10];

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

            public String getVicinity() {
                return vicinity;
            }

            public void setVicinity(String vicinity) {
                this.vicinity = vicinity;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getStar() {
                return star;
            }

            public void setStar(int star) {
                this.star = star;
            }

            public String[] getLandscape() {
                return landscape;
            }

            public void setLandscape(String[] landscape) {
                this.landscape = landscape;
            }
        }
    }
}
