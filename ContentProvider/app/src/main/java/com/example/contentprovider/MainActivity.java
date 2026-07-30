package com.example.contentprovider;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentprovider.adapters.ContactAdapter;
import com.example.contentprovider.models.ContactModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView contactRecyclerView;
    ArrayList<ContactModel> contactList;
    ContactAdapter adapter;

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
        checkPermission();
        fab.setOnClickListener(v -> {
            openDialog();
        });
    }

    private void checkPermission(){
        if(ContextCompat
                .checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED){

            readContacts();

        }else{
            permissionLauncher.launch(
                    new String[]{
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS
                    }
            );
        }
    }
    private void readContacts(){
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = resolver.query(uri,null,null,null,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                String contact_name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contact_photo = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                String contact_no = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.d("CONTACTS","contactName: "+contact_name+contact_no);
                    contactList.add(new ContactModel(contact_name,contact_photo,contact_no));
                    contactRecyclerView.setAdapter(adapter);
            }
        }
    }

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    result->{
                        Boolean isGranted = result.getOrDefault(Manifest.permission.READ_CONTACTS,false);
                        if( isGranted ){
                            readContacts();
                        }else{
                            Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
                        }
                    }
            );

    public void openDialog(){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.add_contact_layout);

        EditText contactName = dialog.findViewById(R.id.saveName);
        EditText contactNo = dialog.findViewById(R.id.saveNo);
        Button saveBtn = dialog.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(v -> {
            String name , no;
            name = contactName.getText().toString();
            no = contactNo.getText().toString();

            ContactModel model = new ContactModel(name,null,no);
            contactList.add(model);

            adapter.notifyItemInserted(contactList.size());
            contactRecyclerView.scrollToPosition(contactList.size()-1);

            dialog.dismiss();

        });

        dialog.show();
    }

    private void initComp(){
        fab = findViewById(R.id.fab);
        contactRecyclerView=findViewById(R.id.contactRV);
        contactList = new ArrayList<>();
        adapter = new ContactAdapter(MainActivity.this,contactList);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}