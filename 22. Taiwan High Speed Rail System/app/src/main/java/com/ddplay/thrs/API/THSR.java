package com.ddplay.thrs.API;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.ddplay.thrs.Data.ObjectToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class THSR {
    private String accessToken = "";

    private String beforeDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
    private String afterDate = "";
    private Boolean isNextDay = true;

    private String httpUrl;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Request API(String msg, String OriginStationID, String DestinationStationID,
                       String TrainDate, String TrainNumber) {

        httpUrl = UrlBuild(msg, OriginStationID, DestinationStationID, TrainDate, TrainNumber);

        checkNextDay();

        if (accessToken.equals("") || isNextDay) {
            try {
                accessToken = postToken();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new Request.Builder()
                .url(httpUrl)
                .addHeader("authorization", "Bearer " + accessToken)
                .build();
    }

    private String UrlBuild(String msg, String OriginStationID, String DestinationStationID,
                            String TrainDate, String TrainNumber) {
        HttpUrl.Builder UriBuild = new HttpUrl.Builder()
                .scheme("https")
                .host("tdx.transportdata.tw")
                .addPathSegment("api")
                .addPathSegment("basic")
                .addPathSegment("v2")
                .addPathSegment("Rail")
                .addPathSegment("THSR");
        HttpUrl httpUrl;
        switch (msg) {
            case "Station":
                httpUrl = UriBuild.addPathSegment("Station")
                        .addQueryParameter("format","JSON").build();
                break;
            case "DailyTimetable":
                httpUrl = UriBuild.addPathSegment("DailyTimetable")
                        .addPathSegment("OD")
                        .addPathSegment(OriginStationID)
                        .addPathSegment("to")
                        .addPathSegment(DestinationStationID)
                        .addPathSegment(TrainDate)
                        .addQueryParameter("format", "JSON").build();
                break;
            case "TrainNumber":
                httpUrl = UriBuild.addPathSegment("DailyTimetable")
                        .addPathSegment("Today")
                        .addPathSegment("TrainNo")
                        .addPathSegment(TrainNumber)
                        .addQueryParameter("format", "JSON").build();
                break;
            default:
                httpUrl = UriBuild.build();
        }
        return httpUrl.toString();
    }

    private String grantType = "client_credentials"; // 固定使用"client_credentials"
    private String clientId = "-------------e5c-4198"; // Client Id
    private String clientSecret = "-----------------b8e3-6f6bc643bcf0"; // Client Secret

    @SuppressLint("StaticFieldLeak")
    private String postToken() throws InterruptedException {
        final String[] token = new String[1];
        String url = new HttpUrl.Builder()
                .scheme("https")
                .host("tdx.transportdata.tw")
                .addPathSegment("auth")
                .addPathSegment("realms")
                .addPathSegment("TDXConnect")
                .addPathSegment("protocol")
                .addPathSegment("openid-connect")
                .addPathSegment("token").toString();
        // Body
        FormBody formBody = new FormBody.Builder()
                .add("grant_type", grantType)
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .build();
        // Request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // POST
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                ObjectToken result = new Gson().fromJson(json, ObjectToken.class);
                token[0] = result.getAccess_token();
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        return token[0];
    }

    private void checkNextDay() {
        afterDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        if (afterDate.compareTo(beforeDate) > 0) {
            isNextDay = true;
            beforeDate = afterDate;
        } else isNextDay = false;
    }
}
