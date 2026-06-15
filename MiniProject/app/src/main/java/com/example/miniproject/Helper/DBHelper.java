package com.example.miniproject.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "products_db";
    private static final String TABLE_NAME = "register";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_USER = "username";
    private static final String COL_PASSWORD = "password";
    private static final String NEW_TABLE = "products";

    public DBHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME+" TEXT,"+COL_USER+" TEXT,"+COL_PASSWORD+" TEXT"+");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 4){
            String qry = "CREATE TABLE IF NOT EXISTS "+NEW_TABLE+" (id INTEGER PRIMARY KEY, product_name TEXT);";
            db.execSQL(qry);
            onCreate(db);
        }
    }

    public boolean register (String name,String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("username",username);
        values.put("password",password);
        long result = db.insert(TABLE_NAME,null,values);
        return result != -1;
    }

    public boolean loginuser(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_USER+","+COL_PASSWORD+" FROM "+TABLE_NAME+" WHERE "+COL_USER+" = ? AND "+COL_PASSWORD+" = ?",new String[]{username,password});
        return cursor.getCount() > 0;
    }

    public boolean isUserExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COL_USER+" FROM "+TABLE_NAME+" WHERE "+COL_USER+" = ?",new String[]{username});
        return cursor.getCount() > 0;
    }
}
