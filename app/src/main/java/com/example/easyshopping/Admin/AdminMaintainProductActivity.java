package com.example.easyshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easyshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductActivity extends AppCompatActivity {

    private Button updateButton ,deleteButton;
    private TextInputEditText productName ;
    private TextInputEditText productPrice , productDescription ;
    private ImageView productImage ;
    private String productID;
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);

        productID = getIntent().getStringExtra("product_id");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);



        updateButton = (Button)findViewById(R.id.maintainUpdate_button);
        productName = (TextInputEditText)findViewById(R.id.maintainproductname_editText);
        productDescription = (TextInputEditText)findViewById((R.id.maintainproductdescription_editText));
        productPrice = (TextInputEditText)findViewById(R.id.maintainproductprice_editText);
        productImage = (ImageView)findViewById(R.id.maintainproductimage_imageView);
        deleteButton =(Button)findViewById(R.id.maintainDelete_button);

        displayProductDetails();
        
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });

    }

    private void deleteProduct() {
        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMaintainProductActivity.this, AdminAddNewProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateDetails() {

        String Product_Name = productName.getText().toString();
        String Product_Description = productDescription.getText().toString();
        String Product_Price = productPrice.getText().toString();

        if(Product_Name.equals(""))
        {
            Toast.makeText(this, "Please insert product name", Toast.LENGTH_SHORT).show();
        }
        else if(Product_Price.equals(""))
        {
            Toast.makeText(this, "Please insert product price", Toast.LENGTH_SHORT).show();
        }
        else if(Product_Description.equals(""))
        {
            Toast.makeText(this, "Please insert product description", Toast.LENGTH_SHORT).show();
        }
        else
        {

            HashMap<String,Object> productMap = new HashMap<>();
            productMap.put("product_id", productID);
            productMap.put("description",Product_Description);
            productMap.put("product_name",Product_Name);
            productMap.put("price",Product_Price);

            productRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductActivity.this, "Product successfully updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminMaintainProductActivity.this,AdminAddNewProductActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }
    }

    private void displayProductDetails() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String Product_Name =dataSnapshot.child("product_name").getValue().toString();
                    String Product_Description = dataSnapshot.child("description").getValue().toString();
                    String Product_Price = dataSnapshot.child("price").getValue().toString();
                    String Product_Image = dataSnapshot.child("image").getValue().toString();

                    productName.setText(Product_Name);
                    productDescription.setText(Product_Description);
                    productPrice.setText(Product_Price);
                    Picasso.get().load(Product_Image).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}