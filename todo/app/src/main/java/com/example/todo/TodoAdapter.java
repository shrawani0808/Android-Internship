package com.example.todo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.helper.DatabaseHelper;
import com.example.todo.model.TodoModel;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private Context context;
    private ArrayList<TodoModel> todoList;
    private MainActivity mainActivity;
    private DatabaseHelper helper;
    public TodoAdapter(Context context,ArrayList<TodoModel> todoList, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.todoList = todoList;
        this. helper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todos_layout,parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        final TodoModel model = todoList.get(position);
        holder.tvTodo.setText(model.getTask());
        holder.itemView.setOnClickListener(v->{
            String id = model.getId();
            mainActivity.edtTask.setText(model.getTask());
            mainActivity.btnAddTodo.setText("Edit Todo");
            //on button click
            mainActivity.btnAddTodo.setOnClickListener(view->{
                String task = mainActivity.edtTask.getText().toString();
                TodoModel mo = new TodoModel(id,task, false);
                helper.updateTodo(mo); // database operation
                todoList.set(position,mo);
                notifyItemChanged(position);
                mainActivity.btnAddTodo.setText("Add Todo");
                mainActivity.clickfun();
            });
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    model.setCompletion(true);
                    helper.updateTodo(model) ;
                    holder.itemView.setBackgroundColor(Color.parseColor("#03fc94"));

                }else{
                    model.setCompletion(false);
                    helper.updateTodo(model) ;
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (helper.deleteTodo(model.getId())) {
                    todoList.remove(position);
                    notifyItemRemoved(position);
                }
                else {
                    Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView tvTodo;
        CheckBox checkbox;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTodo = itemView.findViewById(R.id.tvTodo);
            checkbox = itemView.findViewById(R.id.chbox);
        }
    }
}
