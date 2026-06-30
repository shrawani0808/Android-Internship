package com.example.recyclerviewex.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewex.R;
import com.example.recyclerviewex.holders.MyViewHolder;
import com.example.recyclerviewex.models.Model;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private ArrayList<Model> nameList ;

    public MyAdapter(Context context, ArrayList<Model> nameList){
        this.context=context;
        this.nameList=nameList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.name_layout,parent,false);
        MyViewHolder myholder = new MyViewHolder(v);
        return myholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = nameList.get(position).getName();
        holder.myname.setText(name);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }
}
