package com.ddplay.mediaplayer.Function;

public class ConvertVideoID {
    public String convertVideoId(String videoURL) {
        String scheme = "https://";
        String host = "youtu.be/";
        String newUrl = videoURL.replace(scheme, "");
        newUrl = newUrl.replace(host, "");
        return newUrl;
    }
}
