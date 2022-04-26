package com.example.qrcodeapp.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.qrcodeapp.R;
import com.example.qrcodeapp.ShakeDetector;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QRCodeActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private ImageView qrCode;
    private String text, userId, time, qrValue, getNewCode;
    private Intent i;
    private TextView tvQRCode, backBtn, tvTimer, tvTimerReset;
    private File qrImgFile, file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);  //캡쳡방지

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTimer = findViewById(R.id.tv_timer);
        tvTimerReset = findViewById(R.id.tv_timer_reset);
        qrCode = findViewById(R.id.iv_qr_code);
//        text = "http://ierp.interpass.co.kr";
        i = getIntent();
        userId = i.getStringExtra("user_no");

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
        time = sdf.format(date);
        Log.d("current_time", time+"");

        qrValue = userId + time;  //ex:0001095209
        Log.d("qrValue", qrValue );
        text = qrValue;
        tvQRCode = findViewById(R.id.tv_qr_value);
        tvQRCode.setVisibility(View.VISIBLE);
        tvQRCode.setText("( "+qrValue+" )");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Generating QR Code
//        try {
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix matrix = qrCodeWriter.encode(qrValue, BarcodeFormat.QR_CODE, 200, 200);
//
//        }catch (Exception e){e.printStackTrace();}

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);

        }catch (Exception e){}



        //QR코드 이미지 저장
        try {
            Uri fileUri;
            String filePath;
            String fileName = "QR_" + qrValue + ".png";
            file = null;
            file = new File(Environment.getExternalStorageDirectory()+"QR_IERP");

            if (!file.exists()){
                file.mkdirs();
            }else {Log.d("file_existed", file+"");}

            qrImgFile = new File(file, fileName);
            filePath = qrImgFile.getAbsolutePath();
            Log.d("filePath", filePath.toString());      //  /storage/emulated/0QR_IERP/QR_000001042630.png
            Log.d("filePath_img", qrImgFile.toString()); //  /storage/emulated/0QR_IERP/QR_000001042630.png

        }catch (Exception e){e.printStackTrace();}



        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                //shake 감지할 때
                Log.d("onshake", count+"");

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrCode.setImageBitmap(bitmap);

                }catch (Exception e){}

                tvTimer.setVisibility(View.GONE);
                tvTimerReset.setVisibility(View.VISIBLE);

                //QR코드 아이디값
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
                time = sdf.format(date);
                Log.d("current_time", time+"");
                qrValue = userId + time;  //ex:0001095209
                text = qrValue;
                tvQRCode.setText("( "+qrValue+" )");

                //흔들 때 마다 시간 reset
                CountDownTimer timer2 = new CountDownTimer(15000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int num = (int) (millisUntilFinished / 1000);
                        if (num < 9){
                            tvTimerReset.setText(0 + Integer.toString(num + 1));
                        }else {
                            tvTimerReset.setText(Integer.toString(num + 1));
                        }
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(QRCodeActivity.this, "코드를 다시 생성해주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }.start();
            }
        });


        tvTimer = findViewById(R.id.tv_timer);

        CountDownTimer timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                if (num < 9){
                    tvTimer.setText(0 + Integer.toString(num + 1));
                }else {
                    tvTimer.setText(Integer.toString(num + 1));
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(QRCodeActivity.this, "코드를 다시 생성해주세요.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }.start();


    }//onCreate..

//    private class setCountDownTimer(int ) {
//        CountDownTimer timer = new CountDownTimer(15000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                int num = (int) (millisUntilFinished / 1000);
//                if (num < 9){
//                    tvTimer.setText(0 + Integer.toString(num + 1));
//                }else {
//                    tvTimer.setText(Integer.toString(num + 1));
//                }
//            }
//            @Override
//            public void onFinish() {
//                Toast.makeText(QRCodeActivity.this, "코드를 다시 생성해주세요.", Toast.LENGTH_SHORT).show();
//                onBackPressed();
//            }
//        }.start();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}//QRCodeActivity..