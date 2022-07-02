package com.ddplay.attractions_search.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.ddplay.attractions_search.Data.Gallery;
import com.ddplay.attractions_search.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private final List<Gallery> photo;
    private final LayoutInflater inflater;

    public SliderAdapter(Context context, List<Gallery> photo) {
        this.photo = photo;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    // 回傳 ViewPager 的子項目個數
    @Override
    public int getCount() {
        return photo.size();
    }
    // 用來判斷頁面的 View 和下面 instantiateItem 方法回傳的物件是否一樣
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }
    // 實例化 ViewPager 中相對應位置的頁面
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 創建頁面
        View view = inflater.inflate(R.layout.slider_image, container, false);
        ImageView imgPhoto = view.findViewById(R.id.img_photo);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        // 顯示圖片
        Picasso.get().load(photo.get(position).getPhoto()).into(imgPhoto, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        // 將view加進container
        container.addView(view);
        return view;
    }
    // 當子項目 View 被滑出預備範圍內（當前及前後各一個頁面）時會被移除
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
