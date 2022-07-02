package com.ddplay.mediaplayer.Data;

public class ObjectData {
    public Result result;
    public static class Result {
        private int viewer = 0; // 觀看次數
        private String mainEditor = ""; // 主編名稱

        public VideoInfo videoInfo;
        public static class VideoInfo {
            private String videourl = ""; // 影片ID
            private String title = ""; // 標題
            private String thumbnails = ""; // 預覽圖連結
            private int duration = 0; // 影片長度

            public CaptionResult captionResult;
            public static class CaptionResult {
                public Results[] results; // 0->result[0] 為主字幕, result[1] 為輔字幕 空值為沒有輔助字幕
                public static class Results {
                    public Captions[] captions;
                    public static class Captions {
                        private int miniSecond = 0; // 時間(秒)
                        private String content = ""; // 內容

                        public PinyinList[] pinyinLists;

                        public static class PinyinList {
                            public PinyinSplit[] pinyinSplits;
                            public static class PinyinSplit {
                                private String text = ""; // 字元

                                public String getText() {
                                    return text;
                                }

                                public void setText(String text) {
                                    this.text = text;
                                }
                            }
                        }

                        public Ipa[] ipa;
                        public static class Ipa {
                            private String text = ""; // 字元

                            public String getText() {
                                return text;
                            }

                            public void setText(String text) {
                                this.text = text;
                            }
                        }

                        public int getMiniSecond() {
                            return miniSecond;
                        }

                        public void setMiniSecond(int miniSecond) {
                            this.miniSecond = miniSecond;
                        }

                        public String getContent() {
                            return content;
                        }

                        public void setContent(String content) {
                            this.content = content;
                        }
                    }
                }
            }

            public String getVideourl() {
                return videourl;
            }

            public void setVideourl(String videourl) {
                this.videourl = videourl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(String thumbnails) {
                this.thumbnails = thumbnails;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }
        }

        public int getViewer() {
            return viewer;
        }

        public void setViewer(int viewer) {
            this.viewer = viewer;
        }

        public String getMainEditor() {
            return mainEditor;
        }

        public void setMainEditor(String mainEditor) {
            this.mainEditor = mainEditor;
        }
    }
}
