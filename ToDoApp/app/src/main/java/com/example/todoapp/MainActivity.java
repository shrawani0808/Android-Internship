package com.example.todoapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todoapp.helper.DataBaseHelper;
import com.example.todoapp.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper helper;

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

        initComp();
        String id = Utils.generateUUID();
        boolean isCreated = helper.addtasks(id,"Create todo app",false);
        if(isCreated){
            Toast.makeText(this, "TASK ADDED", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "TASK NOT ADDED", Toast.LENGTH_SHORT).show();
        }


    }

    private void initComp(){
        helper = new DataBaseHelper(MainActivity.this);
    }

}