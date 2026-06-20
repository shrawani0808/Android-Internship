package com.example.expencetracker;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = Expence.class,exportSchema = false,version = 1)
public abstract class DataBaseHelper extends RoomDatabase {
}
