package com.ddplay.attractions_search.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.ddplay.attractions_search.Adapter.SliderAdapter;
import com.ddplay.attractions_search.Data.Gallery;
import com.ddplay.attractions_search.R;
import java.util.List;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);
        ViewPager viewPager = findViewById(R.id.viewPager);

        List<Gallery> Gallery = (List<Gallery>) getIntent().getSerializableExtra("Gallery");
        int Count = getIntent().getIntExtra("Count", 0);
        int Position = getIntent().getIntExtra("Position", 0);
        C = Count;

        setToolbar((Position + 1) + "/" + Count);

        PagerAdapter adapter = new SliderAdapter(this, Gallery);
        viewPager.setAdapter(adapter);
        // 起始位置
        viewPager.setCurrentItem(Position);
        // 監聽畫面滑動，以變換 actionbar
        viewPager.addOnPageChangeListener(listener);
    }
    // 監聽 ViewPager 頁面切換
    private int C;
    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 頁面滾動時觸發
            setToolbar((position + 1) + "/" + C);
        }

        @Override
        public void onPageSelected(int position) {
            // 當新的頁面被選擇時觸發
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // 頁面滑動狀態改變時觸發
        }
    };
    private void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText(title);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}