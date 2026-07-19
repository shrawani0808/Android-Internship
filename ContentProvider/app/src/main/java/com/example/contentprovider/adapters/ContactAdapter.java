package com.example.contentprovider.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contentprovider.R;
import com.example.contentprovider.models.ContactModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

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
        String contactImg = model.getContactImg();
        holder.contactName.setText(contactName);
        try{
            Picasso.get().load(contactImg).placeholder(R.mipmap.ic_launcher).into(holder.img);
        } catch (Exception e) {
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View view = LayoutInflater.from(context).inflate(R.layout.view_contact_layout,null);
                bottomSheetDialog.setContentView(view);
                TextView contactName = view.findViewById(R.id.viewTV);
                ImageView contactImage = view.findViewById(R.id.viewIV);
                contactName.setText(contactList.get(position).getContactName());
                String contImg = contactList.get(position).getContactImg();
                try{
                    Picasso.get().load(contImg).placeholder(R.mipmap.ic_launcher).into(contactImage);
                } catch (Exception e) {
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                bottomSheetDialog.show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View popupview = LayoutInflater.from(context).inflate(R.layout.view_contact_layout,null);
                PopupWindow popupWindow = new PopupWindow(popupview,ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,true);
                popupWindow.showAsDropDown(holder.itemView);
                return true;
            }
        });
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
