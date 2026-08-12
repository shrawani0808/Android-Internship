package com.example.wheatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button getLocationBtn;
    TextView locationTv;
    FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST=100;

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

        getLocationBtn.setOnClickListener(v -> {
            getCurrentUserLocation();
        });

    }

    private void initComp(){
        getLocationBtn=findViewById(R.id.getLocationBtn);
        locationTv=findViewById(R.id.location_tv);
        fusedLocationClient=LocationServices.getFusedLocationProviderClient(this);
    }


    private void getCurrentUserLocation(){
        //Check Permissions
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            },LOCATION_PERMISSION_REQUEST);
            return;
        }

        //Get latitude and longitude
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null)
                .addOnSuccessListener(location -> {
                    if(location != null){
                        Double latitude = location.getLatitude();
                        Double longitude= location.getLongitude();
                        Toast.makeText(this, "Fetched Latitude and longitude", Toast.LENGTH_SHORT).show();
                        getAddress(latitude,longitude);
                    }else{
                        Toast.makeText(this, "On Location in Your Device", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getAddress(Double latitude,Double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try{

            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            if(addresses!=null && !addresses.isEmpty()){
                Address address = addresses.get(0);
                String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalcode = address.getPostalCode();
                String addressline = address.getAddressLine(0);

                locationTv.setText("City: "+city+"\nState: "+state+"\nCountry: "+country+"\nPostal code: "+postalcode+"\nAdressLine: "+addressline);
                Toast.makeText(this, "Location Fetched Successfully", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}