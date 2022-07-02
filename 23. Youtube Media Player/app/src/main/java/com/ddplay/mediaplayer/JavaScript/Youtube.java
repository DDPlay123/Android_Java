package com.ddplay.mediaplayer.JavaScript;

public class Youtube {
    public String buildHTML(String youtubeID) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                    "<head>\n" +
                        " <meta charset=\"utf-8\"/>\n" +
                        "<style>\n" +
                            ".overlay {\n" +
                                 "position: fixed;\n" +
                                 "width: 100%;\n" +
                                 "height: 100%;\n" +
                                 "left: 0;\n" +
                                 "top: 0;\n" +
                                 "background: rgba(51,51,51,0.7);\n" +
                                 "z-index: 10;}\n" +
                        "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                        " <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->\n" +
                        "<div class=\"overlay\", id=\"player\"></div>\n" +
                        "<script>\n" +
                            "// 2. This code loads the IFrame Player API code asynchronously.\n" +
                            "var tag = document.createElement('script');\n" +
                            "tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                            "var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                            "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                            "// 3. This function creates an <iframe> (and YouTube player) after the API code downloads.\n" +
                            "var player;\n" +
                            "function onYouTubeIframeAPIReady() {\n" +
                                "player = new YT.Player('player', {\n" +
                                    "height: '100%',\n" +
                                    "width: '100%',\n" +
                                    "playerVars: {\n" +
                                        "autoplay: 1,// 自動播放影片\n" +
                                        "controls: 0,// 顯示播放器\n" +
                                        "showinfo: 0,// 隱藏影片標題\n" +
                                        "modestbranding: 1,// 隱藏YouTube Logo\n" +
                                        "fs: 0,// 隱藏全螢幕按鈕\n" +
                                        "rel: 0, //不顯示相關視頻\n" +
                                        "cc_load_policty: 0,// 隱藏字幕\n" +
                                        "iv_load_policy: 3,// 隱藏影片註解\n" +
                                        "autohide: 0,// 影片播放時，隱藏影片控制列\n" +
                                        "mute : 0//靜音\n" +
                                    "},\n" +
                                    "videoId: \"" + youtubeID + "\",\n" +
                                    "events: {\n" +
                                        "'onReady': onPlayerReady,\n" +
                                        "'onStateChange': onPlayerStateChange\n" +
                                    "}\n" +
                                "});\n" +
                            "}\n" +
                            "// 4. The API will call this function when the video player is ready.\n" +
                            "function onPlayerReady(event) {\n" +
                                "event.target.playVideo();\n" +
                            "}\n" +
                            "// 5. The API calls this function when the player's state changes.\n" +
                            "//    The function indicates that when playing a video (state=1),\n" +
                            "//    the player should play for six seconds and then stop.\n" +
                            "var refreshInterval;\n" +
                            "function onPlayerStateChange(event) {\n" +
                                "if (player.getPlayerState() === 0) {\n" +
                                    "android.playerIcon(2);\n" +
                                    "clearInterval(refreshInterval);\n" +
                                    "refreshInterval = null;\n" +
                                    "seekTo(0);\n" +
                                    "pauseVideo();\n" +
                                "}\n" +
                                "if (player.getPlayerState() === 1) {\n" +
                                    "android.playerIcon(1);\n" +
                                    "refreshInterval = setInterval('currentTime()', 500);\n" +
                                "}\n" +
                                "if (player.getPlayerState() === 2) {\n" +
                                    "android.playerIcon(0);\n" +
                                    "clearInterval(refreshInterval);\n" +
                                    "refreshInterval = null;\n" +
                                "}\n" +
                                "if (player.getPlayerState() === 3) {\n" +
                                    "android.playerIcon(0);\n" +
                                    "clearInterval(refreshInterval);\n" +
                                    "refreshInterval = null;\n" +
                                "}\n" +
                            "}\n" +
                            "function currentTime() {\n" +
                                "android.getCurrentTime(player.getCurrentTime());\n" +
                            "}\n" +
                            "function seekTo(time) {\n" +
                                "player.seekTo(time);\n" +
                            "}\n" +
                            "function stopVideo() {\n" +
                                "player.stopVideo();\n" +
                            "}\n" +
                            "function pauseVideo() {\n" +
                                "player.pauseVideo();\n" +
                            "}\n" +
                            "function playVideo() {\n" +
                                "android.getCurrentTime(player.getCurrentTime());\n" +
                            "player.playVideo();\n" +
                            "}\n" +
                        "</script>\n" +
                    "</body>\n" +
                "</html>\n";
    }
}
