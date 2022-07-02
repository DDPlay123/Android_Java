package com.ddplay.mediaplayer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ddplay.mediaplayer.Adapter.SentenceAdapter;
import com.ddplay.mediaplayer.Adapter.VideoListAdapter;
import com.ddplay.mediaplayer.Data.Sentence;
import com.ddplay.mediaplayer.Function.ConvertVideoID;
import com.ddplay.mediaplayer.JavaScript.Youtube;
import com.ddplay.mediaplayer.R;
import java.util.List;
import java.util.Objects;

public class VideoActivity extends AppCompatActivity {
    public List<Sentence> sentences;
    private SentenceAdapter adapter;

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        findView();
        // Get Sentence
        sentences = (List<Sentence>) getIntent().getSerializableExtra("sentence");
        // List data
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SentenceAdapter(sentences);
        tvEditor.setText("主編輯:" + sentences.get(0).getMainEditor());
        recyclerView.setAdapter(adapter);
        runOnUiThread(() -> {});
        adapter.setOnItemClickListener(position -> {
            scrollTo(recyclerView, position);
            webView.loadUrl("javascript:seekTo(" + sentences.get(position).getTime() + ")");
        });
        adapter.notifyDataSetChanged();
        // Video
        setWebView();
        touchViewEvent(false);
        String youtubeId = new ConvertVideoID().convertVideoId(sentences.get(0).getVideourl());
        iFrameUrl(youtubeId);
        btnYoutube.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(sentences.get(0).getVideourl()));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        });
        touchView.setOnClickListener(v -> playStop());
        btnPlayer.setOnClickListener(v -> playStop());
    }

    // Scroll recyclerview function
    @SuppressLint("NotifyDataSetChanged")
    private void scrollTo(RecyclerView recyclerView, int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }
        };
        runOnUiThread(() -> {
            smoothScroller.setTargetPosition(position);
            Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
            adapter.tagItem(position);
            adapter.notifyDataSetChanged();
        });
    }

    // WebView setting
    @SuppressLint({"ClickableViewAccessibility", "SetJavaScriptEnabled", "JavascriptInterface"})
    private void setWebView() {
        String USERAGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) " +
                           "AppleWebKit/537.36 (KHTML, like Gecko) " +
                           "Chrome/77.0.3865.90 Safari/537.36";
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setUserAgentString(USERAGENT); //Important to auto play video
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setOnTouchListener((v, event) -> true);
        webView.addJavascriptInterface(new JsObject(), "android");
    }

    // Setting iFrame Youtube URL
    private void iFrameUrl(String youtubeId) {
        String VideoEmbeddedAddress = new Youtube().buildHTML(youtubeId);
        String mimeType = "text/html";
        String encoding = "UTF-8";
        webView.loadUrl(VideoEmbeddedAddress);
        webView.loadDataWithBaseURL("", VideoEmbeddedAddress, mimeType, encoding, "");
    }
    // Play and Stop Video
    int btnState = 0;
    private void playStop() {
        switch (btnState) {
            case 0:
            case 2:
                webView.loadUrl("javascript:playVideo()");
                touchViewEvent(false);
                break;
            case 1:
                webView.loadUrl("javascript:pauseVideo()");
                touchViewEvent(true);
                break;
        }
    }

    // TouchView show and hide
    public void touchViewEvent(Boolean show) {
        if (!(show)) {
            touchViewBackground.setBackgroundColor(Color.parseColor("#00000000"));
            touchViewIcon.setVisibility(View.INVISIBLE);
        } else {
            touchViewBackground.setBackgroundColor(Color.parseColor("#A0000000"));
            touchViewIcon.setVisibility(View.VISIBLE);
        }
    }

    // Javascript Interface
    class JsObject {
        // Change btnPlayer Icon
        @JavascriptInterface
        public void playerIcon(int state) {
            runOnUiThread(() -> {
                btnState = state;
                switch (state) {
                    case 0:
                        btnPlayer.setBackgroundResource(R.drawable.img_player);
                        break;
                    case 1:
                        btnPlayer.setBackgroundResource(R.drawable.img_pause);
                        break;
                    case 2:
                        btnPlayer.setBackgroundResource(R.drawable.img_player);
                        touchViewEvent(true);
                        break;
                }
            });
        }
        // Get CurrentTime and Scroll recyclerview
        @JavascriptInterface
        public void getCurrentTime(int time) {
            runOnUiThread(() -> {
                for (int i = 0; i < sentences.size(); i++) {
                    if (time == sentences.get(i).getTime()) {
                        scrollTo(recyclerView, i);
                    }
                }
            });
        }
    }

    private WebView webView;
    private ImageButton btnPlayer;
    private ImageButton btnYoutube;
    private TextView tvEditor;
    private RecyclerView recyclerView;
    private FrameLayout touchView;
    private View touchViewBackground;
    private View touchViewIcon;
    private void findView() {
        webView = findViewById(R.id.webView);
        btnPlayer = findViewById(R.id.btn_player);
        btnYoutube = findViewById(R.id.btn_youtube);
        tvEditor = findViewById(R.id.tv_editor);
        recyclerView = findViewById(R.id.recyclerview);
        touchView = findViewById(R.id.touchView);
        touchViewBackground = findViewById(R.id.touchView_background);
        touchViewIcon = findViewById(R.id.touchView_icon);
    }

    public void back(View view) {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        webView.clearHistory();
        ((ViewGroup) webView.getParent()).removeView(webView);
        webView.destroy();
        super.onDestroy();
    }
}