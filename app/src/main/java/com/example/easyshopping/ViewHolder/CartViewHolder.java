package com.example.easyshopping.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshopping.Interface.ItemClickListner;
import com.example.easyshopping.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textProductName , textPrice , textQuantity;
    private ItemClickListner itemClickListner ;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        textProductName = itemView.findViewById(R.id.cartProductName_textView);
        textPrice = itemView.findViewById(R.id.cartPrice_textView);
        textQuantity = itemView.findViewById(R.id.cartQuantity_textView);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
