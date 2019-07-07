package com.homemade.dungeondroid.entity;

import java.io.Serializable;

/**
 * Created by joaosousa on 21/12/16.
 */
public class Player implements Serializable {
    private int id;
    private String username;
    private String password;
    private int victories;
    private int defeats;
    private int lifes;

    private Player(){}

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.victories = 0;
        this.defeats = 0;
        this.lifes = 3;
    }

    public Player(int id, String username, String password, int victories, int defeats, int lifes) {
        this.id=id;
        this.username =username;
        this.password =password;
        this.victories = victories;
        this.defeats = defeats;
        this.lifes = lifes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getLifes() {
        return lifes;
    }

    public void incrementLifes() {
        lifes++;
    }

    public void decrementLifes() {
        lifes--;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
