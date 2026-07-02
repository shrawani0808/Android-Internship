package com.example.todo.model;

public class TodoModel {
    String id, task;
    boolean completion;

    public TodoModel () {

    }
    public TodoModel (String id, String task, boolean completion) {
        this.id = id;
        this.task = task;
        this.completion = completion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompletion() {
        return completion;
    }

    public void setCompletion(boolean completion) {
        this.completion = completion;
    }
}