package com.example.car_obstacles_game_v2.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import kotlinx.coroutines.Delay;

public class SignalManager {

    private static volatile SignalManager instance = null;
    private Context context;
    private static Vibrator vibrator;

    private SignalManager(Context context) {
        this.context = context;
    }
    public static SignalManager init(Context context){
        if(instance == null){
            synchronized (SignalManager.class){
                if(instance == null){
                    instance = new SignalManager(context);
                    vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                }
            }
        }
        return getInstance();
    }
    public static SignalManager getInstance() {
        return instance;
    }


    public void toast(String message){
        Toast
                .makeText(context, message, Toast.LENGTH_SHORT)
                .show();
    }

    public static void vibrate(long milliseconds){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds,VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(milliseconds);
        }
    }

}
