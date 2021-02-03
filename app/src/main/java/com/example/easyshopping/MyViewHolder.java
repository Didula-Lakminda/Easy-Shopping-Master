package com.example.easyshopping;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView d_name,d_phone;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        d_name = itemView.findViewById(R.id.d_name);
        d_phone = itemView.findViewById(R.id.d_phone);
    }
}