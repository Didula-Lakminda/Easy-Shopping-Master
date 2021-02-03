package com.example.easyshopping;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class viewh extends RecyclerView.ViewHolder {

    TextView appl_phone,appl_name;

    public viewh(@NonNull View itemView) {
        super(itemView);

        appl_name= itemView.findViewById(R.id.appl_name);
        appl_phone= itemView.findViewById(R.id.appl_phone);

    }
}
