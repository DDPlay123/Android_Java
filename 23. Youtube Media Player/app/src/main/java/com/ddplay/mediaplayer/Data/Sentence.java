package com.ddplay.mediaplayer.Data;

import java.io.Serializable;

public class Sentence implements Serializable {
    private String content;
    private int time;
    private int position;
    private String videourl;
    private String mainEditor;
    public Sentence(String content, int time, int position, String videourl, String mainEditor) {
        this.content = content;
        this.time = time;
        this.position = position;
        this.videourl = videourl;
        this.mainEditor = mainEditor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getMainEditor() {
        return mainEditor;
    }

    public void setMainEditor(String mainEditor) {
        this.mainEditor = mainEditor;
    }
}
