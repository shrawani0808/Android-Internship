package com.example.roomdatabase.entity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="todos")
public class ToDoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="task")
    private String task;
    @ColumnInfo(name="isCompleted")
    private boolean isCompleted;

    public ToDoEntity(int id, String task, boolean isCompleted) {
        this.id = id;
        this.task = task;
        this.isCompleted = isCompleted;
    }

    @Ignore
    public ToDoEntity(int id) {
        this.id = id;
    }

    @Ignore
    public ToDoEntity(String task, boolean isCompleted) {
        this.task = task;
        this.isCompleted = isCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
