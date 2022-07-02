package com.ddplay.ch10;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgFrame = findViewById(R.id.image_frame);
        ImageView imgTween = findViewById(R.id.image_tween);
        Button startFrame = findViewById(R.id.frame_start);
        Button stopFrame = findViewById(R.id.frame_stop);
        Button alphaTween = findViewById(R.id.tween_alpha);
        Button scaleTween = findViewById(R.id.tween_scale);
        Button transparentTween = findViewById(R.id.tween_translate);
        Button rotateTween = findViewById(R.id.tween_rotate);
        // 逐格動畫
        imgFrame.setBackgroundResource(R.drawable.image_animation);
        startFrame.setOnClickListener(view -> {
            AnimationDrawable anim = (AnimationDrawable) imgFrame.getBackground();
            anim.start();
        });
        stopFrame.setOnClickListener(view -> {
            AnimationDrawable anim = (AnimationDrawable) imgFrame.getBackground();
            anim.stop();
        });
        //捕間動畫
        alphaTween.setOnClickListener(view -> {
            AlphaAnimation anim = new AlphaAnimation(1.0f/*開始*/, 0.2f/*結束*/);
            anim.setDuration(1000);
            imgTween.startAnimation(anim);
        });
        rotateTween.setOnClickListener(view -> {
            RotateAnimation anim = new RotateAnimation(
                    // 以左上角為基點
                    0f, // 起始角
                    360f, // 終止角
                    // 加入XY座標
                    RotateAnimation.RELATIVE_TO_SELF, // 相對於自身X坐標
                    0.5f, // 0.5f表明是這個圖片的一半長度為x軸
                    RotateAnimation.RELATIVE_TO_SELF, // 相對於自身Y坐標
                    0.5f // 0.5f表明是這個圖片的一半長度為y軸
            );
            anim.setDuration(1000);
            imgTween.startAnimation(anim);
        });
        scaleTween.setOnClickListener(view -> {
            ScaleAnimation anim = new ScaleAnimation(
                    1.0f,
                    1.5f,
                    1.0f,
                    1.5f,
                    // 加入XY座標
                    RotateAnimation.RELATIVE_TO_SELF, // 相對於自身X坐標
                    0.5f, // 0.5f表明是這個圖片的一半長度為x軸
                    RotateAnimation.RELATIVE_TO_SELF, // 相對於自身Y坐標
                    0.5f // 0.5f表明是這個圖片的一半長度為y軸
            );
            anim.setDuration(1000);
            imgTween.startAnimation(anim);
        });
        transparentTween.setOnClickListener(view -> {
            TranslateAnimation anim = new TranslateAnimation(
                    // 往右上
                    0f, //X 起點
                    100f, //X 終點
                    0f, //Y 起點
                    -100f //Y 終點
            );
            anim.setDuration(1000);
            imgTween.startAnimation(anim);
        });
    }
}