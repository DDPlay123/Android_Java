package com.ddplay.attractions_search.Data;

import java.io.Serializable;

public class Gallery implements Serializable {
    private String photo;

    public Gallery(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
