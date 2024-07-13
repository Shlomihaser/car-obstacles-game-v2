package com.example.car_obstacles_game_v2.Models;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.car_obstacles_game_v2.R;

public class ScoreItem
{
    private int score;
    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public ScoreItem setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public ScoreItem setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public int getScore() {
        return score;
    }
    public ScoreItem setScore(int score) {
        this.score = score;
        return this;
    }

}