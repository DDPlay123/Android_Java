package com.ddplay.mediaplayer.API;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Video {
    // https://api.italkutalk.com/api/video/detail
    // headers => Content-Type: application/json
    // body => {guestKey:"*****", videoID:"*****", mode:0 or 1}
    // mode: 0->不增加總瀏覽數(於詳細頁面重刷時), 1->增加總瀏覽數(從列表進入時) 使用兩個都可
    private static final String guestKey = "----------------a599ae4";
    private static final String videoID = "----------------0c2851a";

    public Request postData() {
        String url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.italkutalk.com")
                .addPathSegment("api")
                .addPathSegment("video")
                .addPathSegment("detail").toString();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String param = "{\"guestKey\":" + "\"" + guestKey + "\"," +
                "\"videoID\":" + "\"" + videoID + "\"," +
                "\"mode\":1}";
        RequestBody body = RequestBody.create(param, mediaType);
        return new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
    }
}
