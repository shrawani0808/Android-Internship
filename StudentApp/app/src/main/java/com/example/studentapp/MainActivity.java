 package com.example.studentapp;

import static android.text.TextUtils.isEmpty;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentapp.helper.DbHelper;
import com.example.studentapp.studentmodel.StudentModel;

import java.util.UUID;

 public class MainActivity extends AppCompatActivity {

    EditText stdName , stdAge , stdCourse , coursePrice;
    Button savebtn;
    DbHelper helper= new DbHelper(MainActivity.this);
    StudentModel model;
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
        savebtn.setOnClickListener(v -> {
            validate();
            setData();
            boolean isadded=helper.addData(model);
            if(isadded){
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Not Added", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initComp(){
        stdName = findViewById(R.id.stdName);
        stdAge = findViewById(R.id.stdAge);
        stdCourse = findViewById(R.id.stdCourse);
        coursePrice = findViewById(R.id.coursePrice);
        savebtn = findViewById(R.id.saveBtn);
    }

    private void validate(){
        if(isEmpty(stdName.getText())||isEmpty(stdAge.getText())||isEmpty(stdCourse.getText())||isEmpty(coursePrice.getText())){

            Toast.makeText(this, "Enter Data in all Fields", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Data entered successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(){
        String id = UUID.randomUUID().toString();
        String name = stdName.getText().toString();
        int age = Integer.parseInt(stdAge.getText().toString());
        String course = stdCourse.getText().toString();
        int price = Integer.parseInt(coursePrice.getText().toString());
        model = new StudentModel(id,name,age,course,price);
    }
}