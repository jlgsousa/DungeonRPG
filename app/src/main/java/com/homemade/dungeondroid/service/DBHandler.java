package com.homemade.dungeondroid.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.homemade.dungeondroid.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaosousa on 21/12/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "playersDb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PLAYER = "player";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_VICTORIES = "Victories";
    private static final String KEY_DEDEATS = "Defeats";
    private static final String KEY_LIFES = "Lifes";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER + " ("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USERNAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_VICTORIES + " INTEGER, "
                + KEY_DEDEATS + " INTEGER, " + KEY_LIFES + " INTEGER)";
        db.execSQL(CREATE_PLAYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        onCreate(db);
    }

    protected void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, player.getUsername());
        values.put(KEY_PASSWORD, player.getPassword());
        values.put(KEY_VICTORIES, player.getVictories());
        values.put(KEY_DEDEATS, player.getDefeats());
        values.put(KEY_LIFES, player.getLifes());

        // Inserting Row
        db.insertWithOnConflict(TABLE_PLAYER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
    }

    protected Player getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER,
                new String[]{ KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_VICTORIES, KEY_DEDEATS, KEY_LIFES},
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

        Player jogador = new Player("", "");
        if (cursor != null) {
            cursor.moveToFirst();
            jogador = new Player(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)));
            cursor.close();
        }

        return jogador;
    }

    protected Player getPlayer(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER,
                new String[]{ KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_VICTORIES, KEY_DEDEATS, KEY_LIFES},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[] { username, password },
                null, null, null, null);

        Player player = new Player("", "");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            player.setId(Integer.parseInt(cursor.getString(0)));
            player.setUsername(cursor.getString(1));
            player.setPassword(cursor.getString(2));
            player.setVictories(Integer.parseInt(cursor.getString(3)));
            player.setDefeats(Integer.parseInt(cursor.getString(4)));

            cursor.close();
        }
        return player;
    }

    protected List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Player player = new Player(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)));

                playerList.add(player);
            } while (cursor.moveToNext());
        }
        return playerList;
    }

    protected int getPlayerCount() {
        String countQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int numberOfPlayers = 0;
        if (cursor != null) {
            numberOfPlayers = cursor.getCount();
            cursor.close();
        }
        return numberOfPlayers;
    }

    protected void updatePlayer(int id, Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues args = new ContentValues();
        args.put(KEY_VICTORIES, player.getVictories());
        args.put(KEY_DEDEATS, player.getDefeats());
        args.put(KEY_LIFES, player.getLifes());

        db.update(TABLE_PLAYER, args, "id= " + id, null);
    }

    protected void deletePlayer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    protected void deletePlayers() {
        String countQuery = "DELETE FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null) cursor.close();
    }

}
