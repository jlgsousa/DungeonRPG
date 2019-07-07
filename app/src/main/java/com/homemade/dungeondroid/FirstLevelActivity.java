
package com.homemade.dungeondroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homemade.dungeondroid.entity.GameEngine;
import com.homemade.dungeondroid.entity.GameMap;
import com.homemade.dungeondroid.entity.level.first.FirstEngine;
import com.homemade.dungeondroid.map.loader.MapLoader;
import com.homemade.dungeondroid.service.DBHandler;
import com.homemade.dungeondroid.entity.Player;
import com.homemade.dungeondroid.service.PlayerService;

import java.util.ArrayList;

import static com.homemade.dungeondroid.constants.Constants.BATTLE;
import static com.homemade.dungeondroid.constants.Constants.BOSS;
import static com.homemade.dungeondroid.constants.Constants.LIFE;
import static com.homemade.dungeondroid.constants.Constants.NORMAL;
import static com.homemade.dungeondroid.constants.Constants.PLAYER;
import static com.homemade.dungeondroid.constants.Constants.PLAYER_INFO;
import static com.homemade.dungeondroid.constants.Constants.POKEBALL;
import static com.homemade.dungeondroid.constants.Constants.STAIRS;


public class FirstLevelActivity extends AppCompatActivity {

        Integer[][] gameMapManual = new Integer[][]{
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,6,2,0,0,0,0,5,0,0,0,0,0,1,1,1},
            {1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,6,2,0,7,0,0,6,0,0,0,0,0,1,1,1},
            {1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,6,2,0,0,0,0,6,0,0,0,0,0,1,1,1},
            {1,3,0,0,0,0,5,4,4,4,4,4,4,4,8,5,3,4,4,8,4,5,0,0,1,1,0,1,1,1},
            {1,3,0,0,0,0,6,1,1,1,1,0,0,0,0,1,1,0,0,0,0,1,1,1,1,1,0,1,1,1},
            {1,3,0,100,0,0,6,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1},
            {1,3,4,4,4,4,5,0,0,0,1,0,0,0,0,0,0,1,1,0,0,1,1,0,1,1,0,1,1,1},
            {1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,1,8,1,1,1},
            {1,1,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,0,0,1,1,1,1,0,0,1,1,1},
            {1,1,0,0,2,1,0,0,0,0,0,0,0,1,1,1,1,0,1,1,0,0,1,0,0,0,0,0,1,1},
            {1,1,0,1,3,4,3,0,0,0,1,0,0,0,0,0,0,0,1,1,8,1,1,0,0,0,0,0,1,1},
            {1,1,0,0,0,1,2,0,0,0,1,0,1,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,1,1},
            {1,1,0,0,1,3,1,1,1,8,1,1,1,1,8,1,1,1,0,0,1,0,0,0,0,0,0,0,1,1},
            {1,2,5,0,0,1,1,0,0,0,0,1,1,0,0,0,1,1,0,0,1,0,0,1,1,1,1,1,1,1},
            {1,2,6,0,0,1,0,0,0,1,1,1,1,0,0,0,1,1,0,0,1,0,0,1,0,0,99,0,1,1},
            {1,2,6,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,0,0,1,0,0,1,0,0,0,0,1,1},
            {1,3,5,1,0,0,0,0,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,1,8,8,1,1,1,1},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1},
            {1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

    };

    private MediaPlayer mediaplayer;

    private TableLayout table;

    private GameMap gameMap;
    private GameEngine gameEngine;

    private int actualRow = 4;
    private int actualCol = 4;

    private ArrayList<Battle.Move> myMoves;
    private ArrayList<Battle.Move> machineMoves;

    private Player player;
    private PlayerService playerService;

    boolean hasPokeball;
    private int stairs = 0;

    private static final String TAG = "MyActivity";
    private static final int identifier = 0xAA;
    public final static String EXTRA_MESSAGE = "com.example.android.rpc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grelha);

        Intent intent = getIntent();

        player = loadPlayer(intent.getExtras());
//        gameMap = new GameMap(loadMap());
        gameMap = new GameMap(gameMapManual);
        gameMap.generateBattleSpots();
        gameMap.generateLifeSpots();

        gameEngine = new FirstEngine();

        hasPokeball = false;

        if (savedInstanceState != null){
            gameMap.setMap((Integer[][]) savedInstanceState.getSerializable("Matriz"));
            actualRow = savedInstanceState.getInt("Row");
            actualCol = savedInstanceState.getInt("Column");
            player.setLifes(savedInstanceState.getInt("Lifes"));

            Log.d(TAG,"Loaded position from instance state: "+ actualRow + " " + actualCol);
        } else {
            Log.d(TAG,"Full refresh/n");
            Log.d(TAG,"Actual position " + actualRow + " " + actualCol);
        }

        startMap(actualRow, actualCol);

        playerService = new PlayerService();
        playerService.init(new DBHandler(this));

        new UpdateTask().execute();
    }

    private Player loadPlayer(Bundle bundle) {
        if (bundle != null && bundle.getSerializable(PLAYER_INFO) instanceof Player) {
            return (Player) bundle.getSerializable(PLAYER_INFO);
        } else {
            Intent intent = new Intent(FirstLevelActivity.this, MainActivity.class);
            Toast.makeText(this, "Error while starting first level, try again later", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Matriz", gameMap.getMap());
        outState.putInt("Row", actualRow);
        outState.putInt("Column", actualCol);
        outState.putInt("Lifes", player.getLifes());
    }

    public void rumble(){
        Intent intent = new Intent(this, Battle.class);
        intent.putExtra("myMoves", myMoves);
        intent.putExtra("machineMoves", machineMoves);
        intent.putExtra(EXTRA_MESSAGE,"Normal");

        startActivityForResult(intent, identifier);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void bossRumble(){
        Intent intent = new Intent(this, Battle.class);
        intent.putExtra("myMoves", myMoves);
        intent.putExtra("machineMoves", machineMoves);
        intent.putExtra(EXTRA_MESSAGE,"Mewtwo");

        startActivityForResult(intent,identifier);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        myMoves = (ArrayList<Battle.Move>) data.getSerializableExtra("myMoves");
        machineMoves = (ArrayList<Battle.Move>) data.getSerializableExtra("machineMoves");

        if (data.hasExtra("defeats")) {
            player.decrementLifes();
        } else if (data.hasExtra("victories")){
            player.incrementLifes();
        } else if (data.hasExtra("VitorMew")){     //Victory against the boss
            actualCol -=1;
            End fragment = new End();
            getSupportFragmentManager().beginTransaction().replace(R.id.grid_layout,fragment).commit();
        }

        if (player.getLifes() <= 0) {
            GameOverFrag fragment = new GameOverFrag();
            getSupportFragmentManager().beginTransaction().replace(R.id.grid_layout,fragment).commit();
        } else{
            startMap(actualRow, actualCol);
        }

        new UpdateTask().execute();
    }

    private Integer[][] loadMap() {
        Uri mapUri = Uri.parse("android.resouce://"
                + this.getPackageName() + "/"
                + R.drawable.maplevel1);
        return new MapLoader().loadMap(this, R.drawable.maplevel1);
    }

    public void startMap(int row, int col) {
        gameMap.getMap()[row + 2][col + 2] = PLAYER;

        TextView lifesTextView = findViewById(R.id.nlife);
        lifesTextView.setText(String.valueOf(getPlayer().getLifes()));

        if (stairs == 1) {
            stairs = 2;
            gameMap.getMap()[actualRow + 2][actualCol + 2] = NORMAL;
        }
        else if (stairs == 2) {
            stairs = 0;
            gameMap.getMap()[actualRow + 2][actualCol + 2] = STAIRS;
        }
        else
            gameMap.getMap()[actualRow + 2][actualCol + 2] = NORMAL;

        table = findViewById(R.id.view_root);
        table.removeAllViews();
        table = gameEngine.loadPlayerView(this, gameMap, row, col, stairs, Player.Direction.UP);

        actualRow = row;
        actualCol = col;
    }

    public void refreshMap(int row, int col, Player.Direction direction) {
        Log.d(TAG,"Refreshing position: "+ actualRow + " " + actualCol);

        TextView lifesTextView = findViewById(R.id.nlife);
        lifesTextView.setText(String.valueOf(getPlayer().getLifes()));

        int currentPosition = gameMap.getMap()[row + 2][col + 2];
        if (currentPosition == POKEBALL){
            hasPokeball = true;

            Toast.makeText(this, getString(R.string.pokeball_discovered), Toast.LENGTH_SHORT).show();
        } else if (currentPosition == BATTLE){
            rumble();
        } else if(currentPosition == LIFE) {
            getPlayer().incrementLifes();
        } else if (currentPosition == STAIRS){
            stairs = 1;
        } else if (isBossBattle(gameMap.getMap()[actualRow + 1][actualCol + 2])) {
            askFightBoss();
        }

        table = findViewById(R.id.view_root);
        table.removeAllViews();
        table = gameEngine.loadPlayerView(this, gameMap, row, col, stairs, direction);

        actualRow = row;
        actualCol = col;
    }

    private boolean isBossBattle(int position) {
        return position == BOSS;
    }

    private void askFightBoss() {
        if (hasPokeball) {
            bossRumble();
        } else {
            Toast.makeText(this, getString(R.string.player_not_ready_yet), Toast.LENGTH_SHORT).show();
        }
    }

    public void moveUp(View view) {
        if (actualRow - 1 >= 0) {
            if (gameEngine.isValidMove(gameMap.getMap()[actualRow + 1][actualCol + 2])){
                refreshMap(actualRow - 1, actualCol, Player.Direction.UP);
            }
        }
    }

    public void moveDown(View view){
        if (actualRow + 1 < (gameMap.getMapHeight() - 4)){
            if (gameEngine.isValidMove(gameMap.getMap()[actualRow + 3][actualCol + 2])) {
                refreshMap(actualRow + 1, actualCol, Player.Direction.DOWN);
            }
        }
    }

    public void moveLeft(View view){
        if (actualCol - 1 >= 0){
            //Caso de parede
            if (gameEngine.isValidMove(gameMap.getMap()[actualRow + 2][actualCol + 1])){
                refreshMap(actualRow, actualCol - 1, Player.Direction.LEFT);
            }
        }
    }

    public void moveRight(View view){
        if (actualCol < (gameMap.getMapWidth() - 4)){
            //Caso de parede
            if (gameEngine.isValidMove(gameMap.getMap()[actualRow + 2][actualCol + 3])) {
                refreshMap(actualRow, actualCol + 1, Player.Direction.RIGHT);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaplayer.isPlaying()){
            mediaplayer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        gameEngine.playResource(this, mediaplayer, R.raw.cave);
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

    private Player getPlayer() {
        return player;
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            playerService.updatePlayer(player.getId(), getPlayer());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
