package com.ddplay.thrs.Function;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class GetTime {
    // 當前時間
    public String getToday() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    // 比較時間
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean compareTime(String time) throws ParseException {
        // 當前時間
        LocalDateTime localDateTime = LocalDateTime.now();
        String localTime = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        // 轉換格式
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date startTime = format.parse(localTime);
        Date endTime = format.parse(time);
        assert endTime != null;
        assert startTime != null;
        long diff = endTime.getTime() - startTime.getTime();
        return diff > 0;
    }
    // 取時間差
    public String diffTime(String start, String end) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date startTime = format.parse(start);
        Date endTime = format.parse(end);
        assert endTime != null;
        assert startTime != null;
        long diff = endTime.getTime() - startTime.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        String time = "";
        if (hours > 0 && minutes > 0) time = hours + "小時" + minutes + "分鐘";
        else if (hours > 0) time = hours + "小時";
        else if (minutes > 0) time = minutes + "分鐘";

        return time;
    }
}
