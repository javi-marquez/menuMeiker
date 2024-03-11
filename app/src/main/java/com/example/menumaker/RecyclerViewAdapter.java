package com.example.menumaker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Dish> arrayList;
    int[] img_resources = new int[]{R.drawable.pasta, R.drawable.carne, R.drawable.pescado, R.drawable.legumbres, R.drawable.arroz};

    public RecyclerViewAdapter(Context context, ArrayList<Dish> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        Dish dish = arrayList.get(position);
        String name = dish.getName();
        holder.txt_list_name.setText(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
        holder.txt_list_category.setText(dish.getCategory().toString());
        holder.img_list_dish.setImageResource(img_resources[dish.getCategory().ordinal()]);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).removeDish(dish);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_list_dish;
        TextView txt_list_name, txt_list_category;
        Button btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_list_dish = itemView.findViewById(R.id.img_list_dish);
            txt_list_name = itemView.findViewById(R.id.txt_list_name);
            txt_list_category = itemView.findViewById(R.id.txt_list_category);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
