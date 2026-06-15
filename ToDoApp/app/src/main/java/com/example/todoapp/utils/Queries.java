package com.example.todoapp.utils;

public class Queries {
    public static final String CREATE_TABLE_TODOLIST = "CREATE TABLE "+Utils.TABLE_NAME+"("
            +Utils.COL_ID+" TEXT PRIMARY KEY, "+Utils.COL_TASKS+" TEXT, "+Utils.COL_ISCOMPLETED+
            " INTEGER DEFAULT 0 );";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+Utils.TABLE_NAME;
}
