package com.example.todo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.model.TodoModel;
import com.example.todo.utils.Queries;
import com.example.todo.utils.Utils;
import java.util.UUID;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Utils.DATABASE_NAME,null,Utils.DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL(Queries.CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Queries.DROP_TABLE_TODO);
        onCreate(db);
    }

    public boolean addTodo(String task) {
        String id = UUID.randomUUID().toString(); //generate uuid
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Utils.COL_ID,id);
        cv.put(Utils.COL_TASK,task);
        cv.put(Utils.COL_IS_COMPLETED,0);
        long result = db.insert(Utils.TABLE_NAME,null,cv);
        return result != -1;
    }

    public ArrayList<TodoModel> getAllTodos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Queries.GET_ALL_TODO,null);
        ArrayList<TodoModel> todoList = new ArrayList<>();

        while (cursor.moveToNext()){
            TodoModel model = new TodoModel();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_ID));
            String task = cursor.getString(cursor.getColumnIndexOrThrow(Utils.COL_TASK));
            int isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(Utils.COL_IS_COMPLETED));

            model.setId(id);
            model.setTask(task);
            model.setCompletion(isCompleted == 1);

            todoList.add(model);

        }
        cursor.close();
        return todoList;

    }


    public boolean updateTodo(String id, String task, boolean isCompleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv =new ContentValues();
        cv.put(Utils.COL_TASK,task);
        cv.put(Utils.COL_IS_COMPLETED, isCompleted ? 1 : 0);
        int result = db.update(
                Utils.TABLE_NAME,
                cv,
                Utils.COL_ID+" = ?",
                new String[]{id}
        );
        return result > 0;
    }

    public boolean deleteTodo(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDeleted = db.delete(
                Utils.TABLE_NAME,
                Utils.COL_ID+" = ?",
                new String[]{id}
        );
        return isDeleted > 0;
    }
}
