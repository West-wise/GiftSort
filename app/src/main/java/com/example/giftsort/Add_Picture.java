package com.example.giftsort;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Add_Picture extends AppCompatActivity {


    private Button add_coupon , load_img;
    private ImageButton back_btn;
    private ImageView couponImg;
    public static final int GALLERY = 101;
    private static final int ADD_COUPON_CODE = 1;
    protected Uri Imgroot;
    private static final String CLOUD_VISION_API_KEY = "AIzaSyC_U2HCHSqVMJTv0LpEVuwsjnXNnWT82-k";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_picture);

        add_coupon = (Button)findViewById(R.id.add_coupon);
        back_btn = (ImageButton)findViewById(R.id.back_btn);
        load_img = (Button)findViewById(R.id.load_img);
        couponImg = (ImageView)findViewById(R.id.couponImg);




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        load_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, ADD_COUPON_CODE); // final int GALLERY = 101;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri;
        RequestOptions option1 = new RequestOptions().circleCrop();
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if(requestCode == ADD_COUPON_CODE){
                selectedImageUri = data.getData();
                Glide.with(getApplicationContext()).load(selectedImageUri).into(couponImg);
                Imgroot = selectedImageUri;
            }
            else{
                Toast.makeText(this, "코드 오류", Toast.LENGTH_SHORT).show();
            }
        }
        else Toast.makeText(this, "오류", Toast.LENGTH_SHORT).show();
    }




}
