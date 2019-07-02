package com.homemade.dungeondroid;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Battle extends AppCompatActivity {

    MediaPlayer mediaplayer = new MediaPlayer();
    String opponent = " ";
    private static final String TAG = "MyActivity";

    ArrayList<Integer> JogadasMinhas = new ArrayList<>();
    ArrayList<Integer> JogadasMaquina = new ArrayList<>();
    int Vitorias = 0;
    int Empates = 0;
    int Derrotas = 0;

    Machine machine = new Machine();

    public final static String EXTRA_MESSAGE = "com.example.android.rpc";

    AnimationDrawable manAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalha);

        Intent intent = getIntent();
        JogadasMinhas = intent.getIntegerArrayListExtra("myMoves");
        JogadasMaquina = intent.getIntegerArrayListExtra("machineMoves");
        opponent = intent.getStringExtra(Battle.EXTRA_MESSAGE);
        Log.e("TAG","opponent: "+opponent);

        //Display das imagens
        ImageView imageView = (ImageView) findViewById(R.id.mao1);
        ImageView imageView2 = (ImageView) findViewById(R.id.mao2);
        imageView.setImageResource(R.drawable.mefight);

        if (opponent.equals("Mewtwo")){
            imageView2.setImageResource(R.drawable.rocket);

            manAnimation = (AnimationDrawable)imageView2.getDrawable();
            manAnimation.start();

            Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mewtwo_fight);
            mediaplayer = MediaPlayer.create(Battle.this,path);
            mediaplayer.start();

        } else {

            Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.battle);
            mediaplayer = MediaPlayer.create(Battle.this,path);
            mediaplayer.start();

            Random r = new Random();
            int choice = r.nextInt(4)+1;
            switch (choice){
                case 1:
                    imageView2.setImageResource(R.drawable.misty);
                    break;
                case 2:
                    imageView2.setImageResource(R.drawable.dork);
                    break;
                case 3:
                    imageView2.setImageResource(R.drawable.gary);
                    break;
                case 4:
                    imageView2.setImageResource(R.drawable.giovanni);
                    break;
            }
        }
        //HP
        ImageView hp1 = (ImageView) findViewById(R.id.imageView);
        ImageView hp2 = (ImageView) findViewById(R.id.imageView2);
        hp1.setImageResource(R.drawable.hp1);
        hp2.setImageResource(R.drawable.hp1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void Rock(View view){
        ImageView imageView = (ImageView) findViewById(R.id.mao1);
        imageView.setImageResource(R.drawable.rock);
        Jogar(1);
    }
    public void Paper(View view){
        ImageView imageView = (ImageView) findViewById(R.id.mao1);
        imageView.setImageResource(R.drawable.paper);
        Jogar(2);
    }
    public void Scissors(View view){
        ImageView imageView = (ImageView) findViewById(R.id.mao1);
        imageView.setImageResource(R.drawable.scissors);
        Jogar(3);
    }

    public void Run(View view){
        TextView textView = (TextView) findViewById(R.id.textView);

        Random r = new Random();
        if (r.nextInt(11) < 8){
            textView.setText("You got away safely");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putIntegerArrayListExtra("myMoves",JogadasMinhas);
            intent.putIntegerArrayListExtra("machineMoves",JogadasMaquina);
            setResult(RESULT_OK,intent);
            finish();
        }else{
            textView.setText("The opponent blocked your escape!");
            Button button = (Button) findViewById(R.id.run);
            button.setVisibility(View.INVISIBLE);
        }
    }

    public void Jogar(int MinhaJogada){
        if (JogadasMaquina.size() < 5){
            machine.AiMove = machine.JogaRandom();
        }
        else {
            machine.AiMove = machine.PreveJogada1(JogadasMinhas,JogadasMaquina);
        }

        //Por imagem da jogada da machine
        switch (machine.AiMove){
            case 1:
                ImageView imageView = (ImageView) findViewById(R.id.mao2);
                imageView.setImageResource(R.drawable.rockm);
                break;
            case 2:
                ImageView imageView1 = (ImageView) findViewById(R.id.mao2);
                imageView1.setImageResource(R.drawable.paperm);
                break;
            case 3:
                ImageView imageView2 = (ImageView) findViewById(R.id.mao2);
                imageView2.setImageResource(R.drawable.scissorsm);
        }

        String resultado = machine.Resultado(MinhaJogada, machine.AiMove);

        if (resultado.equals("Ganhou")){
            Vitorias += 1;

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            if (Vitorias == 1){imageView.setImageResource(R.drawable.hp2);}
            else if (Vitorias == 2){imageView.setImageResource(R.drawable.hp3);}
            else if (Vitorias == 3){imageView.setImageResource(R.drawable.hp4);}

            if (Vitorias > 3){

                Intent intent = new Intent(this, MainActivity.class);
                intent.putIntegerArrayListExtra("myMoves",JogadasMinhas);
                intent.putIntegerArrayListExtra("machineMoves",JogadasMaquina);

                if (mediaplayer.isPlaying()){
                    mediaplayer.stop();
                }

                Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.victory);
                mediaplayer = MediaPlayer.create(Battle.this,path);
                mediaplayer.start();

                if (!opponent.equals("Mewtwo")){
                    Toast.makeText(this,"Congratulations you've won!", Toast.LENGTH_SHORT).show();

                    intent.putExtra("Vitorias","sim");
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(this,"Congratulations you've won!", Toast.LENGTH_SHORT).show();

                    intent.putExtra("VitorMew","sim");
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        }
        else if (resultado.equals("Empatou")){
            Empates += 1;
        }
        else if (resultado.equals("Perdeu")){
            Derrotas += 1;

            ImageView imageView = (ImageView) findViewById(R.id.imageView2);
            if (Derrotas == 1){imageView.setImageResource(R.drawable.hp2);}
            else if (Derrotas == 2){imageView.setImageResource(R.drawable.hp3);}
            else if (Derrotas == 3){imageView.setImageResource(R.drawable.hp4);}

            if (Derrotas > 3){

                //POR AQUI UM FRAGMENTO COM A MENSAGEM E QUE SE DESTROI!!!
                Toast.makeText(this,"Seems like you've Lost!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                intent.putIntegerArrayListExtra("myMoves",JogadasMinhas);
                intent.putIntegerArrayListExtra("machineMoves",JogadasMaquina);
                intent.putExtra("Derrotas","sim");
                setResult(RESULT_OK,intent);
                finish();
            }
        }

        JogadasMinhas.add(MinhaJogada);
        JogadasMaquina.add(machine.AiMove);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Vitorias: "+Vitorias+"\nEmpates: "+Empates+"\nDerrotas: "+Derrotas);
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
}