package com.ddplay.thrs.API;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class THSR {
    private static final String ptx_app_id = "-------------------bbafbcf16";
    private static final String ptx_app_key = "--------------------1SZb4";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Request API(String msg, String OriginStationID, String DestinationStationID,
                       String TrainDate, String TrainNumber) {
        String httpUrl = UrlBuild(msg, OriginStationID, DestinationStationID, TrainDate, TrainNumber);
        return new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization", authString())
                .addHeader("x-date", serverTime())
                .build();
    }

    private String UrlBuild(String msg, String OriginStationID, String DestinationStationID,
                            String TrainDate, String TrainNumber) {
        HttpUrl.Builder UriBuild = new HttpUrl.Builder()
                .scheme("https")
                .host("ptx.transportdata.tw")
                .addPathSegment("MOTC")
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
    /*------------------------------------------------------------*/
    // ptx api 有 HMAC機制
    // 以HMAC簽章驗證使用者的身份，用戶在請求API服務時，將APP Key
    // 與當下時間（格式請使用GMT時間）做HMAC-SHA1 運算後轉成Base64 格式，
    // 帶入signature屬性欄位，服務器端將驗證用戶請求時的header欄位(詳如第四點)，
    // 驗證使用者的身份及請求服務的時效性。
    // 需要加入HTTP Header設定
    /*------------------------------------------------------------*/
    // 金鑰
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String authString() {
        String signature = "";
        // get signature
        try {
            signature = signature("x-date: " + serverTime());
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "hmac username=\"" + ptx_app_id + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\""
                + signature + "\"";
    }
    // HMAC-SHA1 簽證機制
    // https://github.com/ptxmotc/Sample-code/tree/master/Java/src
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String signature(String xData) throws java.security.SignatureException {
        try {
            final Base64.Encoder encoder = Base64.getEncoder();
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(THSR.ptx_app_key.getBytes(StandardCharsets.UTF_8),"HmacSHA1");

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(xData.getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : "+ e.getMessage());
        }
    }
    // 當前時間
    private String serverTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(calendar.getTime());
    }
}
