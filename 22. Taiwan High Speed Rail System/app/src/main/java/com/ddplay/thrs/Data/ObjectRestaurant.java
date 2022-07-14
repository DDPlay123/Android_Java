package com.ddplay.thrs.Data;

public class ObjectRestaurant {
    public Results results;
    public static class Results {
        public Content[] content;
        public static class Content {
            public Double lat = 0.0;
            public Double lng = 0.0;
            public String name = "";
            public Double rating = 0.0;
            public String vicinity = "";
            public String photo = "";
            public int reviewsNumber = 0;
        }
    }
}
