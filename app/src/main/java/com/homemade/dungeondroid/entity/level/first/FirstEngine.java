package com.homemade.dungeondroid.entity.level.first;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.homemade.dungeondroid.R;
import com.homemade.dungeondroid.entity.GameEngine;
import com.homemade.dungeondroid.entity.GameMap;
import com.homemade.dungeondroid.entity.Player.Direction;

import java.util.Random;

import static com.homemade.dungeondroid.constants.Constants.BATTLE;
import static com.homemade.dungeondroid.constants.Constants.BOSS;
import static com.homemade.dungeondroid.constants.Constants.BOTTOM_LEFT_STONE;
import static com.homemade.dungeondroid.constants.Constants.LIFE;
import static com.homemade.dungeondroid.constants.Constants.NORMAL;
import static com.homemade.dungeondroid.constants.Constants.PLAYER;
import static com.homemade.dungeondroid.constants.Constants.POKEBALL;
import static com.homemade.dungeondroid.constants.Constants.STAIRS;
import static com.homemade.dungeondroid.constants.Constants.STONE;
import static com.homemade.dungeondroid.constants.Constants.STONE_WALL_HORIZONTAL;
import static com.homemade.dungeondroid.constants.Constants.TOP_LEFT_STONE;
import static com.homemade.dungeondroid.entity.Player.Direction.DOWN;
import static com.homemade.dungeondroid.entity.Player.Direction.LEFT;
import static com.homemade.dungeondroid.entity.Player.Direction.RIGHT;
import static com.homemade.dungeondroid.entity.Player.Direction.UP;

public class FirstEngine extends GameEngine {

    private Random random;
    private int[] opponentResourceIds;

    public FirstEngine() {
        random = new Random();
        opponentResourceIds = new int[]{R.drawable.misty, R.drawable.giovanni, R.drawable.gary, R.drawable.dork};
    }

    @Override
    public TableLayout loadPlayerView(Context context, GameMap gameMap, int row, int col, int stairs, Direction direction) {

        TableLayout table = new TableLayout(context);

        // Populate the table with stuff
        for (int y = 0; y < 5; y++) {
            TableRow tableRow = new TableRow(context);
            table.addView(tableRow);

            for (int x = 0; x < 5; x++) {
                ImageView imageView = new ImageView(context);

                int value = gameMap.getMap()[row + y][col + x];

                imageView.setBackgroundResource(R.drawable.sand);

                switch (value) {
                    case PLAYER :
                        if (stairs != 0) imageView.setBackgroundResource(R.drawable.stairs);
                        loadPlayerIntoImageView(imageView, direction);
                        break;
                    case STONE :
                        imageView.setImageResource(R.drawable.stone);
                        break;
                    case POKEBALL :
                        imageView.setImageResource(R.drawable.pokeball);
                        break;
                    case BOSS :
                        imageView.setImageResource(R.drawable.mewtwo);
                        break;
                    default:
                        imageView.setImageResource(loadImageId(imageView, value));
                }

                tableRow.addView(imageView);
            }
        }

        return table;
    }

    private void loadPlayerIntoImageView(ImageView imageView, Direction direction) {
        if (direction == UP) {
            imageView.setImageResource(R.drawable.manup);
        } else if (direction == LEFT){
            imageView.setImageResource(R.drawable.manleft);
        } else if (direction == RIGHT) {
            imageView.setImageResource(R.drawable.manright);
        } else if (direction == DOWN){
            imageView.setImageResource(R.drawable.mandown);
        }
    }

    private int loadImageId(ImageView imageView, int value) {
        int imageId = 0;

        if (value == TOP_LEFT_STONE) {
            imageId = R.drawable.topleft;
        } else if (value == 3 || value == 6) {
            imageId = R.drawable.stonewallvertical;
        } else if (value == STONE_WALL_HORIZONTAL) {
            imageId = R.drawable.stonewallhorizontal;
        } else if (value == BOTTOM_LEFT_STONE) {
            imageId = R.drawable.bottomleft;
        }  else if (value == STAIRS) {
            imageId = R.drawable.stairs;
        }  else if (value == NORMAL || value == BATTLE){
            imageId = R.drawable.sand;
        }

        return imageId;
    }

    @Override
    public boolean isValidMove(int value) {
        return value == NORMAL
                || value == STAIRS
                || value == POKEBALL
                || value == LIFE
                || value == BATTLE;
    }

    @Override
    public boolean runFromBattle() {
        return random.nextInt(11) < 8;
    }

    @Override
    public int loadOpponentResourceId() {
        return opponentResourceIds[random.nextInt(4) + 1];
    }
}
