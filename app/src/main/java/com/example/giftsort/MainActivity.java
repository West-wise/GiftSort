package com.example.giftsort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    //Google Cloude Vision API 관련
    //private static final String CLOUD_VISION_API_KEY = "AIzaSyC_U2HCHSqVMJTv0LpEVuwsjnXNnWT82-k";
    //public static final String FILE_NAME = "temp.jpg";

    private long backBtnTime =0;

    //플로팅 애니메이션 변수들
    private boolean fabMain_status = false;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabAddImg;
    private FloatingActionButton fabSet;


    //리사이클러 뷰 변수들
    private ArrayList<ImgData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //플로팅 메뉴 변수 ID값
        fabMain = findViewById(R.id.fabMain);
        fabAddImg = findViewById(R.id.fabDelete);
        fabSet = findViewById(R.id.fabSet);

        //리사이클러 뷰 변수ID값
        //recyclerView = (RecyclerView) findViewById(R.id.rv);
        //linearLayoutManager  = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager((linearLayoutManager));

        //arrayList = new ArrayList<>();
        //mainAdapter = new MainAdapter(arrayList);
        //recyclerView.setAdapter(mainAdapter);

        //갤러리 접근 부분
        //권한 체크
        boolean hasCamPerm = checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean hasWritePerm = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!hasCamPerm || !hasWritePerm)  // 권한 없을 시  권한설정 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);





        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });
        // 사진추가 플로팅 버튼 클릭
        fabAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "사진 추가 버튼 클릭", Toast.LENGTH_SHORT).show();
                /*ImgData imgdata = new ImgData(R.mipmap.ic_launcher,"홍드로이드","리사이클러뷰");
                arrayList.add(imgdata);
                mainAdapter.notifyDataSetChanged();*/

                Intent intent =new Intent(getApplicationContext(), Add_Picture.class);
                startActivity(intent);


            }
        });

        // 설정 플로팅 버튼 클릭
        fabSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "설정 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });

    }




    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if(fabMain_status) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabAddImg, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabSet, "translationY", 0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.menu_icon);

        }else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabAddImg, "translationY", -150f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabSet, "translationY", -300f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.menu_icon_pressed2);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }
    //두번 눌러서 종료
    public void onBackPressed() {//두번눌러서 종료
        long currentTime = System.currentTimeMillis();
        long gapTime = currentTime - backBtnTime;
        if(0 <= gapTime && 2000>=gapTime) super.onBackPressed();
        else {
            backBtnTime = currentTime;
            Toast.makeText(this,"한번더 누르면 종료됩니다",Toast.LENGTH_SHORT).show();
        }
    }


    //사진 파일 가져오기


    /* private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }*/

    //이미지 세팅하기


}