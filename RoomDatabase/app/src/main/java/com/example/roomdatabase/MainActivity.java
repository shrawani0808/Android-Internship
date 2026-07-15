package com.example.roomdatabase;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.adapter.TodoAdapter;
import com.example.roomdatabase.entity.ToDoEntity;
import com.example.roomdatabase.helper.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    RecyclerView recyclerView;
    ArrayList<ToDoEntity> todolist;
    TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       initDatabase();
//        helper.todoDao().addTodos(
//                new ToDoEntity("do crud operations",false)
//        );
//        helper.todoDao().addTodos(
//                new ToDoEntity("add recycler view",false)
//        );
//        helper.todoDao().addTodos(
//                new ToDoEntity("use room database",false)
//        );
        helper.todoDao().updateTodos(
                new ToDoEntity(2,"Recycler view added",true)
        );

        helper.todoDao().deleteTodos(
                new ToDoEntity(5)
        );

        todolist = (ArrayList<ToDoEntity>) helper.todoDao().getAllTodos();
        adapter = new TodoAdapter(this,todolist);
        recyclerView.setAdapter(adapter);

        for(ToDoEntity todo : todolist){
            Log.d("Todos","Your task is:"+todo.getTask());
        }
    }

    public void initDatabase(){
        helper = DatabaseHelper.getInstance(MainActivity.this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}