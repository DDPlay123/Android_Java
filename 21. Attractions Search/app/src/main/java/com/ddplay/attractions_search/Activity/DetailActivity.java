package com.ddplay.attractions_search.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.ddplay.attractions_search.Adapter.GalleryAdapter;
import com.ddplay.attractions_search.Data.DetailData;
import com.ddplay.attractions_search.Data.Gallery;
import com.ddplay.attractions_search.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private final int[] imgStar = {R.drawable.icon_star, R.drawable.icon_star, R.drawable.icon_star, R.drawable.icon_star, R.drawable.icon_star};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setToolbar();
        // 讀取資料
        List<DetailData> data = (List<DetailData>) getIntent().getSerializableExtra("Data");
        int N = getIntent().getIntExtra("Number", 8);
        // 定義元件
        ImageView imgPhoto = findViewById(R.id.img_photo);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView tvName = findViewById(R.id.tv_name);
        TextView tvAddress = findViewById(R.id.tv_address);
        GridView gridStar = findViewById(R.id.grid_star);
        TextView tvPhotos = findViewById(R.id.tv_photos);
        GridView gridPhotos = findViewById(R.id.grid_photos);
        // 大圖
        Picasso.get().load(data.get(N).getPhoto()).into(imgPhoto, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        // 名稱
        tvName.setText(data.get(N).getName());
        // 地址
        tvAddress.setText(data.get(N).getVicinity());
        // 星級
        List<Map<String, Integer>> star = new ArrayList<>();
        for (int i = 0; i < data.get(N).getStar(); i++) {
            HashMap<String, Integer> item = new HashMap<>();
            item.put("img", imgStar[i]);
            star.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, star, R.layout.grid_star, new String[]{"img"}, new int[]{R.id.img_star});
        gridStar.setNumColumns(5);
        gridStar.setAdapter(adapter);
        // 總圖片數
        tvPhotos.setText("景觀圖(" + data.get(N).getLandscape().length + ")");
        //圖片集
        List<Gallery> gallery = new ArrayList<>();
        for (int i = 0; i < data.get(N).getLandscape().length; i++) {
            String photo = data.get(N).getLandscape()[i];
            gallery.add(new Gallery(photo));
        }
        gridPhotos.setNumColumns(3);
        gridPhotos.setAdapter(new GalleryAdapter(this, R.layout.grid_gallery, gallery));
        gridPhotos.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(DetailActivity.this, BaseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Gallery", (Serializable) gallery);
            bundle.putInt("Count", data.get(N).getLandscape().length);
            bundle.putInt("Position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvTitle = toolbar.findViewById(R.id.toolbar_title);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tvTitle.setText("詳細資料");

        getSupportActionBar().setDisplayShowCustomEnabled(false);
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