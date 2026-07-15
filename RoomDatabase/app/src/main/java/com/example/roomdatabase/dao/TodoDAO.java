package com.example.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.example.roomdatabase.entity.ToDoEntity;

import java.util.List;

@Dao
public interface TodoDAO {
    @Query("SELECT * FROM todos")
    List<ToDoEntity> getAllTodos();
    @Insert
    void addTodos(ToDoEntity toDoEntity);
    @Update
    void updateTodos(ToDoEntity toDoEntity);
    @Delete
    void deleteTodos(ToDoEntity toDoEntity);
}
