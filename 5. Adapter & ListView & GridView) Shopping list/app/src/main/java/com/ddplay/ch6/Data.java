package com.ddplay.ch6;

public class Data {

    private int photo;
    private String name;
    private int price;

    public Data(int photo, String name, int price) {
        this.photo = photo;
        this.name = name;
        this.price = price;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
