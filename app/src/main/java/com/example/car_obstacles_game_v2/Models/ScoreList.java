package com.example.car_obstacles_game_v2.Models;

import java.util.ArrayList;

public class ScoreList {

    private static final int MAX_SCORES = 10;
    private ArrayList<ScoreItem> scores = new ArrayList<>();

    public ArrayList<ScoreItem> getScores() {
        return scores;
    }

    public ScoreList setScores(ArrayList<ScoreItem> scores) {
        this.scores = scores;
        return this;
    }

    public void addScore(int score,double lat,double lon){
        if(scores.size() == MAX_SCORES &&
                score <= scores.get(0).getScore())
            return;

        ScoreItem newScore = new ScoreItem();
        newScore.setScore(score);
        newScore.setLat(lat);
        newScore.setLon(lon);
        scores.add(newScore);
        // Sort Here
    }
}
