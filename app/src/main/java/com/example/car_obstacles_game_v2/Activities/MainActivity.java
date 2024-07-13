package com.example.car_obstacles_game_v2.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.car_obstacles_game_v2.Interfaces.MovementCallback;
import com.example.car_obstacles_game_v2.Interfaces.UpdateLocationCallback;
import com.example.car_obstacles_game_v2.Logic.GameManager;
import com.example.car_obstacles_game_v2.Logic.LocationManager;
import com.example.car_obstacles_game_v2.Logic.RecordsDBManager;
import com.example.car_obstacles_game_v2.Models.ScoreList;
import com.example.car_obstacles_game_v2.R;
import com.example.car_obstacles_game_v2.Utilities.MoveDetector;
import com.example.car_obstacles_game_v2.Utilities.SignalManager;
import com.example.car_obstacles_game_v2.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_MODE = "";
    private static final int MOVE_LEFT = -1;
    private static final int MOVE_RIGHT = 1;

    private AppCompatImageView obstacles[][] = new AppCompatImageView[9][5];
    private AppCompatImageView hearts[] = new AppCompatImageView[3];
    private AppCompatImageView cars[] = new AppCompatImageView[5];
    private MaterialTextView main_Score;
    private ExtendedFloatingActionButton leftButton,rightButton;
    private GameManager gameManager;
    private Timer timer;
    private boolean TimerOn;
    private SoundPlayer crashSound;
    private MoveDetector moveDetector;
    private double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager();
        crashSound = new SoundPlayer(this);
        findViews();
        initViews();
        startGame();
    }

    private void findViews() {
        leftButton = findViewById(R.id.main_FAB_move_left);
        rightButton = findViewById(R.id.main_FAB_move_right);
        main_Score = findViewById(R.id.main_Score);
        int resId;
        for(int i = 0;i < cars.length ;i++){
            resId = getResources().getIdentifier("car" + i,"id",getPackageName());
            cars[i] = findViewById(resId);
        }

        for(int i = 0 ;i < hearts.length ;i++){
            resId = getResources().getIdentifier("main_IMG_heart" + i,"id",getPackageName());
            hearts[i] = findViewById(resId);
        }

        for(int i = 0 ; i < gameManager.getROWS() ;i++){
            for (int j = 0; j < gameManager.getCOLS(); j++){
                resId = getResources().getIdentifier("img" + i + j,"id",getPackageName());
                obstacles[i][j] = findViewById(resId);
            }
        }
    }

    private void initViews(){
        Intent preIntent = getIntent();
        int mode = preIntent.getIntExtra(KEY_MODE,0);
        if(mode == 1){
            leftButton.setVisibility(View.INVISIBLE);
            rightButton.setVisibility(View.INVISIBLE);
            moveDetector = new MoveDetector(this, new MovementCallback() {
                @Override
                public void moveX(int side) {
                    moveCar(side);
                }
            });
            moveDetector.start();

        } else {
            leftButton.setOnClickListener(v -> moveCar(MOVE_LEFT));
            rightButton.setOnClickListener(v -> moveCar(MOVE_RIGHT));
        }

        for(int i=0;i< cars.length;i++)
            cars[i].setVisibility(View.INVISIBLE);
        cars[2].setVisibility(View.VISIBLE);

        main_Score.setText("00");
    }

    private void startGame(){

        if(!TimerOn){
            timer = new Timer();
            TimerOn = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(()-> {
                        gameManager.updateObstacles();
                        updateUI();
                        checkCrashes();
                    });
                }
            },0L,700L);


        }


    }

    private void checkCrashes(){
        if(gameManager.isCrash()){
            crashSound.playSound(R.raw.carcrash);
            SignalManager.getInstance().toast("Collision");
            SignalManager.getInstance().vibrate(500);
            updateLife();
        } else if(gameManager.isCollectCoin()){
            gameManager.updateScore();
            main_Score.setText(String.valueOf(gameManager.getScore()));
            SignalManager.getInstance().toast("Collect+10");
        }
    }

    private void updateUI(){
        int val;
        for(int i = 0; i < gameManager.getROWS(); i++)
            for(int j = 0 ; j < gameManager.getCOLS();j++){
                val = gameManager.getObstaclesPos()[i][j];
                if(val == 1){
                    obstacles[i][j].setImageResource(R.drawable.cone);
                    obstacles[i][j].setVisibility(View.VISIBLE);
                }
                else if(val == 2){
                    obstacles[i][j].setImageResource(R.drawable.coin);
                    obstacles[i][j].setVisibility(View.VISIBLE);
                }
                else
                    obstacles[i][j].setVisibility(View.INVISIBLE);
            }

    }



    private void updateLife(){
        int life = gameManager.getLifeCount();

        if(life == 1) {
            stopTimer();
            RecordsDBManager.getInstance().saveScoreList(gameManager.getScore(),lat,lon);
            Intent recordsIntent = new Intent(this,GameRecordsActivity.class);
            startActivity(recordsIntent);
        } else {
            hearts[life-1].setVisibility(View.INVISIBLE);
            gameManager.updateLife();
        }
    }
    private void stopTimer(){
        if(timer != null){
            TimerOn = false;
            timer.cancel();

        }
    }
    private void moveCar(int side){
        int carPosition = gameManager.getCarPos();
        if( (side == MOVE_LEFT && carPosition == 0) || (side == MOVE_RIGHT && carPosition == 4))
            return;
        cars[carPosition].setVisibility(View.INVISIBLE);
        cars[carPosition + side].setVisibility(View.VISIBLE);
        gameManager.setCarPos(carPosition + side);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
        crashSound.stopSound();
        if(moveDetector != null)
            moveDetector.stop();
        LocationManager.getInstance().stopLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocationManager.getInstance().setUpdateLocationCallback(
                new UpdateLocationCallback() {
                    @Override
                    public void updateLocation(double lat, double lon) {
                        MainActivity.this.lat = lat;
                        MainActivity.this.lon = lon;
                    }
                }
        );

        LocationManager.getInstance().getDeviceLocation();
        crashSound = new SoundPlayer(this);
        startGame();
    }
}