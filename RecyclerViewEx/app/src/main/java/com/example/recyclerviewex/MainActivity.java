package com.example.recyclerviewex;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
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
        nameList.add(new Model("Sharwari"));
        nameList.add(new Model("Tejal"));
        nameList.add(new Model("Shraddha"));
        nameList.add(new Model("Sayali"));
        nameList.add(new Model("Rutuja"));
        nameList.add(new Model("Vaishnavi"));
        adapter= new MyAdapter(MainActivity.this,nameList);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.add_name_layout);
            Button btnEditAndSave = dialog.findViewById(R.id.btnEditAndSave);

            btnEditAndSave.setText("Save");
            btnEditAndSave.setOnClickListener(v1 -> {
                TextView nameTextview = dialog.findViewById(R.id.nameTv);
                String name = nameTextview.getText().toString();
                Model model = new Model(name);
                nameList.add(model);
                adapter.notifyItemInserted(nameList.size());
                dialog.dismiss();
            });
            dialog.show();
        });

    }

    public void initComp(){
        fab = findViewById(R.id.fab);
        nameTv = findViewById(R.id.nameTv);
        recyclerView=findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}