package com.example.logindemo;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText uname, pass;
    MaterialButton loginbtn;
    TextView txtRegister,txtForgot;
    FirebaseAuth auth ;
    String userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComp();
        auth = FirebaseAuth.getInstance();
        loginbtn.setOnClickListener(v -> {
            FirebaseApp.initializeApp(LoginActivity.this);

            userName=uname.getText().toString().trim();
            password=pass.getText().toString().trim();

            auth.signInWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, DashboardFragment.class));
                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    });
        });

        txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

        txtForgot.setOnClickListener(v -> {
            userName = uname.getText().toString().trim();
            if(userName.isEmpty()) {
               uname.setError("Enter Email");
               return;
            }

//            password=pass.getText().toString().trim();
            auth.sendPasswordResetEmail(userName)
                    .addOnCompleteListener(
                            task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(this, "Password Reset Email sent!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
        });

    }


    private void initComp(){

        uname = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPassword);
        loginbtn = findViewById(R.id.btnLogin);
        txtRegister=findViewById(R.id.txtRegister);
        txtForgot=findViewById(R.id.txtForgot);
    }
}