package com.example.androidandfragementlifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MAIN_ACTIVITY_LIFECYCLE";
    TextView TV ;
    Button openSecondActivity;

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

        Log.d(TAG,"onCreate() method Called!!!");
        Toast.makeText(this, "onCreate() method Called!!!", Toast.LENGTH_LONG).show();

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragementContainer,new FirstFragment())
                    .commit();
        }

        TV=findViewById(R.id.TV);
        openSecondActivity=findViewById(R.id.openSecondActivity);

        openSecondActivity.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() method Called!!");
        Toast.makeText(this, "onStart() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResumet() method Called!!");
        Toast.makeText(this, "onResume() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() method Called!!");
        Toast.makeText(this, "onPause() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() method Called!!");
        Toast.makeText(this, "onStop() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart() method Called!!");
        Toast.makeText(this, "onRestart() method Called!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() method Called!!");
        Toast.makeText(this, "onDestroy() method Called!!", Toast.LENGTH_LONG).show();
    }

}