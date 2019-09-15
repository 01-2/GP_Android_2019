package com.gp_android_2019.bench;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "GP";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "text";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TEXT + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insert() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_NAME + " VALUES(NULL, 'abc');";
        db.execSQL(sql);
        db.close();
    }

    public void update(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET text='xyz' WHERE id=" + id + ";";
        db.execSQL(sql);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=" + id + ";";
        db.execSQL(sql);
        db.close();
    }
}
