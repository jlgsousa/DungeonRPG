package com.homemade.dungeondroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import static com.homemade.dungeondroid.constants.Constants.ANDROID_RESOURCE;
import static com.homemade.dungeondroid.constants.Constants.PLAYER_INFO;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaplayer;
    private DBHandler dbhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhandler = new DBHandler(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void startActivityStats(View view){
        startActivity(new Intent(this, Stats.class));
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

        String openingTheme = ANDROID_RESOURCE +
                getPackageName() +
                "/" +
                R.raw.opening;

        Uri path = Uri.parse(openingTheme);
        mediaplayer = MediaPlayer.create(MainActivity.this, path);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayout layout=new LinearLayout(this);
        final EditText usernameBox = new EditText(this);
        usernameBox.setHint(getString(R.string.player_name));
        layout.addView(usernameBox);

        final EditText passwordBox = new EditText(this);
        passwordBox.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordBox.setHint(getString(R.string.player_password));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(passwordBox);

        switch (item.getItemId()) {
            case R.id.action_settings:

                new AlertDialog.Builder(this)
                        .setView(layout)
                        .setPositiveButton(getString(R.string.player_register), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog12, int which) {
                                String username = String.valueOf(usernameBox.getText());
                                String password = String.valueOf(passwordBox.getText());

                                List<Player> playerList = dbhandler.getAllJogadores();

                                for (Player gamer : playerList) {
                                    if (username.equals(gamer.getUsername())) {
                                        Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.existing_user), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                Player player = new Player();
                                player.setUsername(username);
                                player.setPassword(password);
                                player.setVitorias(0);
                                player.setDerrotas(0);
                                dbhandler.addJogador(player);
                                Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.registration_done), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .create()
                        .show();

                return true;

            case R.id.action_about:

                new AlertDialog.Builder(this)
                        .setView(layout)
                        .setPositiveButton(getString(R.string.player_login), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean failedLogin = true;
                                String username = String.valueOf(usernameBox.getText());
                                String password = String.valueOf(passwordBox.getText());
                                List<Player> playerList = dbhandler.getAllJogadores();

                                for (Player gamer : playerList) {
                                    if (username.equals(gamer.getUsername()) && password.equals(gamer.getPassword())) {
                                        Intent intent = new Intent(MainActivity.this, Grid.class);
                                        int[] info = {gamer.getId(), gamer.getVitorias(), gamer.getDerrotas()};
                                        intent.putExtra(PLAYER_INFO, info);
                                        MainActivity.this.startActivity(intent);
                                        failedLogin = false;
                                    }
                                }

                                if (failedLogin) {
                                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.username_or_password_incorrect), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .create()
                        .show();

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
