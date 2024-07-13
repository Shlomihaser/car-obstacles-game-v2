package com.example.car_obstacles_game_v2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.car_obstacles_game_v2.Adapters.ScoreAdapter;
import com.example.car_obstacles_game_v2.Interfaces.MovementCallback;
import com.example.car_obstacles_game_v2.Interfaces.ZoomCallback;
import com.example.car_obstacles_game_v2.Logic.RecordsDBManager;
import com.example.car_obstacles_game_v2.Models.ScoreItem;
import com.example.car_obstacles_game_v2.Models.ScoreList;
import com.example.car_obstacles_game_v2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class ListFragment extends Fragment {

    private RecyclerView records_LST_scores;
    private MaterialTextView list_LBL_title;
    private MaterialButton list_LBL_send;
    private ZoomCallback zoomCallback;
    public ListFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initViews();
        return v;
    }

    private void findViews(View view) {
        records_LST_scores = view.findViewById(R.id.records_LST_scores);
    }

    private void initViews() {
        ScoreList scoreList = RecordsDBManager.getInstance().loadScoreList();
        ScoreAdapter scoreAdapter = new ScoreAdapter(scoreList.getScores());
        scoreAdapter.setZoomCallback(zoomCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        records_LST_scores.setLayoutManager(linearLayoutManager);
        records_LST_scores.setAdapter(scoreAdapter);

    }

    public void setZoomCallback(ZoomCallback zoomCallback) {
        this.zoomCallback = zoomCallback;
    }
    private void itemClicked(double lat, double lon) {
        if(zoomCallback != null)
            zoomCallback.listItemClicked(lat,lon);
    }


}