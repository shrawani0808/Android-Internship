package com.example.recyclerviewex;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewex.adapters.MyAdapter;
import com.example.recyclerviewex.models.Model;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView nameTv;
    private RecyclerView recyclerView ;
    ArrayList<Model> nameList = new ArrayList<>();
    MyAdapter adapter ;


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

        nameList.add(new Model("Shrawani"));
        nameList.add(new Model("Shrawani"));
        nameList.add(new Model("Shrawani"));
        nameList.add(new Model("Shrawani"));
        adapter= new MyAdapter(MainActivity.this,nameList);
        recyclerView.setAdapter(adapter);

    }

    public void initComp(){
        nameTv = findViewById(R.id.nameTv);
        recyclerView=findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}