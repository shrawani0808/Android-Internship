package com.example.expencetracker;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edttitle,edtamount;
    Button btnaddexpence ;
    DataBaseHelper dbhelper;
    String title,amount;

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

        btnaddexpence.setOnClickListener(v -> {
            title=edttitle.getText().toString();
            amount=edtamount.getText().toString();
            dbhelper= DataBaseHelper.getDB(this.getApplicationContext());
            if(title.isEmpty() || amount.isEmpty()){
                Toast.makeText(this, "Fields should not be empty!!", Toast.LENGTH_SHORT).show();
            }else {
                dbhelper.expenceDao().addTx(
                        new Expence(title, amount)
                );

                ArrayList<Expence> arrExpences = (ArrayList<Expence>) dbhelper.expenceDao().getAllExpence();
                for (int i = 0; i < arrExpences.size(); i++) {
                    Log.d("Data", "Title: " + arrExpences.get(i).getTitle()
                            + " Amount: " + arrExpences.get(i).getAmount());
                }
                Toast.makeText(this,"Expence Added successfully!!",Toast.LENGTH_SHORT).show();
                edttitle.setText("");
                edtamount.setText("");
                edttitle.requestFocus();
            }

        });




    }

    public void initComp(){
        edttitle=findViewById(R.id.title);
        edtamount=findViewById(R.id.amount);
        btnaddexpence=findViewById(R.id.addexpence);
    }
}