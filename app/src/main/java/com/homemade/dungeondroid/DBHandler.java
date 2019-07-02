package com.homemade.dungeondroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaosousa on 21/12/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "playersInfo";
    // Contacts table name
    private static final String TABLE_PLAYER = "player";
    // Notes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_VITORIAS = "Vitorias";
    private static final String KEY_DERROTAS = "Derrotas";

    private static final String TAG = "MyActivity";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER + " ("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_USERNAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_VITORIAS + " INTEGER, " + KEY_DERROTAS + " INTEGER)";
        db.execSQL(CREATE_PLAYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        // Creating tables again
        onCreate(db);
    }

    // Adding new note
    public void addJogador(Player jogador) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, jogador.getUsername());
        values.put(KEY_PASSWORD, jogador.getPassword());
        values.put(KEY_VITORIAS, jogador.getVitorias());
        values.put(KEY_DERROTAS, jogador.getDerrotas());
        // Inserting Row
        db.insertWithOnConflict(TABLE_PLAYER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection
    }

    // Getting one note
    public Player getJogador(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PLAYER, new String[] { KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_VITORIAS, KEY_DERROTAS}, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Player jogador = new Player(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
        // return jogador
        return jogador;
    }

    // Getting All notes
    public List<Player> getAllJogadores() {
        List<Player> jogadorList = new ArrayList<Player>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Player jogador = new Player();
                jogador.setId(Integer.parseInt(cursor.getString(0)));
                jogador.setUsername(cursor.getString(1));
                jogador.setPassword(cursor.getString(2));
                jogador.setVitorias(Integer.parseInt(cursor.getString(3)));
                jogador.setDerrotas(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                jogadorList.add(jogador);
            } while (cursor.moveToNext());
        }
        // return contact list
        return jogadorList;
    }

    // Getting notes Count
    public int getJogadoresCount() {
        String countQuery = "SELECT * FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating a note
    public void updateJogador(int id, int Vitorias, int Derrotas) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Log.e(TAG,"Query " + "UPDATE " + TABLE_PLAYER + " SET " + KEY_VITORIAS + " = " + Vitorias + " , " + KEY_DERROTAS + " = " + Derrotas + " WHERE " + KEY_ID + " = " + id);
//        String countQuery = "UPDATE " + TABLE_PLAYER + " SET " + KEY_VITORIAS + " = " + Vitorias + " , " + KEY_DERROTAS + " = " + Derrotas + " WHERE " + KEY_ID + " = " + id;
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        ContentValues args = new ContentValues();
        args.put(KEY_VITORIAS, Vitorias);
        args.put(KEY_DERROTAS, Derrotas);
        db.update(TABLE_PLAYER, args, "id= "+id, null);

    }

    // Deleting a note
    public void deleteJogador(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER, KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    // Deleting everything
    public void deleteAllJogadores() {
        String countQuery = "DELETE FROM " + TABLE_PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
    }


}
