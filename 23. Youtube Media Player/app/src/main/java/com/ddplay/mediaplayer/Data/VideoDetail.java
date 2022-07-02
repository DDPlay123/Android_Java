package com.ddplay.mediaplayer.Data;

import java.io.Serializable;

public class VideoDetail implements Serializable {
    private String thumbnails;
    private ConvertDuration duration;
    private String title;
    private int viewer;
    public VideoDetail(String thumbnails, ConvertDuration duration, String title, int viewer) {
        this.thumbnails = thumbnails;
        this.duration = duration;
        this.title = title;
        this.viewer = viewer;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public ConvertDuration getDuration() {
        return duration;
    }

    public void setDuration(ConvertDuration duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }
}