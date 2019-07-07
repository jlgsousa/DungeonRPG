package com.homemade.dungeondroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homemade.dungeondroid.entity.GameEngine;
import com.homemade.dungeondroid.entity.level.first.FirstEngine;

import java.util.ArrayList;
import java.util.List;

public class Battle extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.homemade.dungeondroid";

    private ArrayList<Move> myMoves;
    private ArrayList<Move> machineMoves;
    private MediaPlayer mediaplayer;
    private Machine machine;
    private String opponent;
    private int victories;
    private int draws;
    private int defeats;
    private GameEngine gameEngine;

    AnimationDrawable manAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalha);

        //TODO: This should be flexible
        gameEngine = new FirstEngine();

        Intent intent = getIntent();
        myMoves = (ArrayList<Move>) intent.getSerializableExtra("myMoves");
        machineMoves = (ArrayList<Move>) intent.getSerializableExtra("machineMoves");
        mediaplayer = new MediaPlayer();
        machine = new Machine();

        opponent = intent.getStringExtra(Battle.EXTRA_MESSAGE);
        Log.d("TAG","opponent: " + opponent);
        loadUiBattle(opponent);

    }

    private void loadUiBattle(String opponent) {
        ImageView myHandView = findViewById(R.id.myHand);
        ImageView machineHandView = findViewById(R.id.machineHand);

        myHandView.setImageResource(R.drawable.mefight);

        if (opponent.equals("Mewtwo")){
            gameEngine.playBossSong(Battle.this, mediaplayer);

            machineHandView.setImageResource(R.drawable.rocket);

            manAnimation = (AnimationDrawable) machineHandView.getDrawable();
            manAnimation.start();
        } else {
            gameEngine.playBattleSong(Battle.this, mediaplayer);

            machineHandView.setImageResource(gameEngine.loadOpponentResourceId());
        }

        //HP
        ImageView hp1 = findViewById(R.id.machineHealthBar);
        ImageView hp2 = findViewById(R.id.myHealthBar);
        hp1.setImageResource(R.drawable.hp1);
        hp2.setImageResource(R.drawable.hp1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void rock(View view){
        ImageView imageView = findViewById(R.id.myHand);
        imageView.setImageResource(R.drawable.rock);
        play(Move.ROCK);
    }

    public void paper(View view){
        ImageView imageView = findViewById(R.id.myHand);
        imageView.setImageResource(R.drawable.paper);
        play(Move.PAPER);
    }

    public void scissors(View view){
        ImageView imageView = findViewById(R.id.myHand);
        imageView.setImageResource(R.drawable.scissors);
        play(Move.SCISSORS);
    }

    public void run(View view){
        TextView textView = findViewById(R.id.messageView);

        boolean runSuccessful = gameEngine.runFromBattle();

        if (runSuccessful){
            textView.setText(getString(R.string.got_away_safely));
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("myMoves", myMoves);
            intent.putExtra("machineMoves", machineMoves);

            setResult(RESULT_OK, intent);
            finish();
        } else {
            textView.setText(getString(R.string.opponent_blocked_escape));
            Button button = findViewById(R.id.run);
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void play(Move move){
        Move machineMove = machinePlay();

        loadMachineHandView(machineMove);

        Result outcome = machine.getResult(move, machineMove);
        processOutcome(outcome);

        myMoves.add(move);
        machineMoves.add(machineMove);

        TextView messageView = findViewById(R.id.messageView);
        messageView.setText(getString(R.string.battle_state, victories, draws, defeats));
    }

    private Move machinePlay() {
        if (machineMoves.size() < 5){
            return machine.randomMove();
        } else {
            return machine.antecipateByPairOfMoves(myMoves, machineMoves);
        }
    }

    private void loadMachineHandView(Move machineMove) {
        ImageView machineHandView = findViewById(R.id.machineHand);

        switch (machineMove){
            case ROCK:
                machineHandView.setImageResource(R.drawable.rockm);
                break;
            case PAPER:
                machineHandView.setImageResource(R.drawable.paperm);
                break;
            case SCISSORS:
                machineHandView.setImageResource(R.drawable.scissorsm);
        }
    }

    private void processOutcome(Result outcome) {
        ImageView myHealthView = findViewById(R.id.myHealthBar);
        if (outcome == Result.WIN){
            victories += 1;
            processVictories(myHealthView, victories);
        } else if (outcome == Result.DRAW){
            draws += 1;
        } else if (outcome == Result.LOST){
            defeats += 1;
            processDefeats(myHealthView, defeats);
        }
    }

    private void processVictories(ImageView myHealthView, int victories) {
        if (victories == 1) myHealthView.setImageResource(R.drawable.hp2);
        else if (victories == 2) myHealthView.setImageResource(R.drawable.hp3);
        else if (victories == 3) myHealthView.setImageResource(R.drawable.hp4);
        else if (victories > 3) {
            gameEngine.playWinningSong(this, mediaplayer);
            showWinningDialog();
            goBackAfterVictory();
        }
    }

    private void showWinningDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.won_dialog_title))
                .setNeutralButton(getString(R.string.confirm_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void goBackAfterVictory() {
        Toast.makeText(this, getString(R.string.congrats_win), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("myMoves", myMoves);
        intent.putExtra("machineMoves", machineMoves);
        if (!opponent.equals("Mewtwo")){
            intent.putExtra("victories","sim");
        } else {
            intent.putExtra("VitorMew","sim");
        }
        setResult(RESULT_OK,intent);
        finish();
    }

    private void processDefeats(ImageView myHealthView, int defeats) {
        if (defeats == 1) myHealthView.setImageResource(R.drawable.hp2);
        else if (defeats == 2) myHealthView.setImageResource(R.drawable.hp3);
        else if (defeats == 3) myHealthView.setImageResource(R.drawable.hp4);
        else if (defeats > 3) {
            showLosingDialog();
            gameEngine.playLosingSong(this, mediaplayer);
            goBackAfterDefeat();
        }
    }

    private void showLosingDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.lost_dialog_title)
                .setMessage(getString(R.string.lost_dialog_message))
                .setNeutralButton(getString(R.string.confirm_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void goBackAfterDefeat() {
        Toast.makeText(this, getString(R.string.congrats_lost), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("myMoves", myMoves);
        intent.putExtra("machineMoves", machineMoves);
        intent.putExtra("defeats","sim");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaplayer.isPlaying()){
            mediaplayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaplayer!=null && mediaplayer.isPlaying()){
            mediaplayer.stop();
            mediaplayer.release();
            mediaplayer = null;
        }
    }

    public enum Move {
        ROCK,
        PAPER,
        SCISSORS
    }

    public enum Result {
        WIN,
        DRAW,
        LOST
    }
}