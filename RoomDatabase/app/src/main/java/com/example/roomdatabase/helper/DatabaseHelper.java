package com.example.roomdatabase.helper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabase.dao.TodoDAO;
import com.example.roomdatabase.entity.ToDoEntity;

@Database(entities = ToDoEntity.class,exportSchema = false,version = 1)
public abstract class DatabaseHelper extends RoomDatabase {

    private static String DB_NAME = "todo_db";
    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,DatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract TodoDAO todoDao();

}
