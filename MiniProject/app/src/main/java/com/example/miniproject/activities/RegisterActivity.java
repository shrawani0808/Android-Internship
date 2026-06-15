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

public class RegisterActivity extends AppCompatActivity {

    public EditText registername, registeruname, registerpass;
    public Button registerbtn;
    public String name, username, password;
   private DBHelper dbhelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initcomponent();

        registerbtn.setOnClickListener(v -> {
            if (validRegistration()) {
                register();
            }
        });
    }

    public void initcomponent() {
        registername = findViewById(R.id.registername);
        registeruname = findViewById(R.id.registeruname);
        registerpass = findViewById(R.id.registerpass);
        registerbtn = findViewById(R.id.registerbtn);
        dbhelper = new DBHelper(RegisterActivity.this);
    }

    public boolean validRegistration() {
        name = registername.getText().toString().trim();
        username = registeruname.getText().toString().trim();
        password = registerpass.getText().toString().trim();
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields Should not be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public void register() {
        if (dbhelper.isUserExists(username)) {
            Toast.makeText(this, "user already exist with this username!", Toast.LENGTH_LONG).show();
        } else {
            dbhelper.register(name, username, password);
            Global.navigate(RegisterActivity.this, MainActivity.class,true);
            setregister();
        }

    }

    public void setregister() {
        SharedPreferences preferences = getSharedPreferences("LoginFlow", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedin", true);
        editor.apply();
    }

}