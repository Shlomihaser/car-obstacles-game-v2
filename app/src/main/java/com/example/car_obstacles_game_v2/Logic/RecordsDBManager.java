package com.example.car_obstacles_game_v2.Logic;

import android.content.Context;
import android.util.Log;

import com.example.car_obstacles_game_v2.Models.ScoreList;
import com.example.car_obstacles_game_v2.Utilities.SharePreferencesManager;
import com.google.gson.Gson;

public class RecordsDBManager {
    private static volatile RecordsDBManager instance = null;
    private Gson gson;

    public RecordsDBManager(){
        gson = new Gson();
    }
    public static RecordsDBManager init (Context context){
        if(instance == null)
        {
            synchronized (RecordsDBManager.class){
                if(instance == null){
                    instance = new RecordsDBManager();
                }
            }
        }
        return getInstance();
    }
    public static RecordsDBManager getInstance(){
        return instance;
    }

    public ScoreList loadScoreList(){
        String scoreListAsJson = SharePreferencesManager.getInstance().getString("scoreList","");
        if(scoreListAsJson.isEmpty())
            return new ScoreList();
        Log.d("ScoreListRead",scoreListAsJson);
        return gson.fromJson(scoreListAsJson, ScoreList.class);
    }

    public void saveScoreList(int score,double lat,double lon){
        ScoreList scoreList = loadScoreList();
        scoreList.addScore(score,lat,lon);
        String scoreListAsJson = gson.toJson(scoreList);
        Log.d("ScoreListSave",scoreListAsJson);
        SharePreferencesManager.getInstance().putString("scoreList",scoreListAsJson);
    }


}
