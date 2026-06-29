package com.example.recyclerviewdemo.adapters;

import android.app.Dialog;
import android.content.Context;
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

    }

    @Override
    public int getItemCount() {
        return courceList.size();
    }

    private void openDialog(CourceModel model,int position){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_cource_layout);
        initDialogComp(dialog,model,position);
        dialog.show();
    }

    private void initDialogComp(Dialog dialog,CourceModel model,int position){
        EditText edtImage = dialog.findViewById(R.id.edtCourceImage);
        EditText edtTitle = dialog.findViewById(R.id.edtCourceTitle);
        EditText edtPrice = dialog.findViewById(R.id.tvCourcePrice);
        Button btnSaveAndEdit = dialog.findViewById(R.id.btnAddAndEdit);

        edtTitle.setText(model.getCourceTitle());
        edtPrice.setText(model.getCourcePrice());
        edtImage.setText(model.getCourceImage());
        btnSaveAndEdit.setText("Edit");

        btnSaveAndEdit.setOnClickListener(v -> {
            String image,title,price;
            image=edtImage.getText().toString();
            title=edtTitle.getText().toString();
            price=edtPrice.getText().toString();

            CourceModel newModel = new CourceModel(image,title,price);
            courceList.set(position,newModel);
            notifyItemChanged(position);
            dialog.dismiss();

        });
    }


}
