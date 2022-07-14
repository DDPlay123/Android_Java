package com.ddplay.thrs.Data;

public class RestaurantItem {
    public String name;
    public String vicinity;
    public Double lat;
    public Double lng;
    public Float distance;
    public Double rating;
    public int reviewsNumber;
    public String photo;

    public RestaurantItem(String name, String vicinity, Double lat, Double lng, Float distance, Double rating, int reviewsNumber, String photo) {
        this.name = name;
        this.vicinity = vicinity;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.rating = rating;
        this.reviewsNumber = reviewsNumber;
        this.photo = photo;
    }
}
