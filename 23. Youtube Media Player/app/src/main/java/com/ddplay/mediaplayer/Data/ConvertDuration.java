package com.ddplay.mediaplayer.Data;

public class ConvertDuration {
    private String min;
    private String sec;

    public ConvertDuration(String min, String sec) {
        this.min = min;
        this.sec = sec;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}
