package com.example.recyclerviewdemo.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.holders.CourceViewHolder;
import com.example.recyclerviewdemo.models.CourceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CourceAdapter extends RecyclerView.Adapter<CourceViewHolder> {

    private final Context context;
    private final ArrayList<CourceModel> courceList;
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
            final CourceModel model = courceList.get(position);
            String title = model.getCourceTitle();
            String price = model.getCourcePrice();
            String image = model.getCourceImage();
            holder.tvTitle.setText(title);
            holder.tvPrice.setText(price);
            try{
                Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(holder.courceIv);
            } catch (Exception e) {
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            holder.itemView.setOnClickListener(v -> {
                openDialog(model,position);
            });


            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Item");
                    builder.setMessage("Do you want to delete this row?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            courceList.remove(position);
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
        return courceList.size();
    }

    private void openDialog(CourceModel model,int position){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_cource_layout);
        initDialogComp(dialog,model,position);

    }

    private void initDialogComp(Dialog dialog,CourceModel model,int position){
        EditText edtCourceImage = dialog.findViewById(R.id.edtCourceImage);
        EditText edtCourceTitle = dialog.findViewById(R.id.edtCourceTitle);
        EditText edtCourcePrice = dialog.findViewById(R.id.edtCourcePrice);
        Button btnSaveAndEdit = dialog.findViewById(R.id.btnAddAndEdit);

        edtCourceTitle.setText(model.getCourceTitle());
        edtCourcePrice.setText(model.getCourcePrice());
        edtCourceImage.setText(model.getCourceImage());
        btnSaveAndEdit.setText("Edit");


        btnSaveAndEdit.setOnClickListener(v -> {
            String image,title,price;
            image=edtCourceImage.getText().toString();
            title=edtCourceTitle.getText().toString();
            price=edtCourcePrice.getText().toString();

            CourceModel newModel = new CourceModel(image,title,price);
            courceList.set(position,newModel);
            notifyItemChanged(position);
            dialog.dismiss();

        });
        dialog.show();
    }


}
