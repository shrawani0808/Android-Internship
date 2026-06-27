package com.example.recyclerviewdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.holders.CourceViewHolder;
import com.example.recyclerviewdemo.models.CourceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourceAdapter extends RecyclerView.Adapter<CourceViewHolder> {

    private Context context;
    private ArrayList<CourceModel> courceList;
    public CourceAdapter(Context context,ArrayList<CourceModel> courceList){
        this.context=context;
        this.courceList=courceList;
    }

    @NonNull
    @Override
    public CourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =layoutInflater.inflate(R.layout.cource_row_layout,parent,false);
        CourceViewHolder viewHolder = new CourceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourceViewHolder holder, int position) {
            String title = courceList.get(position).getCourceTitle();
            String price = courceList.get(position).getCourcePrice();
            String image = courceList.get(position).getCourceImage();
            holder.tvTitle.setText(title);
            holder.tvPrice.setText(price);
            try{
                Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(holder.courceIv);
            } catch (Exception e) {
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public int getItemCount() {
        return courceList.size();
    }
}
