package com.homemade.dungeondroid.entity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.TableLayout;

import com.homemade.dungeondroid.Battle;
import com.homemade.dungeondroid.R;

public abstract class GameEngine {

    public abstract TableLayout loadPlayerView(Context context, GameMap gameMap, int row, int col, int stairs, Player.Direction direction);

    public abstract boolean isValidMove(int value);

    public abstract boolean runFromBattle();

    public abstract int loadOpponentResourceId();

    public void playBossSong(Context context, MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        Uri path = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.mewtwo_fight);
        mediaPlayer = MediaPlayer.create(context, path);
        mediaPlayer.start();
    }

    public void playBattleSong(Context context, MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        Uri path = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.battle);
        mediaPlayer = MediaPlayer.create(context, path);
        mediaPlayer.start();
    }

    public void playWinningSong(Context context, MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        Uri path = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.victory);
        mediaPlayer = MediaPlayer.create(context, path);
        mediaPlayer.start();
    }

    public void playLosingSong(Context context, MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        Uri path = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.defeat);
        mediaPlayer = MediaPlayer.create(context, path);
        mediaPlayer.start();
    }

    public void playResource(Context context, MediaPlayer mediaPlayer, int resourceId) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }

        Uri path = Uri.parse("android.resource://" + context.getPackageName() + "/" + resourceId);
        mediaPlayer = MediaPlayer.create(context, path);
        mediaPlayer.start();
    }
}
