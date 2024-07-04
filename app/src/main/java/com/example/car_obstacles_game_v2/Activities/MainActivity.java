package com.example.car_obstacles_game_v2.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.car_obstacles_game_v2.Logic.GameManager;
import com.example.car_obstacles_game_v2.R;
import com.example.car_obstacles_game_v2.Utilities.SignalManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView obstacles[][] = new AppCompatImageView[9][5];
    private AppCompatImageView hearts[] = new AppCompatImageView[3];
    private AppCompatImageView cars[] = new AppCompatImageView[5];
    private ExtendedFloatingActionButton leftButton,rightButton;
    private GameManager gameManager;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameManager = new GameManager();
        SignalManager.init(this);
        findViews();
        initViews();
        startGame();
    }

    private void findViews() {
        leftButton = findViewById(R.id.main_FAB_move_left);
        rightButton = findViewById(R.id.main_FAB_move_right);

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
        leftButton.setOnClickListener(v -> moveCar(-1));
        rightButton.setOnClickListener(v -> moveCar(1));

        for(int i=0;i< cars.length;i++)
            cars[i].setVisibility(View.INVISIBLE);
        cars[2].setVisibility(View.VISIBLE);
    }

    private void startGame(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()-> {
                    gameManager.updateObstacles();
                    updateUI();
                    checkCrash();
                });
            }
        },0L,500L);
    }

    private void checkCrash() {
        if(!gameManager.isCrash())
            return;

        SignalManager.getInstance().toast("Collision");
        SignalManager.getInstance().vibrate(500);
        updateLife();
    }

    private void updateUI(){
        for(int i = 0; i < gameManager.getROWS(); i++)
            for(int j = 0 ; j < gameManager.getCOLS();j++)
                obstacles[i][j].setVisibility(gameManager.getObstaclesPos()[i][j] == 1 ? View.VISIBLE : View.INVISIBLE);
    }



    private void updateLife(){
        int life = gameManager.getLifeCount();

        if(life == 1) {
            timer.cancel();
            timer = null;
        } else {
            hearts[life-1].setVisibility(View.INVISIBLE);
            gameManager.updateLife();
        }
    }

    private void moveCar(int side){
        int carPosition = gameManager.getCarPos();
        if( (side == -1 && carPosition == 0) || (side == 1 && carPosition == 4))
            return;
        cars[carPosition].setVisibility(View.INVISIBLE);
        cars[carPosition + side].setVisibility(View.VISIBLE);
        gameManager.setCarPos(carPosition + side);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startGame();
    }
}