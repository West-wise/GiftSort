package com.example.giftsort;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class Add_Picture extends AppCompatActivity {


    private Button add_coupon , load_img;
    private Bitmap image =null;
    private ImageButton back_btn;
    private ImageView couponImg;

    private TextView expiration_period_et , brand_name_et , barcode_text ,Goodsname_et;
    public static final int GALLERY = 101;
    private static final int ADD_COUPON_CODE = 1;
    Uri selectedImageUri;
    private TessBaseAPI mTess; //Tess API reference
    String datapath = "";


    private static final String CLOUD_VISION_API_KEY = "AIzaSyC_U2HCHSqVMJTv0LpEVuwsjnXNnWT82-k";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_picture);

        add_coupon = (Button)findViewById(R.id.add_coupon);
        back_btn = (ImageButton)findViewById(R.id.back_btn);
        load_img = (Button)findViewById(R.id.load_img);
        couponImg = (ImageView)findViewById(R.id.couponImg);
        brand_name_et = (TextView)findViewById(R.id.brand_name_et);
        expiration_period_et = (TextView)findViewById(R.id.expiration_period_et);
        barcode_text = (TextView)findViewById(R.id.barcode_text);
        Goodsname_et = (TextView)findViewById(R.id.Goodsname_et);


        //언어파일 경로
        datapath = getFilesDir() + "/tesseract/";

        //트레이닝데이터가 카피되어 있는지 체크
        checkFile(new File(datapath + "tessdata/"),"kor");
        checkFile(new File(datapath + "tessdata/"),"eng");
        //Tesseract API 언어 세팅
        String lang = "kor+eng";
        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);

        //OCR 세팅
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

        add_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                startActivity(intent);
            }
        });

    }

    /***
     *  이미지에서 텍스트 읽기
     */
    public void processImage(View view) {
        String OCRresult = null;
        String goods ="" , market = "", deadline= "";
        int i=0;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        extractText(extract(OCRresult));
        //OCRTextView.setText(OCRresult);
    }


    //추출된 문장들에서 각 부분 부출
    public void extractText(String originalText){
        String extract = originalText , market="",deadline="";
        String[] array = originalText.split("\n");
        barcode_text.setText(array[1]);
        Goodsname_et.setText(array[0]);
        brand_name_et.setText(array[2].substring(4));
        expiration_period_et.setText(array[3].substring(5));
    }

    //필요한 부분만 추출
    public String extract(String original){
        int end =0 , cnt =0;
        String text = original;
        String text2;
        int lastIndex = original.length()-1;
        while(cnt<=5){
            lastIndex--;
            if(original.charAt(lastIndex)=='\n'){
                cnt++;
                if(cnt==2) end = lastIndex;
            }
        }
        text2 = text.substring(lastIndex+1,end);
        return text2;
    }

    /***
     *  언어 데이터 파일, 디바이스에 복사
     */
    private void copyFiles(String lang) {
        try {
            String filepath = datapath + "/tessdata/"+lang+".traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /***
     *  디바이스에 언어 데이터 파일 존재 유무 체크
     * @param dir
     */
    private void checkFile(File dir ,String lang) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang);
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            String datafilepath = datapath + "파일경로";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(lang);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //RequestOptions option1 = new RequestOptions().circleCrop();
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if(requestCode == ADD_COUPON_CODE){
                selectedImageUri = data.getData();
                Glide.with(getApplicationContext()).load(selectedImageUri).into(couponImg);

                try { //여기서 갤러리에서 불러온 이미지를 비트맵으로 전환
                    image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                couponImg.setImageBitmap(image);
            }
            else{
                Toast.makeText(this, "코드 오류", Toast.LENGTH_SHORT).show();
            }
        }
        else Toast.makeText(this, "오류", Toast.LENGTH_SHORT).show();
    }




}
