package com.example.recyclerviewdemo.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.holders.CourceViewHolder;
import com.example.recyclerviewdemo.models.CourceModel;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CourceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
