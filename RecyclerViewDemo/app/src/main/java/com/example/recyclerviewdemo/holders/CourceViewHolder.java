package com.example.recyclerviewdemo.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;

public class CourceViewHolder extends RecyclerView.ViewHolder {
    public TextView tvTitle, tvPrice;
    public ImageView courceIv;
    public CourceViewHolder(@NonNull View itemView){
        super(itemView);
        tvTitle=itemView.findViewById(R.id.tvCourceTitle);
        tvPrice=itemView.findViewById(R.id.tvCourcePrice);
        courceIv=itemView.findViewById(R.id.courceIv);
    }
}
