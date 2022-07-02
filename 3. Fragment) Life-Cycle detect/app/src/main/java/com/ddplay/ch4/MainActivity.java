package com.ddplay.ch4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("Main","onCreate");
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Main","onRestart");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Main","onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Main","onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Main","onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Main","onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Main","onDestroy");
    }
}