package com.example.recyclerviewdemo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.adapters.CourceAdapter;
import com.example.recyclerviewdemo.models.CourceModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerview;

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

        getAllList();
    }

    private void getAllList(){
        ArrayList<CourceModel> courceList = new ArrayList<>();
//        CourceModel model = new CourceModel();
//        model.setCourceTitle("C Language");
//        model.setCourcePrice("299");
//        CourceModel model1 = new CourceModel();
//        model1.setCourceTitle("C++ Language");
//        model1.setCourcePrice("399");
//        courceList.add(model);
//        courceList.add(model1);

        courceList.add(new CourceModel("https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/C_Programming_Language.svg/1920px-C_Programming_Language.svg.png","C Language","299"));
        courceList.add(new CourceModel("https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/ISO_C%2B%2B_Logo.svg/1280px-ISO_C%2B%2B_Logo.svg.png","C++ Language","399"));
        courceList.add(new CourceModel("https://upload.wikimedia.org/wikipedia/en/thumb/3/30/Java_programming_language_logo.svg/960px-Java_programming_language_logo.svg.png","Java Language","499"));
        CourceAdapter adapter = new CourceAdapter(MainActivity.this,courceList);
        recyclerview.setAdapter(adapter);

    }
    public void initComp(){
        fab= findViewById(R.id.fabAdd);
        recyclerview=findViewById(R.id.courseRV);
        LinearLayoutManager layoutManager= new LinearLayoutManager(MainActivity.this);
        recyclerview.setLayoutManager(layoutManager);
    }
}