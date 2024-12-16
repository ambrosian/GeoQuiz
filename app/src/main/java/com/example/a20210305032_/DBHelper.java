package com.example.a20210305032_;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "geoquiz20210305032.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "capitalsbest INTEGER DEFAULT 0, " +
            "flagsbest INTEGER DEFAULT 0, " +
            "countrybest INTEGER DEFAULT 0, " +
            "country TEXT);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void saveBestScores(SQLiteDatabase db, String playerName, int capitalsBestScore, int flagsBestScore, int countryBestScore) {
        String query = "INSERT OR REPLACE INTO users (name, capitalsbest, flagsbest, countrybest) VALUES (?, ?, ?, ?)";
        db.execSQL(query, new Object[]{playerName, capitalsBestScore, flagsBestScore, countryBestScore});
    }

    public int getCapitalsBestScore(SQLiteDatabase db) {
        String query = "SELECT MAX(capitalsbest) FROM users";
        Cursor cursor = db.rawQuery(query, null);
        int bestScore = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                bestScore = cursor.getInt(0);
            }
            cursor.close();
        }
        return bestScore;
    }

    public int getFlagsBestScore(SQLiteDatabase db) {
        String query = "SELECT MAX(flagsbest) FROM users";
        Cursor cursor = db.rawQuery(query, null);
        int bestScore = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                bestScore = cursor.getInt(0);
            }
            cursor.close();
        }
        return bestScore;
    }

    public int getCountryBestScore(SQLiteDatabase db) {
        String query = "SELECT MAX(countrybest) FROM users";
        Cursor cursor = db.rawQuery(query, null);
        int bestScore = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                bestScore = cursor.getInt(0);
            }
            cursor.close();
        }
        return bestScore;
    }


}
