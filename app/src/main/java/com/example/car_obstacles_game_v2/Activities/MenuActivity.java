package com.example.car_obstacles_game_v2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.car_obstacles_game_v2.Logic.LocationManager;
import com.example.car_obstacles_game_v2.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MenuActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton menu_BTN_start_game,
            menu_BTN_game_records, menu_BTN_buttons,menu_BTN_sensors;
    private int selectedMode = -1;

    public static final int BUTTONS_MODE = 0;
    public static final int SENSORS_MODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        LocationManager.getInstance().requestLocationPermission(this);
        findViews();
        initViews();

    }

    private void findViews()
    {
        menu_BTN_buttons = findViewById(R.id.menu_BTN_buttons);
        menu_BTN_sensors = findViewById(R.id.menu_BTN_sensors);
        menu_BTN_game_records = findViewById(R.id.menu_BTN_game_records);
        menu_BTN_start_game = findViewById(R.id.menu_BTN_start_game);
    }
    private void initViews()
    {
            menu_BTN_start_game.setOnClickListener((v) -> startGameActivity());
            menu_BTN_game_records.setOnClickListener((v) -> moveToGameRecordsActivity());
            menu_BTN_buttons.setOnClickListener((v) -> {
                selectedMode = BUTTONS_MODE;
                updateButtonColors(menu_BTN_buttons, menu_BTN_sensors);

            });
            menu_BTN_sensors.setOnClickListener((v) -> {
                selectedMode = SENSORS_MODE;
                updateButtonColors(menu_BTN_buttons, menu_BTN_sensors);
            });
    }

    private void updateButtonColors(ExtendedFloatingActionButton selectedButton, ExtendedFloatingActionButton unselectedButton) {
        Log.d("sadsa","ss");
        // Set the selected button's background color
        selectedButton.setBackgroundColor(getResources().getColor(R.color.darker_purple_button));
        // Set the unselected button's background color
        unselectedButton.setBackgroundColor(getResources().getColor(R.color.purple_button));
    }

    private void moveToGameRecordsActivity()
    {
       Intent recordsActivity = new Intent(this,GameRecordsActivity.class);
       startActivity(recordsActivity);
       finish();
    }

    private void startGameActivity()
    {
        if(selectedMode == -1) return;
        Intent gameIntent = new Intent(this,MainActivity.class);
        gameIntent.putExtra(MainActivity.KEY_MODE,selectedMode);
        startActivity(gameIntent);
        finish();
    }
}