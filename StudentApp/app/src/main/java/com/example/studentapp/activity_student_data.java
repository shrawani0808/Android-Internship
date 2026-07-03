package com.example.studentapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.adapter.StudentAdapter;
import com.example.studentapp.helper.DbHelper;
import com.example.studentapp.studentmodel.StudentModel;

import java.util.ArrayList;

public class activity_student_data extends AppCompatActivity {
    RecyclerView recyclerview;
    ArrayList<StudentModel> studList;
    DbHelper helper= new DbHelper(activity_student_data.this);
    StudentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        studList = helper.getData();
        adapter = new StudentAdapter(activity_student_data.this,studList);
        recyclerview.setAdapter(adapter);

    }
}