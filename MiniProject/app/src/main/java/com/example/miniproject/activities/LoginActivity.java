package com.example.miniproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miniproject.Helper.DBHelper;
import com.example.miniproject.MainActivity;
import com.example.miniproject.R;
import com.example.miniproject.constants.Global;

public class LoginActivity extends AppCompatActivity {

    private EditText uname,pass;
    private Button loginbtn, register;
    private String username, password;
    private DBHelper dbhelper; //Declaration
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initcomponents();
        loginbtn.setOnClickListener(v -> {
            if(validlogin()){
                login();
            }
        });

        register.setOnClickListener(v -> {

            Global.navigate(LoginActivity.this, RegisterActivity.class,false);

        });

    }

    public void initcomponents(){
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);
        loginbtn = findViewById(R.id.loginbtn);
        register = findViewById(R.id.register);
        dbhelper = new DBHelper(LoginActivity.this);
    }
    public boolean validlogin(){
        username = uname.getText().toString().trim();
        password = pass.getText().toString().trim();
        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Username and Password Should not be empty",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    public void login(){
        if(dbhelper.isUserExists(username)) {
            if (dbhelper.loginuser(username, password)) {
                Global.navigate(LoginActivity.this, MainActivity.class,true);
                setLogin();
            } else {
                Toast.makeText(this, "Invalid Credientials", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"User does not exist with this username",Toast.LENGTH_LONG).show();
        }

    }
    public void setLogin(){
        SharedPreferences preferences = getSharedPreferences("LoginFlow",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedin",true);
        editor.apply();
    }

    public boolean checkLogin(){
        SharedPreferences preferences = getSharedPreferences("LoginFlow",MODE_PRIVATE);
        boolean login = preferences.getBoolean("isLoggedin",false);
        Toast.makeText(this,""+login,Toast.LENGTH_LONG).show();
        return login;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"onStart method start",Toast.LENGTH_LONG).show();
        if(checkLogin()){
            Global.navigate(LoginActivity.this, MainActivity.class,true);
        }
    }
}