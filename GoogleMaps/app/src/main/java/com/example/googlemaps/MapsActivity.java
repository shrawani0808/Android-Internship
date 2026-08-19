package com.example.googlemaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;

import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;
    Marker marker;
    boolean isfirstlocationupdate=true;

    SearchView searchbar ;
    FloatingActionButton fab;


    private final ActivityResultLauncher<String[]> locationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),result ->{
                Boolean fineLocationEnabled = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false);
                Boolean coarseLocationEnabled = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);
                if(fineLocationEnabled != null && fineLocationEnabled){
                    enableUserLocation();
                } else if (coarseLocationEnabled != null && coarseLocationEnabled) {
                    checkLocationSettings();
                }else {
                    Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps);
        searchbar=findViewById(R.id.searchView);
        fab=findViewById(R.id.fab);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        if(mapFragment != null){
            mapFragment.getMapAsync(this);
        }
        setUpLocationCallback();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try{

                    List<Address> address = geocoder.getFromLocationName(query,1);
                    if(address != null && !address.isEmpty()){
                       Address address1 = address.get(0);
                       double latitude = address1.getLatitude();
                       double longitude = address1.getLongitude();
                       LatLng latLng = new LatLng(latitude,longitude);
                       googleMap.animateCamera(
                               CameraUpdateFactory.newLatLngZoom(latLng,15)
                       );
                       googleMap.addMarker(new MarkerOptions()
                               .position(latLng)
                               .title(query)
                       );
                    }
                    else {
                        Toast.makeText(MapsActivity.this, "Location Not found!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    Toast.makeText(MapsActivity.this, "Unable to search LOcation: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        fab.setOnClickListener(v -> {
            if(googleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                Toast.makeText(this, "Satellite View ", Toast.LENGTH_SHORT).show();
            }else{
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                Toast.makeText(this, "Normal View", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressWarnings("MissingPermission")
    private void enableUserLocation(){
        if(googleMap == null){
            return;
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        startLocationUpdate();
    }

    private void setUpLocationCallback(){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for(Location location : locationResult.getLocations()){
                    if(location != null){
                        LatLng currentLatlng = new LatLng(location.getLatitude(),location.getLongitude());
                        if(googleMap != null) {
                            if (marker == null) {
                                marker = googleMap.addMarker(new MarkerOptions()
                                        .position(currentLatlng)
                                        .title("Your Location"));
                            } else {
                                marker.setPosition(currentLatlng);
                            }
                            if (isfirstlocationupdate) {
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatlng, 16f));
                                isfirstlocationupdate = false;
                            }
                        }
                    }
                }
            }
        };
    }
    @SuppressWarnings("MissingPermission")
    private void startLocationUpdate(){
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000)
                .setMinUpdateIntervalMillis(2000)
                .setMinUpdateDistanceMeters(5f)
                .build();
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(fusedLocationClient != null && locationCallback != null){
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(googleMap != null){
            checkLocationPermissionAndStartUpdate();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        checkLocationPermissionAndStartUpdate();
    }

    private void checkLocationPermissionAndStartUpdate(){
       boolean finegranted = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
       boolean coarsegranted = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
       if(finegranted | coarsegranted){
           checkLocationSettings();
       }else{
           locationPermissionLauncher.launch(
                   new String[]{
                           Manifest.permission.ACCESS_FINE_LOCATION,
                           Manifest.permission.ACCESS_COARSE_LOCATION
                   }
           );
       }
    }

    public void checkLocationSettings(){
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000)
                .setMinUpdateIntervalMillis(2000)
                .setMinUpdateDistanceMeters(5f)
                .build();
        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<com.google.android.gms.location.LocationSettingsResponse> task =
                settingsClient.checkLocationSettings(locationSettingsRequest);
        task.addOnSuccessListener(locationSettingsResponse -> {
            enableUserLocation();
        });
        task.addOnFailureListener(e -> {
            if(e instanceof ResolvableApiException){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                locationSettingsLauncher.launch(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> locationSettingsLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> checkLocationSettings()
            );

}