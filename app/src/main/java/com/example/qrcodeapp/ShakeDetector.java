package com.example.qrcodeapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

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
            //축의 값
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //중력 가속도갑스로 나눈값
            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = x / SensorManager.GRAVITY_EARTH;
            float gZ = x / SensorManager.GRAVITY_EARTH;
            float gForce = (float) Math.sqrt(gX * gX * gY * gZ * gZ);

            if ( gForce > SHAKE_THRESHOLD_GRAVITY ) {
                final long now = System.currentTimeMillis();

                if ( mShakeTimestamp + SHAKE_SLOP_TIME_MS > now ) {
                    return;
                }

                if ( mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now ) {
                    mShakeCount = 0;
                }

                //업데이트
                mShakeTimestamp = now;
                mShakeCount++;

                //흔들렸을 때 행동설정
                mListener.onShake(mShakeCount);
            }


        }
    }


}
