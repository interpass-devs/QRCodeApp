package com.example.qrcodeapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QRCodeActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private ImageView qrCode;
    private String userId, time, qrValue;
    private Intent i;
    private TextView tvQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        qrCode = findViewById(R.id.iv_qr_code);
        i = getIntent();
        userId = i.getStringExtra("userId");

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
        time = sdf.format(date);

        qrValue = userId + time;  //ex:0001095209
        Log.d("qrValue", qrValue );
        tvQRCode = findViewById(R.id.tv_qr_value);
        tvQRCode.setVisibility(View.VISIBLE);
        tvQRCode.setText("( "+qrValue+" )");

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                //감지시 할 작업 작성

            }
        });

    }//onCreate..


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

}//QRCodeActivity..