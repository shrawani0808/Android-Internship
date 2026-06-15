package com.example.todoapp.utils;

import java.util.UUID;

public class Utils {

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }

    public static final String DATABASE_NAME = "todo";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME = "todolist";
    public static final String COL_ID = "id";
    public static final String COL_TASKS = "tasks";
    public static final String  COL_ISCOMPLETED = "iscompleted";


}
