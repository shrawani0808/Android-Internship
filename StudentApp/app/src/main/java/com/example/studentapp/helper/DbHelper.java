package com.example.studentapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentapp.studentmodel.StudentModel;

import java.util.ArrayList;
import java.util.UUID;


public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="StudentDB";
    public static final int DATABASE_VERSION=2;
    public static final String TABLE_NAME = "StdRecords";
    public static final String COL_ID = "id" ;
    public static final String COL_NAME = "name";
    public static final String COL_AGE ="age";
    public static final String COL_COURSE ="course";
    public static final String COL_PRICE="price";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable= "CREATE TABLE "+TABLE_NAME+"("+COL_ID+" TEXT PRIMARY KEY,"+COL_NAME+" TEXT,"+COL_AGE+" INTEGER,"+COL_COURSE+" TEXT,"+COL_PRICE+" INTEGER)";
        db.execSQL(createTable);
        //CREATE TABLE TABLE_NAME (COL_ID text primary key,COL_NAME text,COL_AGE integer, COL_COURSE text,COL_PRICE integer)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(StudentModel model){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID,model.getId());
        cv.put(COL_NAME,model.getName());
        cv.put(COL_AGE,model.getAge());
        cv.put(COL_COURSE,model.getCourse());
        cv.put(COL_PRICE,model.getPrice());
        long result = db.insert(TABLE_NAME,null,cv);
        return result>0;
    }

    public ArrayList<StudentModel> getData(){
        ArrayList<StudentModel> studList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String fetch = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(fetch,null);
        while(cursor.moveToNext()) {
            StudentModel model = new StudentModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(COL_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE));
            String course = cursor.getString(cursor.getColumnIndexOrThrow(COL_COURSE));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRICE));

            model.setId(id);
            model.setName(name);
            model.setAge(age);
            model.setCourse(course);
            model.setPrice(price);

            studList.add(model);
        }
        return studList;
    }

}
