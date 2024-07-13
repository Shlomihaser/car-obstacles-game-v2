package com.example.car_obstacles_game_v2;

import android.app.Application;

import com.example.car_obstacles_game_v2.Logic.LocationManager;
import com.example.car_obstacles_game_v2.Logic.RecordsDBManager;
import com.example.car_obstacles_game_v2.Utilities.SharePreferencesManager;
import com.example.car_obstacles_game_v2.Utilities.SignalManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RecordsDBManager.init(this);
        SignalManager.init(this);
        SharePreferencesManager.init(this);
        LocationManager.init(this);
    }






}
