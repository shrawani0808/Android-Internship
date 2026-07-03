package com.example.studentapp.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="StudentDB";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME = "StdRecords";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
