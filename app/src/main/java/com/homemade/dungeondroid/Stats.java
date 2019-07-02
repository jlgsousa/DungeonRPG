package com.homemade.dungeondroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Stats extends AppCompatActivity {

    private DBHandler dbhandler;
    private ListView JogadorListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        dbhandler = new DBHandler(this);

        JogadorListView = (ListView) findViewById(R.id.list_todo);
        updateUI();

    }

    private void updateUI() {
        List<Player> JogadorList = dbhandler.getAllJogadores();
        ArrayList<String> Jogadores = new ArrayList<>();
        for (Player jogador : JogadorList){
            Jogadores.add("Nome: " + jogador.getUsername()  + " Vitorias: " + jogador.getVitorias() + " Derrotas: " + jogador.getDerrotas());
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.activity_stats, R.id.jogador_id, Jogadores);
            JogadorListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(Jogadores);
            mAdapter.notifyDataSetChanged();
        }
    }
}
