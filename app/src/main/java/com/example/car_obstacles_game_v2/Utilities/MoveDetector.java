package com.example.car_obstacles_game_v2.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.telecom.Call;

import com.example.car_obstacles_game_v2.Interfaces.MovementCallback;

import javax.security.auth.callback.Callback;

public class MoveDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private MovementCallback callback;
    private int movementX;
    private int movementY;
    private long timestamp = 0l;

    public MoveDetector(Context context, MovementCallback callback){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callback = callback;
        initEventListner();
    }

    private void initEventListner() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                calculateMove(x,y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // PASS
            }
        };
    }

    private void calculateMove(float x, float y) {
        if(System.currentTimeMillis() - timestamp > 500){
            timestamp = System.currentTimeMillis();
            if(x > 3) movementX = -1;
            else if(x < -3) movementX = 1;
            if(callback != null)
                callback.moveX(movementX);
        }
    }


    public void start(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop(){
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}
