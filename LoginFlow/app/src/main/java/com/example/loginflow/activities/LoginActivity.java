package com.example.loginflow.activities;

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

import com.example.loginflow.MainActivity;
import com.example.loginflow.R;

public class LoginActivity extends AppCompatActivity {

    private EditText uname, pass;
    private Button loginbtn;
    private String username, password;

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
        initComponent();

        loginbtn.setOnClickListener(v -> {
            if(validateLogin()){
                login();
            }
        });
    }

    private void initComponent(){
        uname=findViewById(R.id.uname);
        pass=findViewById(R.id.pass);
        loginbtn=findViewById(R.id.loginbtn);
    }

    private boolean validateLogin(){
        username=uname.getText().toString().trim();
        password=pass.getText().toString().trim();
        if(username.isEmpty()){
            Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.isEmpty()){
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void login(){
        if(username.equals("admin") && password.equals("admin@123")){
            Intent i=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            setLogin();
        }
        else{
            Toast.makeText(this,"Invalid Credientials!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void setLogin(){
        SharedPreferences preferences = getSharedPreferences("LoginFlow",MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putBoolean("isLoggedIn",true);
        editor.apply();
    }

    private boolean checkedLoggedIn(){
        SharedPreferences preferences = getSharedPreferences("LoginFlow",MODE_PRIVATE);
        boolean login= preferences.getBoolean("isLoggedIn",false);
        Toast.makeText(this,""+login,Toast.LENGTH_SHORT).show();
        return login;

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart called", Toast.LENGTH_SHORT).show();
        if(checkedLoggedIn()){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}