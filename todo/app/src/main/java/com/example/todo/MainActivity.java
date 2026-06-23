package com.example.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todo.helper.DatabaseHelper;
import com.example.todo.model.TodoModel;
import com.example.todo.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private ArrayList<TodoModel> todoList;
    private EditText edtTask;
    private Button btnAddTodo;
    //private LinearLayout container_layout;

    private ListView listView;
    private ArrayList<String> taskList;
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

        //init comp
        initComp();

        btnAddTodo.setOnClickListener(v-> {
            //get task from edit text
            String task = edtTask.getText().toString().trim();
            boolean isAdded = helper.addTodo(task);
            if (isAdded) {
                Toast.makeText(this, "Todo Added...", Toast.LENGTH_SHORT).show();
                getAllTodos();
            }
            else {
                Toast.makeText(this, "failed to add todo", Toast.LENGTH_SHORT).show();
            }
        });

        //update todos
//        updateTodo();
//        deleteTodo();
       // getAllTodos();
        searchTodo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                String selected = taskList.get(index);
                Toast.makeText(MainActivity.this, "selected:"+selected, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initComp()
    {
        helper = new DatabaseHelper(MainActivity.this);
        todoList = new ArrayList<>();
        edtTask = findViewById(R.id.edtTask);
        btnAddTodo = findViewById(R.id.btnAddTodo);
        listView = findViewById(R.id.listview);
    }

    private void getAllTodos() {
        todoList = helper.getAllTodos();
        taskList = new ArrayList<>();
        for (int i=0;i<todoList.size();i++) {
            TodoModel model = todoList.get(i);
            taskList.add(model.getTask());
        }
        ArrayAdapter<String> tasksAdapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                taskList
        );
        listView.setAdapter(tasksAdapter);

    }

    private void updateTodo() {
        String id = "fdcc6aca-5077-4dc3-ad30-66253388dc46";
        boolean isUpdated = helper.updateTodo(id,"To See Recycler View",true);
        if (isUpdated) {
            Toast.makeText(this, "Todo updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Failed to update todo", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTodo() {
        String id = "a437473d-cd9d-45da-889c-9db42bad6407";
        boolean isDeleted = helper.deleteTodo(id);
        if (isDeleted) {
            Toast.makeText(this, "Todo deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Failed to delete todo", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchTodo(){

       todoList = helper.search("a");
       for (int i=0;i<todoList.size();i++){
           Log.d("Search", "searchTodo: "+todoList.get(i).getTask());
       }

    }
}