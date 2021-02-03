package com.example.easyshopping.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.easyshopping.Model.Products;
import com.example.easyshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView productName , productPrice, productDescription ;
    private ElegantNumberButton elegantNumberButton ;
    private ImageView productImage;
    private String product_id ;
    private Button addToCartButton;
    private String user_id ="1";
    private ImageView backButton;
    private ImageView cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        product_id = getIntent().getStringExtra("product_id");

        productName =(TextView)findViewById(R.id.productDetailsName_textView);
        productDescription =(TextView)findViewById(R.id.productDetailsDescription_textView);
        productPrice =(TextView)findViewById(R.id.productDetailsPrice_textView);
        elegantNumberButton = (ElegantNumberButton)findViewById(R.id.quantity_eleganButton);
        productImage = (ImageView)findViewById(R.id.productDetailsImage_imageView) ;
        addToCartButton=(Button)findViewById(R.id.addToCart_button);
        backButton =(ImageView) findViewById(R.id.backMenu);

        cart = (ImageView)findViewById(R.id.cart);



        cart = (ImageView)findViewById(R.id.cart);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        getProductDetails(product_id);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartList();
            }
        });
    }

    private void addToCartList() {
        String saveCurrentTime , saveCurrentDate ;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");

        final HashMap<String,Object> cartListMap = new HashMap<>();
        cartListMap.put("product_id",product_id);
        cartListMap.put("productName",productName.getText().toString());
        cartListMap.put("description",productDescription.getText().toString());
        cartListMap.put("price",productPrice.getText().toString());
        cartListMap.put("date",saveCurrentDate);
        cartListMap.put("time",saveCurrentTime);
        cartListMap.put("quantity",elegantNumberButton.getNumber());
        cartListMap.put("discount","");

        //needs to get user 22 11.00
        //cartListRef.child("Products").child(product_id)
        //                .updateChildren(cartListMap)
        cartListRef.child("User View").child(user_id).child("Products").child(product_id)
                .updateChildren(cartListMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            cartListRef.child("Admin View").child(user_id).child("Products").child(product_id)
                                    .updateChildren(cartListMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(ProductDetailsActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductDetailsActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });

                        }

                    }
                });


    }

    private void getProductDetails(String product_id) {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(product_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getProduct_name());
                    productDescription.setText((products.getDescription()));
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}