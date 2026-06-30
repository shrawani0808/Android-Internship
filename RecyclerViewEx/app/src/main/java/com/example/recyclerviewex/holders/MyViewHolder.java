package com.example.recyclerviewex.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewex.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView myname;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        myname = itemView.findViewById(R.id.nameTv);
    }
}
