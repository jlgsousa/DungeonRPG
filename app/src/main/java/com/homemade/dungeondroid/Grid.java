
package com.homemade.dungeondroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.homemade.dungeondroid.constants.Constants.PLAYER_INFO;


public class Grid extends AppCompatActivity {

    MediaPlayer mediaplayer;
    DBHandler dbHandler;

    TableLayout table;

    int[][] game = new int[][]{
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
    private final int TABLE_WIDTH = game[0].length;
    private final int TABLE_HEIGHT = game.length;

    private int actualRow = 5;
    private int actualCol = 1;

    List<Integer> myMoves = new ArrayList<>();
    List<Integer> machineMoves = new ArrayList<>();

    private int id;
    private int victorys = 0;
    private int defeats = 0;
    private int lifes=3;

    boolean pokeball;
    private int stairs=0;

    private static final String TAG = "MyActivity";
    private static final int identifier = 0xAA;
    public final static String EXTRA_MESSAGE = "com.example.android.rpc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grelha);

        Intent intent = getIntent();
        int[] info = intent.getIntArrayExtra(PLAYER_INFO);
        id = info[0];
        victorys = info[1];
        defeats = info[2];

        pokeball = false;

        //Gerar 30 batalhas em sitios aleatorios (que possam ser alcancados)
        int linha = 0;
        int coluna = 0;
        Random r = new Random();
        for (int i = 0; i < 30; i++){
            while (game[linha][coluna] != 0){
                linha = r.nextInt(game.length);
                coluna = r.nextInt(game[0].length);
            }
            game[linha][coluna] = 98;
        }

        //Espalha aleatoriamente 10 vidas pelo jogo
        for (int i = 0; i < 10; i++){
            while (game[linha][coluna] != 0){
                linha = r.nextInt(game.length);
                coluna = r.nextInt(game[0].length);
            }
            game[linha][coluna] = 97;
        }

        if (savedInstanceState != null){
            game = (int[][]) savedInstanceState.getSerializable("Matriz");
            actualRow = savedInstanceState.getInt("Row");
            actualCol = savedInstanceState.getInt("Column");
            lifes= savedInstanceState.getInt("Lifes");
            Log.e(TAG," Row Col"+ actualRow + " " + actualCol);
//            RefreshMap(actualRow,actualCol,0);
        }else {
            Log.e(TAG,"Refresquei do zero");
            Log.e(TAG," Row Col"+ actualRow + " " + actualCol);

        }
        RefreshMap(actualRow, actualCol,0);

        dbHandler = new DBHandler(this);

        new UpdateTask().execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Matriz", game);
        outState.putInt("Row", actualRow);
        outState.putInt("Column", actualCol);
        outState.putInt("Lifes",lifes);
    }

    public void Rumble(){
        Intent intent = new Intent(this, Battle.class);
        intent.putIntegerArrayListExtra("myMoves", (ArrayList<Integer>) myMoves);
        intent.putIntegerArrayListExtra("machineMoves", (ArrayList<Integer>) machineMoves);
        intent.putExtra(EXTRA_MESSAGE,"Normal");

        startActivityForResult(intent,identifier);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void RumbleMewtwo(){
        Intent intent = new Intent(this, Battle.class);
        intent.putIntegerArrayListExtra("myMoves", (ArrayList<Integer>) myMoves);
        intent.putIntegerArrayListExtra("machineMoves", (ArrayList<Integer>) machineMoves);
        intent.putExtra(EXTRA_MESSAGE,"Mewtwo");

        startActivityForResult(intent,identifier);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        myMoves = data.getIntegerArrayListExtra("myMoves");
        machineMoves = data.getIntegerArrayListExtra("machineMoves");

        if (data.hasExtra("Derrotas")){
            defeats += 1;
            lifes--;
        }
        else if (data.hasExtra("Vitorias")){
            victorys += 1;
        }
        else if(data.hasExtra("VitorMew")){     //Vitoria contra o mewtwo
            actualCol -=1;
            End fragment = new End();
            getSupportFragmentManager().beginTransaction().replace(R.id.LayoutMeu,fragment).commit();
        }

        if(lifes==0){
            GameOverFrag fragment = new GameOverFrag();
            getSupportFragmentManager().beginTransaction().replace(R.id.LayoutMeu,fragment).commit();
        }
        else{
            RefreshMap(actualRow, actualCol,0);
        }

        new UpdateTask().execute();
    }

    public void RefreshMap(int row, int col, int direction){
        Log.e(TAG," Row Col"+ actualRow + " " + actualCol);
        //Pokeball ou batalha random
        if (game[row + 2][col + 2] == 7){
            pokeball = true;

            Toast.makeText(this,"You have won the power of the Gods!", Toast.LENGTH_SHORT).show();
        } else if(game[row + 2][col + 2] == 98){
            Rumble();
        }

        //Apanha vida
        if(game[row + 2][col + 2] == 97)
            lifes++;

        //check for stairs
        if (game[row + 2][col + 2] == 8){
            stairs=1;
        }

        //Actualiza o nr de vidas
        TextView vidas=(TextView) findViewById(R.id.nlife);
        vidas.setText(" "+lifes);

        //Caso inicial
        if (direction != 0){
            //Substituir a posicao do jogador
            game[row + 2][col + 2] = 100;
            //Posicao onde estava o jogador passa a 0
            if(stairs==1){
                stairs=2;
                game[actualRow + 2][actualCol + 2] = 0;
            }
            else if(stairs==2){
                stairs=0;
                game[actualRow + 2][actualCol + 2] = 8;
            }
            else
                game[actualRow + 2][actualCol + 2] = 0;
        }

        table = (TableLayout) findViewById(R.id.view_root);
        table.removeAllViews();

        // Populate the table with stuff
        for (int y = 0; y < 5; y++) {
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < 5; x++) {
                ImageView b = new ImageView(this);

                int valor = game[row + y][col + x];

                if (valor == 1 ){
                    b.setBackgroundResource(R.drawable.grass);
                    b.setImageResource(R.drawable.wall);

                }else if (valor == 100){
                    if(stairs==0)
                        b.setBackgroundResource(R.drawable.grass);
                    else
                        b.setBackgroundResource(R.drawable.stairs);
                    if (direction == 1 || direction == 0){
                        b.setImageResource(R.drawable.mu);
                    }else if (direction == 2){
                        b.setImageResource(R.drawable.ml);
                    }else if (direction == 3){
                        b.setImageResource(R.drawable.mr);
                    }else if (direction == 4){
                        b.setImageResource(R.drawable.md);
                    }
                }
                else if (valor == 2){
                    b.setImageResource(R.drawable.topleft);
                }
                else if (valor == 3){
                    b.setImageResource(R.drawable.left);
                }
                else if (valor == 4){
                    b.setImageResource(R.drawable.front);
                }
                else if (valor == 5){
                    b.setImageResource(R.drawable.bottomright);
                }
                else if (valor == 6){
                    b.setImageResource(R.drawable.right);
                }
                else if (valor == 7){
                    b.setBackgroundResource(R.drawable.grass);
                    b.setImageResource(R.drawable.pokeball);
                }
                else if (valor == 8){
                    b.setImageResource(R.drawable.stairs);
                }
                else if (valor == 99){
                    b.setBackgroundResource(R.drawable.grass);
                    b.setImageResource(R.drawable.mewtwo);
                }
                else if (valor == 0 || valor == 98){
                    b.setImageResource(R.drawable.grass);
                }
                else if(valor==97){
                    b.setBackgroundResource(R.drawable.grass);
                    b.setImageResource(R.drawable.getlife);
                }
                r.addView(b);
            }
        }

        actualRow = row;
        actualCol = col;

        //Se estiver de frente para o mewtwo ha luta!
        if ((game[actualRow + 1][actualCol + 2] == 99 && pokeball)){
            RumbleMewtwo();
        }else if ((game[actualRow + 1][actualCol + 2] == 99 && !pokeball)) {
            Toast.makeText(this,"Mewtwo: You don´ have what it takes to fight me\nCome back when you have at least one pokeball", Toast.LENGTH_SHORT).show();
        }
    }

    public void Up(View view){
        if (actualRow - 1 >= 0){
            //Caso de parede
            if (game[actualRow + 1][actualCol + 2] == 0 || game[actualRow + 1][actualCol + 2] == 8 || game[actualRow + 1][actualCol + 2] == 7 || game[actualRow + 1][actualCol + 2] == 98 || game[actualRow + 1][actualCol + 2] == 97){
                RefreshMap(actualRow - 1, actualCol, 1);
            }
        }
    }

    public void Down(View view){
        if (actualRow + 1 < (TABLE_HEIGHT - 4)){
            //Caso de parede
            if (game[actualRow + 3][actualCol + 2] == 0 || game[actualRow + 3][actualCol + 2] == 8 || game[actualRow + 3][actualCol + 2] == 7 || game[actualRow + 3][actualCol + 2] == 98 || game[actualRow + 3][actualCol + 2] == 97) {
                RefreshMap(actualRow + 1, actualCol, 4);
            }
        }
    }

    public void Left(View view){
        if (actualCol - 1 >= 0){
            //Caso de parede
            if (game[actualRow + 2][actualCol + 1] == 0 || game[actualRow + 2][actualCol + 1] == 8 || game[actualRow + 2][actualCol + 1] == 7 || game[actualRow + 2][actualCol + 1] == 98 || game[actualRow + 2][actualCol + 1] == 97){
                RefreshMap(actualRow, actualCol - 1, 2);
            }
        }
    }

    public void Right(View view){
        if (actualCol < (TABLE_WIDTH - 4)){
            //Caso de parede
            if (game[actualRow + 2][actualCol + 3] == 0 || game[actualRow + 2][actualCol + 3] == 8 || game[actualRow + 2][actualCol + 3] == 7 || game[actualRow + 2][actualCol + 3] == 98 || game[actualRow + 2][actualCol + 3] == 97) {
                RefreshMap(actualRow, actualCol + 1, 3);
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

        Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cave);
        mediaplayer = MediaPlayer.create(Grid.this,path);
        mediaplayer.start();

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

    private class UpdateTask extends AsyncTask<Void, Void, Void> {
        private Exception e=null;

        //Ir atualizando o jogador
        @Override
        protected Void doInBackground(Void... voids) {
            dbHandler.updateJogador(id, victorys, defeats);

            Player jogador = dbHandler.getJogador(id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
