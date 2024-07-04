package com.example.car_obstacles_game_v2.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.car_obstacles_game_v2.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MenuActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton menu_BTN_start_game, menu_BTN_game_records, menu_BTN_buttons,menu_BTN_sensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        initViews();
    }



    private void findViews(){
        menu_BTN_buttons = findViewById(R.id.menu_BTN_buttons);
        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors);
        menu_BTN_game_records = findViewById(R.id.menu_BTN_game_records);
        menu_BTN_start_game = findViewById(R.id.menu_BTN_start_game);
    }
    private void initViews(){
            menu_BTN_start_game.setOnClickListener((v) -> startGameActivity());
            menu_BTN_game_records.setOnClickListener((v) -> moveToGameRecordsActivity());
         //   menu_BTN_sensors.setOnClickListener((v) -> moveToGameRecordsActivity());
         //   menu_BTN_buttons.setOnClickListener((v) -> moveToGameRecordsActivity());
    }

    private void moveToGameRecordsActivity() {
     //   Intent i = new Intent(this,GameRecordsActivity.class);

    }
    private void startGameActivity(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }




}