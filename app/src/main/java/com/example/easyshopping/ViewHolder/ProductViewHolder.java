package com.example.easyshopping.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyshopping.Interface.ItemClickListner;
import com.example.easyshopping.R;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productNameText , productDescriptionText , productPriceText;
    public ImageView productImage;

    public TextView categoryName, categoryDes;
    public ImageView CategoryImage;

    private ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = (ImageView)itemView.findViewById(R.id.productimage_imageView);
        productNameText =(TextView)itemView.findViewById(R.id.productname_textView);
        productDescriptionText =(TextView)itemView.findViewById(R.id.productdescription_textView);
        productPriceText =(TextView)itemView.findViewById(R.id.productprice_textView);

        CategoryImage = (ImageView) itemView.findViewById(R.id.CategoryImage);
        categoryName = (TextView) itemView.findViewById(R.id.categoryName);
        categoryDes = (TextView) itemView.findViewById(R.id.categoryDes);

    }
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }
}
