package com.ddplay.attractions_search.Data;

import java.io.Serializable;

public class DetailData implements Serializable {
    // data
    private String name;
    private float lat;
    private float lng;
    private String vicinity;
    private String photo;
    private int star;
    private String[] landscape;

    public DetailData(
            String name,
            float lat,
            float lng,
            String vicinity,
            String photo,
            int star,
            String[] landscape)
    {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.vicinity = vicinity;
        this.photo = photo;
        this.star = star;
        this.landscape = landscape;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
