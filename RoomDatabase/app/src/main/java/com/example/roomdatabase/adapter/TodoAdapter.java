package com.example.roomdatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.R;
import com.example.roomdatabase.entity.ToDoEntity;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    public Context context;
    ArrayList<ToDoEntity> todoList;
    public TodoAdapter(Context context, ArrayList<ToDoEntity> todoList){
        this.context = context;
        this.todoList = todoList;
    }
    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.todo_layout,parent,false);
        return new TodoHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        final ToDoEntity entity = todoList.get(position);
        String task = entity.getTask();
        boolean isCompleted = entity.isCompleted();
        holder.task.setText(task);
        holder.isCompleted.setText(isCompleted+"");

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoHolder extends RecyclerView.ViewHolder{

        public TextView task,isCompleted;
        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.taskTv);
            isCompleted = itemView.findViewById(R.id.isCompleted);
        }
    }
}
