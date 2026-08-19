package com.example.logindemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    TextView nametv, locationTv , locationGeoTV;
    EditText edtupdatename;
    Button locationBtn ,editbtn, deletefieldbtn, deleteuserbtn, changepassBtn;
    FirebaseAuth auth;
    FirebaseFirestore db ;

    private static final int LOCATION_PERMISSION_REQUEST = 100;
    private FusedLocationProviderClient fusedLocationClient ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        nametv= view.findViewById(R.id.nametxt);
        locationTv=view.findViewById(R.id.tvLocation);
        locationGeoTV=view.findViewById(R.id.tvLocationGeocoder);
        locationBtn=view.findViewById(R.id.btnLocation);
        edtupdatename=view.findViewById(R.id.edtupdatename);
        editbtn=view.findViewById(R.id.btnedit);
        deletefieldbtn=view.findViewById(R.id.btndelete);
        deleteuserbtn = view.findViewById(R.id.btndeleteuser);
        changepassBtn=view.findViewById(R.id.btnchangepassword);
        fusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity());
        auth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getUserName();


        locationBtn.setOnClickListener(v -> {
            getCurrentLocation();
        });

        editbtn.setOnClickListener(v -> {
            updateName();
        });

        deletefieldbtn.setOnClickListener(v -> {
            deleteName();
        });

        deleteuserbtn.setOnClickListener(v -> {
            deleteCurrentUser();
        });

        changepassBtn.setOnClickListener(v -> {

                requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.dashboard, new ChangePasswordFragement())
                    .addToBackStack(null)
                    .commit();

        });

        return view;

    }

    private void getUserName(){
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            String uid = user.getUid();
            db.collection("Users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if(documentSnapshot.exists()){
                            String name = documentSnapshot.getString("name");
                            nametv.setText("Welcome "+name);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void updateName(){
        String newname = edtupdatename.getText().toString().trim();
        if(newname.isEmpty()){
            edtupdatename.setError("Please Enter Name");
            return;
        }else{
            FirebaseUser user = auth.getCurrentUser();
            if(user != null){
                String uid = user.getUid();
                db.collection("Users")
                        .document(uid)
                        .update("name",newname)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(requireContext(), "Value Updated", Toast.LENGTH_SHORT).show();
                            getUserName();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(requireContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        }

    }

    private void deleteName(){
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            String uid = user.getUid();
            db.collection("Users")
                    .document(uid)
                    .update("name", FieldValue.delete())
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(requireContext(), "Field Deleated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void deleteCurrentUser(){

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            String uid = user.getUid();
            db.collection("Users")
                    .document(uid)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(requireContext(), "Document Deleated", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(requireContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

    }


    private void getCurrentLocation(){

        //Permission
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },LOCATION_PERMISSION_REQUEST
            );

            return;

        }

        //Get Location
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,null)
                .addOnSuccessListener(location -> {
                    if(location != null){
                       Double latitude = location.getLatitude();
                       Double longitude = location.getLongitude();

                       getCurrentAddress(latitude,longitude);

                       locationTv.setText("latitude: "+latitude+"\n longitude: "+longitude);
                        Toast.makeText(requireContext(), "Location Fetched Sucessfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(requireContext(),"Enable to access location",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private void getCurrentAddress(Double latitude,Double longitude){
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try{

            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            if(addresses!=null && !addresses.isEmpty()){
                Address address = addresses.get(0);
                String city = address.getLocality();
                String state = address.getAdminArea();
                String country = address.getCountryName();
                String postalcode = address.getPostalCode();
                String addressline = address.getAddressLine(0);

                locationGeoTV.setText("City: "+city+"\nState: "+state+"\nCountry: "+country+"\nPostal code: "+postalcode+"\nAdressLine: "+addressline);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}