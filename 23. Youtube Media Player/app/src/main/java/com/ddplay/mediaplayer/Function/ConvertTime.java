package com.ddplay.mediaplayer.Function;

import com.ddplay.mediaplayer.Data.ConvertDuration;

public class ConvertTime {
    // Converter duration to Time ex: 00:00
    public ConvertDuration convertTime(int duration) {
        int minutes = duration / 60;
        int seconds = duration % 60;
        String min;
        if (minutes == 0) min = "00";
        else min = String.valueOf(minutes);
        String sec;
        if (seconds == 0) sec = "00";
        else sec = String.valueOf(seconds);
        return new ConvertDuration(min, sec);
    }
}
