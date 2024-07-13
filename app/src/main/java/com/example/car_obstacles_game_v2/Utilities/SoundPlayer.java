package com.example.car_obstacles_game_v2.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {

    private Context context;
    private Executor executor;
    private MediaPlayer mediaPlayer;

    public SoundPlayer(Context context){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playSound(int resId){

        if(mediaPlayer == null){
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context,resId);
                mediaPlayer.setVolume(1.0f,1.0f);
                mediaPlayer.start();
                mediaPlayer.setLooping(false);
            });
        }
        else
            mediaPlayer.start();
    }

    public void stopSound(){
        if(mediaPlayer != null){
            executor.execute(() -> {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }
}
