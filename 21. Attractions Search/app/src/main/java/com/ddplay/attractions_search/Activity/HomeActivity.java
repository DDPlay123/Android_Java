package com.ddplay.attractions_search.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.ddplay.attractions_search.Adapter.RecordAdapter;
import com.ddplay.attractions_search.Adapter.SearchAdapter;
import com.ddplay.attractions_search.Data.DetailData;
import com.ddplay.attractions_search.Data.ObjectData;
import com.ddplay.attractions_search.Data.RecordData;
import com.ddplay.attractions_search.Dialog.DialogInfo;
import com.ddplay.attractions_search.R;
import com.ddplay.attractions_search.SQLite.Database;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    private SQLiteDatabase database;
    private static final String httpURL = "https://android-quiz-29a4c.web.app/";
    EditText edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        edSearch = findViewById(R.id.edit_search);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnRecord = findViewById(R.id.btn_record);
        ImageView btnDelete = findViewById(R.id.btn_delete);

        // 取得資料庫
        database = new Database(this, "History", null, 1).getWritableDatabase();
        // 要求權限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        // 載入地圖
        loadMap();
        // 搜尋功能
        btnSearch.setOnClickListener(view -> {
            search();
            // 關閉鍵盤
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        });
        edSearch.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_SEARCH) search();
            return false;
        });
        btnDelete.setOnClickListener(view -> edSearch.setText(""));
        // 搜尋紀錄
        btnRecord.setOnClickListener(view -> {
            List<RecordData> data = new ArrayList<>();
            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery("SELECT name, vicinity FROM history", null);
            cursor.moveToFirst(); // 從第一筆開始輸出
            for (int i = cursor.getCount(); i > 0; i --) {
                data.add(new RecordData(cursor.getString(0), cursor.getString(1)));
                cursor.moveToNext();
            }
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "無歷史紀錄", Toast.LENGTH_SHORT).show();
            } else {
                // 建立 AlertDialog
                AlertDialog.Builder dialogRecord = new AlertDialog.Builder(this);
                View rowList = getLayoutInflater().inflate(R.layout.dialog_record, null);
                dialogRecord.setView(rowList);
                // 使用 dialog_record 的 ListView
                ListView listView = rowList.findViewById(R.id.list_record);
                RecordAdapter adapter = new RecordAdapter(this, R.layout.item_record, data);
                listView.setAdapter(adapter);
                // 彈出 Dialog 畫面
                adapter.notifyDataSetChanged();
                AlertDialog dialog = dialogRecord.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                // 如清空按鈕被點選，刪除所有資料
                rowList.findViewById(R.id.btn_delete).setOnClickListener(v -> {
                    database.execSQL("DELETE FROM history");
                    Toast.makeText(this, "已清除紀錄", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            // 精確定位包含粗略定位，因此只要求精確定位權限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getData();
            myMap = googleMap;
            // 顯示目前位置與目前位置的按鈕
            myMap.setMyLocationEnabled(true);
            // 初始化地圖中心點及size
            myMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            new LatLng(23.805857418790836, 120.9195094280048), 7f)
            );
            // Marker 點選
            myMap.setOnMarkerClickListener(marker -> {
                if (!marker.isInfoWindowShown()) {
                    DialogInfo dialog = new DialogInfo(this);
                    dialog.setMsg(marker.getTitle())
                            .setInfo(new DialogInfo.IOBtnInfo() {
                                @Override
                                public void btnInfo(DialogInfo dialog) {
                                    int N = 0;
                                    for (int i = 0; i < details.size(); i++) {
                                        if (Objects.requireNonNull(marker.getTitle()).contains(details.get(i).getName())) N = i;
                                    }
                                    Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Data", (Serializable) details);
                                    bundle.putInt("Number", N);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            })
                            .setGps(new DialogInfo.IOBtnGps() {
                                @Override
                                public void btnGps(DialogInfo dialog) {
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                            Uri.parse("http://maps.google.com/maps?daddr=" + marker.getPosition().latitude + "," + marker.getPosition().longitude));
                                    startActivity(intent);
                                }
                            });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
                return true;
            });
        }
    }
    // 搜尋功能
    private void search() {
        if (edSearch.length() < 1) {
            Toast.makeText(this, "查無結果", Toast.LENGTH_SHORT).show();
        } else {
            List<DetailData> data = new ArrayList<>();
            for (DetailData items : details) {
                if (items.getName().contains(edSearch.getText().toString()) ||
                    items.getVicinity().contains(edSearch.getText().toString())) {
                    data.add(items);
                }
            }
            if (data.isEmpty())Toast.makeText(this, "查無結果", Toast.LENGTH_SHORT).show();
            else {
                // 建立 AlertDialog
                AlertDialog.Builder dialogSearch = new AlertDialog.Builder(this);
                View rowList = getLayoutInflater().inflate(R.layout.dialog_search, null);
                dialogSearch.setView(rowList);
                // 使用 dialog_search 的 ListView
                ListView listView = rowList.findViewById(R.id.list_search);
                SearchAdapter adapter = new SearchAdapter(this, R.layout.item_record, data);
                listView.setAdapter(adapter);
                // 彈出 Dialog 畫面
                adapter.notifyDataSetChanged();
                AlertDialog dialog = dialogSearch.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    // 儲存該筆資料到Database
                    database.execSQL("INSERT INTO history(name, vicinity) VALUES(?,?)", new String[] {data.get(position).getName(), data.get(position).getVicinity()});
                    myMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(data.get(position).getLat(), data.get(position).getLng()), 13f)
                    );
                    dialog.dismiss();
                });
            }
        }
    }
    // 讀取: https://android-quiz-29a4c.web.app/
    private final List<DetailData> details = new ArrayList<>();
    private void getData() {
        Request request = new Request.Builder()
                .url(httpURL)
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = Objects.requireNonNull(response.body()).string();
                ObjectData objectData = new Gson().fromJson(json, ObjectData.class);
                for (ObjectData.Result.Record data : objectData.results.content) {
                    details.add(new DetailData(data.getName(), data.getLat(), data.getLng(), data.getVicinity(), data.getPhoto(), data.getStar(), data.getLandscape()));
                }
                runOnUiThread(() -> {
                    for (int i = 0; i < details.size(); i++) {
                        myMap.addMarker(new MarkerOptions()
                                .position(new LatLng(details.get(i).getLat(), details.get(i).getLng()))
                                .title(details.get(i).getName()));
                    }
                });
            }
        });
    }
    // 載入地圖
    private GoogleMap myMap;
    private void loadMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }
    // 要求權限
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish();
            } else {
                loadMap();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close(); // 關閉資料庫
    }
}