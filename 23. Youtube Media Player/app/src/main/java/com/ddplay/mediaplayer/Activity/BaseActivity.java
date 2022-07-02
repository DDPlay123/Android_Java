package com.ddplay.mediaplayer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.ddplay.mediaplayer.Adapter.ViewPagerAdapter;
import com.ddplay.mediaplayer.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView navigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        // Define ViewPager2 and Adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2 = findViewById(R.id.baseFragment);
        viewPager2.setAdapter(adapter);
        // Listener ViewPager2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                // Listener Fragment slide to change BottomNavigationView
                navigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        // Define BottomNavigationView
        navigationView = findViewById(R.id.navigation);
        // Listener BottomNavigationView
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    viewPager2.setCurrentItem(0);
                    break;
                case R.id.navigation_community:
                    viewPager2.setCurrentItem(1);
                    break;
                case R.id.navigation_chat:
                    viewPager2.setCurrentItem(2);
                    break;
                case R.id.navigation_information:
                    viewPager2.setCurrentItem(3);
                    break;
            }
            return true;
        });
    }
}