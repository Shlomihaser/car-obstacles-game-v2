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
    private int selectedMode = -1;

    public static final int BUTTONS_MODE = 0;
    public static final int SENSORS_MODE = 1;

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
            menu_BTN_buttons.setOnClickListener((v) -> selectedMode = BUTTONS_MODE);
            menu_BTN_sensors.setOnClickListener((v) -> selectedMode = SENSORS_MODE);
    }

    private void moveToGameRecordsActivity() {
       Intent recordsActivity = new Intent(this,GameRecordsActivity.class);
       startActivity(recordsActivity);
       finish();
    }
    private void startGameActivity(){
        if(selectedMode == -1)
            return;
        Intent gameIntent = new Intent(this,MainActivity.class);
        gameIntent.putExtra(MainActivity.KEY_MODE,selectedMode);
        startActivity(gameIntent);
        finish();
    }
}