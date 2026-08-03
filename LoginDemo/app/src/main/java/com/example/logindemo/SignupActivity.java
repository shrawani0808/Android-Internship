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
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText uname,email,pass;
    MaterialButton singupbtn;
    TextView gotologin;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signupmain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComp();
        auth = FirebaseAuth.getInstance();

        singupbtn.setOnClickListener(v -> {
            String emailString,passString;
            emailString=email.getText().toString().trim();
            passString=pass.getText().toString().trim();

            if(emailString.isEmpty()){
                email.setError("Enter Email");
                return;
            }
            if (passString.isEmpty()) {
                pass.setError("Enter Password");
                return;
            }
            if(passString.length()<6){
                pass.setError("Password must Contain at least 6 characters");
                return;
            }
            auth
                    .createUserWithEmailAndPassword(emailString,passString)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(this, "Account is Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(
                                    SignupActivity.this, DashboardFragment.class
                            ));
                            finish();
                        }else{
                            String error =task.getException()!= null
                                    ? task.getException().getMessage()
                                    : "Registretion failed";
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        }
                    });


        });

        gotologin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });

    }

    private void initComp(){

        uname=findViewById(R.id.etUnamesup);
        email=findViewById(R.id.etEmailsup);
        pass=findViewById(R.id.etPasswordsup);
        singupbtn=findViewById(R.id.btnSignup);
        gotologin=findViewById(R.id.gotologintxt);


    }
}