package com.ddplay.ch20;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private float angle = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button btnPhoto = findViewById(R.id.btn_photo);
        Button btnRotate = findViewById(R.id.btn_rotate);

        btnPhoto.setOnClickListener(view -> mStartForResult.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));
        btnRotate.setOnClickListener(view -> {
            angle += 90f;
            imageView.setRotation(angle);
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable(); //取得 Bitmap
            Bitmap photo = bitmapDrawable.getBitmap();
            detector(photo);
        });
    }
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Bundle bundle = intent.getExtras();
                        Bitmap photo = (Bitmap) bundle.get("data");
                        imageView.setImageBitmap(photo);
                        detector(photo);
                    }
                }
            });
    private void detector(Bitmap bitmap) {
        try {
            Log.d("Detector", "開始辨識");
            // 取得辨識標籤
            ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
            // 建立 InputImage 物件
            InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
            // 匹配辨識標籤與圖像，並建立執行成功與失敗的監聽器
            labeler.process(inputImage)
                    .addOnSuccessListener(imageLabels -> {
                        // 取得辨識結果與準確度
                        List<String> result = new ArrayList<>();
                        for (ImageLabel label : imageLabels) {
                            String text = label.getText();
                            float confidence = label.getConfidence();
                            result.add(text + ", 準確度: " + confidence);
                        }
                        // 將結果顯示於 ListView
                        ListView listView = findViewById(R.id.listView);
                        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result));
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "辨識錯誤", Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}