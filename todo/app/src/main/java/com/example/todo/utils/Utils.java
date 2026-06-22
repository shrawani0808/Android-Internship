package com.example.todo.utils;
import java.util.UUID;
public class Utils {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static final String DATABASE_NAME="todo";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="todos";
    public static final String COL_ID="id";
    public static final String COL_TASK="task";

    public static final String COL_IS_COMPLETED="isCompleted";



}
