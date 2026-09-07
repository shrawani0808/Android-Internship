package com.example.cloudnarydemo;

import android.content.ContentResolver;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Button btnSelect , btnUpload;
    TextView textResult,textImageUrl;
    private Uri imageUri;
    private FirebaseFirestore firestore;
    private static final String CLOUD_NAME = "dr1v4juzd";
    private static final String UPLOAD_PRESET = "gallery_images";
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
        textImageUrl=findViewById(R.id.textViewImageUrl);
        firestore = FirebaseFirestore.getInstance();
        btnSelect.setOnClickListener(v -> {
            imagePicker.launch("image/*");
        });

        btnUpload.setOnClickListener(v -> {
            uploadImage();
        });

    }
    private void uploadImage() {

        if (imageUri == null) {
            textResult.setText("Please select an image first.");
            return;
        }

        try {

            ContentResolver contentResolver = getContentResolver();

            InputStream inputStream =
                    contentResolver.openInputStream(imageUri);

            byte[] imageBytes = new byte[inputStream.available()];

            inputStream.read(imageBytes);
            inputStream.close();

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse("image/*"),
                            imageBytes
                    );

            MultipartBody.Part imagePart =
                    MultipartBody.Part.createFormData(
                            "file",
                            "image.jpg",
                            requestFile
                    );

            RequestBody uploadPreset =
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            UPLOAD_PRESET
                    );

            CloudinaryApi api =
                    ApiClient.getApi(CLOUD_NAME);

            Call<CloudinaryResponse> call =
                    api.uploadImage(
                            imagePart,
                            uploadPreset
                    );

            textResult.setText("Uploading...");

            call.enqueue(new Callback<CloudinaryResponse>() {

                @Override
                public void onResponse(
                        Call<CloudinaryResponse> call,
                        Response<CloudinaryResponse> response) {

                    if (response.isSuccessful()
                            && response.body() != null) {

                        String imageUrl =
                                response.body().getSecure_url();

                        textResult.setText(
                                "Upload successful!\n" + imageUrl
                        );
                        saveImageUrlToFirestore(imageUrl);

                    } else {

                        textResult.setText(
                                "Upload failed: "
                                        + response.code()
                        );
                    }
                }

                @Override
                public void onFailure(
                        Call<CloudinaryResponse> call,
                        Throwable t) {

                    textResult.setText(
                            "Upload error: "
                                    + t.getMessage()
                    );
                }
            });

        } catch (IOException e) {

            textResult.setText(
                    "Error reading image: "
                            + e.getMessage()
            );
        }
    }

    private void saveImageUrlToFirestore(String imageUrl){
        Map <String, Object> imageData = new HashMap<>();
        imageData.put("imageUrl",imageUrl);
        imageData.put("uploaded ",com.google.firebase.Timestamp.now());
        firestore.collection("images").add(imageData)
                .addOnSuccessListener(documentReference -> {
                    textImageUrl.setText("Saved Successfully! "+ imageUrl);
                })
                .addOnFailureListener(e -> {
                    textImageUrl.setText("Firestore Error !"+e.getMessage());
                });
    }

}