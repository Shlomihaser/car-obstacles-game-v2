package com.example.car_obstacles_game_v2.Activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.car_obstacles_game_v2.Fragments.ListFragment;
import com.example.car_obstacles_game_v2.Fragments.MapFragment;
import com.example.car_obstacles_game_v2.Interfaces.ZoomCallback;
import com.example.car_obstacles_game_v2.R;
import com.google.android.gms.maps.model.LatLng;

public class GameRecordsActivity extends AppCompatActivity {

    private FrameLayout records_FRAME_list;
    private FrameLayout records_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_records);

        initViews();
    }



    private void initViews() {
        listFragment = new ListFragment();
        listFragment.setZoomCallback(new ZoomCallback() {
            @Override
            public void listItemClicked(double lat, double lon) {
                mapFragment.mapControl(new LatLng(lat,lon));
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_list,listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_map,mapFragment).commit();
    }

}