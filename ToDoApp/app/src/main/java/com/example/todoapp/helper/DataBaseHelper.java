package com.example.todoapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todoapp.utils.Queries;
import com.example.todoapp.utils.Utils;

public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(@Nullable Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Queries.CREATE_TABLE_TODOLIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Queries.DROP_TABLE);
        onCreate(db);
    }

    public boolean addtasks(String id,String todotasks,boolean iscompleted){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Utils.COL_ID,id);
        cv.put(Utils.COL_TASKS,todotasks);
        cv.put(Utils.COL_ISCOMPLETED,iscompleted ? 1 : 0);
        long result = db.insert(Utils.TABLE_NAME,null,cv);
        return result != -1;

    }
}
