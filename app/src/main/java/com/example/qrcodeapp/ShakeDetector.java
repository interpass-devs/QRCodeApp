package com.example.qrcodeapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeDetector implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7f;
    private static final int SHAKE_SLOP_TIME_MS = 500;
    private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;
    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }
    public interface OnShakeListener {
        public void onShake(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListener != null) {
            //x,y,z 축의 값
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d("sensor_val", "x: "+x+", y: "+y+", z: "+z);

            //중력 가속도값으로 나눈값
            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = x / SensorManager.GRAVITY_EARTH;
            float gZ = x / SensorManager.GRAVITY_EARTH;
            float gForce = (float) Math.sqrt(gX * gX * gY * gZ * gZ);
            Log.d("sensor_value_x", "gX: "+gX+", gY: "+gY+", gZ: "+gZ);

            //진동을 감지했을 때
            //gforce가 기준치 이상일 경우
            if ( gForce > SHAKE_THRESHOLD_GRAVITY ) {
                final long now = System.currentTimeMillis();

                if ( mShakeTimestamp + SHAKE_SLOP_TIME_MS > now ) {
                    return;
                }
                //3초 이상 걸렸을 때 reset
                if ( mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now ) {
                    mShakeCount = 0;
                }

                //업데이트
                mShakeTimestamp = now;
                mShakeCount++;

                Log.d("shake_timestamp", mShakeTimestamp+"");
                Log.d("shake_count", mShakeCount+"");

                //흔들렸을 때 행동설정
                mListener.onShake(mShakeCount);
            }


        }
    }


}
