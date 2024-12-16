package com.example.a20210305032_;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {

    private DBHelper databaseHelper;

    public UserRepository(Context context) {
        databaseHelper = new DBHelper(context);
    }

    public SQLiteDatabase getWritableDatabase() {
        return databaseHelper.getWritableDatabase();
    }

    public Cursor getUsers() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return db.query("users", new String[]{"name", "country"}, null, null, null, null, null);
    }

    public void addUser(String name, String country) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("country", country);

        db.insert("users", null, values);
        db.close();
    }

    public void updateUser(String name, String country) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("country", country);

        db.update("users", values, null, null);
        db.close();
    }

    public void saveBestScores(SQLiteDatabase db, String playerName, int capitalsBestScore, int flagsBestScore, int countryBestScore) {
        databaseHelper.saveBestScores(db, playerName, capitalsBestScore, flagsBestScore, countryBestScore);
    }

    public int getCapitalsBestScore(SQLiteDatabase db) {
        return databaseHelper.getCapitalsBestScore(db);
    }

    public int getFlagsBestScore(SQLiteDatabase db) {
        return databaseHelper.getFlagsBestScore(db);
    }

    public int getCountryBestScore(SQLiteDatabase db) {
        return databaseHelper.getCountryBestScore(db);
    }
}
