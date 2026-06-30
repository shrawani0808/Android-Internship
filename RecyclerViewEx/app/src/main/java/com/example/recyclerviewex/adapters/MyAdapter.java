package com.example.recyclerviewex.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = nameList.get(position);
        String name = model.getName();
        holder.myname.setText(name);

        holder.itemView.setOnClickListener(v -> {
            openDialog(model,position);
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation Window");
                builder.setMessage("Do you want to delete the name?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nameList.remove(position);
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public void openDialog(Model model,int position){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_name_layout);
        TextView nameTv = dialog.findViewById(R.id.edtname);
        Button btnEditAndSave = dialog.findViewById(R.id.btnEditAndSave);

        nameTv.setText(model.getName());
        btnEditAndSave.setText("Edit");

        btnEditAndSave.setOnClickListener(v -> {
            String name = nameTv.getText().toString();
            Model newmodel = new Model(name);
            nameList.add(position,newmodel);
            notifyItemChanged(position);
            dialog.dismiss();
        });
        dialog.show();


    }

}
