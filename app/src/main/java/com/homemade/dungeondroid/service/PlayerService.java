package com.homemade.dungeondroid.service;


import com.homemade.dungeondroid.entity.Player;

import java.util.List;

public class PlayerService {
    private DBHandler dbHandler;

    public void init(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public boolean register(String username, String password) {
        for (Player gamer : dbHandler.getPlayers()) {
            if (username.equals(gamer.getUsername())) {
                return false;
            }
        }

        dbHandler.addPlayer(new Player(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        Player existingPlayer = dbHandler.getPlayer(username, password);
        return existingPlayer != null && !("").equals(existingPlayer.getUsername())
                && !("").equals(existingPlayer.getPassword());

    }

    public Player getPlayer(String username, String password) {
        return dbHandler.getPlayer(username, password);
    }

    public List<Player> getPlayers() {
        return dbHandler.getPlayers();
    }

    public void updatePlayer(int id, Player player) {
        dbHandler.updatePlayer(id, player);
    }
}
