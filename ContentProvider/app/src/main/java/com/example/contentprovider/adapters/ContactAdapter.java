package com.example.contentprovider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentprovider.R;
import com.example.contentprovider.models.ContactModel;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    Context context;
    ArrayList<ContactModel> contactList;
    public ContactAdapter(Context context, ArrayList<ContactModel> contactList){
        this.context=context;
        this.contactList=contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.contact_layout,parent,false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final ContactModel model = contactList.get(position);
        String contactName = model.getContactName();
        holder.contactName.setText(contactName);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView contactName;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.contactImage);
            contactName = itemView.findViewById(R.id.contactTextview);
        }
    }


}
