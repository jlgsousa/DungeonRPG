package com.homemade.dungeondroid.entity;

import android.content.Intent;

import java.util.Random;

import static com.homemade.dungeondroid.constants.Constants.BATTLE;
import static com.homemade.dungeondroid.constants.Constants.LIFE;
import static com.homemade.dungeondroid.constants.Constants.NORMAL;

public class GameMap {

    private Integer[][] map;
    private int mapWidth;
    private int mapHeight;

    private GameMap() {}

    public GameMap(Integer[][] map) {
        this.map = map;
    }

    public void generateBattleSpots() {
        int nPositions = map.length * map[0].length;
        int nBattles = nPositions / 20;

        Random r = new Random();
        int row = 0;
        int column = 0;
        for (int i = 0; i < nBattles; i++) {
            while (map[row][column] != NORMAL) {
                row = r.nextInt(map.length);
                column = r.nextInt(map[0].length);
            }
            map[row][column] = BATTLE;
        }
    }

    public void generateLifeSpots() {
        int nLifes = 10;

        Random r = new Random();
        int row = 0;
        int column = 0;
        for (int i = 0; i < nLifes; i++){
            while (map[row][column] != NORMAL){
                row = r.nextInt(map.length);
                column = r.nextInt(map[0].length);
            }
            map[row][column] = LIFE;
        }
    }

    public Integer[][] getMap() {
        return map;
    }

    public void setMap(Integer[][] map) {
        this.map = map;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
}
