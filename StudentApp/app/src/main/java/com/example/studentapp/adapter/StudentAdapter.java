package com.example.studentapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapp.R;
import com.example.studentapp.studentmodel.StudentModel;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudViewHolder> {

    Context context;
    ArrayList<StudentModel> studList;
    public StudentAdapter(Context context,ArrayList<StudentModel> studList){
        this.context=context;
        this.studList=studList;
    }

    @NonNull
    @Override
    public StudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.display_layout,parent,false);
        return new StudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudViewHolder holder, int position) {
        StudentModel model = studList.get(position);
        String Name = model.getName();
        int Age = model.getAge();
        String course = model.getCourse();
        int price = model.getPrice();
        holder.name.setText(Name);
        holder.age.setText(Age+"");
        holder.course.setText(course);
        holder.price.setText(price+"");
    }

    @Override
    public int getItemCount() {
        return studList.size();
    }

    public class StudViewHolder extends RecyclerView.ViewHolder{

        TextView name,age,course,price;
        public StudViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.nameTv);
            age= itemView.findViewById(R.id.ageTv);
            course= itemView.findViewById(R.id.courseTv);
            price= itemView.findViewById(R.id.priceTv);
        }
    }
}
