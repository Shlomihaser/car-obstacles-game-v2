package com.example.car_obstacles_game_v2.Logic;


import java.util.Arrays;
import java.util.Random;

public class GameManager {
    private final int ROWS = 9,COLS = 5;
    private int obstaclesPos[][] = new int[ROWS][COLS]; // 1 indicate there is obstacle in the cell
    private int carPos, lifeCount; // presents the car positon 0 - Left /1 - Middle /2 - Right
    private Random random;

    public GameManager() {
        random = new Random();
        lifeCount = 3;
        carPos = 2;
    }

    public void updateObstacles(){
        int randNum;
        randNum = random.nextInt(5);
        // Moving rows downwards
        for (int i = obstaclesPos.length - 1; i > 0; i--)
            for (int j = 0; j < obstaclesPos[i].length; j++)
                obstaclesPos[i][j] = obstaclesPos[i-1][j];

        // Setting the first row base on the random number
        Arrays.fill(obstaclesPos[0], 0);
        if(random.nextInt(2) == 1)
            obstaclesPos[0][randNum] = 1;
    }

    public boolean isCrash(){
        return obstaclesPos[7][carPos] == 1;
    }


    public int[][] getObstaclesPos() {
        return obstaclesPos;
    }


    public int getROWS() {
        return ROWS;
    }
    public int getCOLS() {return COLS;}

    public int getCarPos() {
        return carPos;
    }

    public GameManager setCarPos(int carPos) {
        this.carPos = carPos;
        return this;
    }

    public int getLifeCount() {
        return lifeCount;
    }

    public void updateLife() {
        lifeCount--;
    }

}