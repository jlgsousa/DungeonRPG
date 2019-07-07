package com.homemade.dungeondroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.homemade.dungeondroid.service.DBHandler;
import com.homemade.dungeondroid.entity.Player;
import com.homemade.dungeondroid.service.PlayerService;

import java.util.ArrayList;
import java.util.List;

public class Stats extends AppCompatActivity {

    private PlayerService playerService;
    private ListView playerListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        playerService = new PlayerService();
        playerService.init(new DBHandler(this));

        playerListView = findViewById(R.id.list_todo);
        updateUI();

    }

    private void updateUI() {

        List<String> players = new ArrayList<>();

        for (Player player : playerService.getPlayers()){
            players.add(getString(R.string.name) + player.getUsername()
                    + getString(R.string.victories) + player.getVictories()
                    + getString(R.string.defeats) + player.getDefeats());
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.activity_stats, R.id.jogador_id, players);
            playerListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(players);
            mAdapter.notifyDataSetChanged();
        }
    }
}
