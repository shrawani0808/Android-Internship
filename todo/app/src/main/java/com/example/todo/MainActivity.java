package com.example.todo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.helper.DatabaseHelper;
import com.example.todo.model.TodoModel;
import com.example.todo.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private ArrayList<TodoModel> todoList;
    public EditText edtTask;
    public Button btnAddTodo;
    //private LinearLayout container_layout;
    //private ListView listView;
    private RecyclerView todoRv;
    private TodoAdapter adapter;

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
        //update todos
//        updateTodo();
//        deleteTodo();
        getAllTodos();
        //searchTodo();
        clickfun();


    }



    public void clickfun(){
        btnAddTodo.setOnClickListener(v-> {
            String btnText = btnAddTodo.getText().toString();
            if (btnText.equals("Add Todo")) {
                //addition
                addTodo();
            }
        });
    }
    private void addTodo()
    {
        String task = edtTask.getText().toString().trim();
        String id = Utils.generateUUID();
        TodoModel model = new TodoModel(id,task,false);
        boolean isAdded = helper.addTodo(model);
        if (isAdded) {
            Toast.makeText(this, "Todo Added...", Toast.LENGTH_SHORT).show();
            todoList.add(model);
            adapter.notifyItemInserted(todoList.size());
        }
        else {
            Toast.makeText(this, "failed to add todo", Toast.LENGTH_SHORT).show();
        }
    }

    private void initComp()
    {
        helper = new DatabaseHelper(MainActivity.this);
        todoList = new ArrayList<>();
        edtTask = findViewById(R.id.edtTask);
        btnAddTodo = findViewById(R.id.btnAddTodo);
        todoRv = findViewById(R.id.todoRv);
        todoRv.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getAllTodos() {
        todoList = helper.getAllTodos();
        adapter = new TodoAdapter(MainActivity.this,todoList,MainActivity.this);
        todoRv.setAdapter(adapter);

    }

    public void updateTodo(String id) {
        String task = edtTask.getText().toString();
        TodoModel model = new TodoModel(id,task,false);
        boolean isUpdated = helper.updateTodo(model);
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