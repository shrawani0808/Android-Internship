package com.example.cloudnarydemo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Button btnSelect , btnUpload;
    TextView textResult;
    private Uri imageUri;
    private final ActivityResultLauncher<String> imagePicker =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if(uri!=null){
                            imageUri = uri;
                            imgView.setImageURI(imageUri);
                            textResult.setText("Image Selected Successfully!");
                        }
                    });

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

        imgView=findViewById(R.id.imageView);
        btnUpload=findViewById(R.id.uploadBtn);
        btnSelect=findViewById(R.id.selectBtn);
        textResult=findViewById(R.id.textView);

        btnSelect.setOnClickListener(v -> {
            imagePicker.launch("image/*");
        });


    }
}