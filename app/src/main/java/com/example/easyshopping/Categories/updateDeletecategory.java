package com.example.easyshopping.Categories;

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

public class updateDeletecategory extends AppCompatActivity {

    private Button updateButton ,deleteButton;
    private TextInputEditText categoryName ;
    private TextInputEditText categoryDes ;
    private ImageView productImage ;
    private String categoryID;
    private DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_deletecategory);
        categoryID = getIntent().getStringExtra("c_id");
        productRef = FirebaseDatabase.getInstance().getReference().child("category").child(categoryID);



        updateButton = (Button)findViewById(R.id.maintainUpdate_button);
        categoryName = (TextInputEditText)findViewById(R.id.maintainproductname_editText);
        categoryDes = (TextInputEditText)findViewById((R.id.maintainproductdescription_editText));
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
                Toast.makeText(updateDeletecategory.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(updateDeletecategory.this, displaycategory.class);
                startActivity(intent);
            }
        });
    }

    private void updateDetails() {

        String Category_name = categoryName.getText().toString();
        String Category_des = categoryDes.getText().toString();


        if(Category_name.equals(""))
        {
            Toast.makeText(this, "Please insert product name", Toast.LENGTH_SHORT).show();
        }
        else if(Category_des.equals(""))
        {
            Toast.makeText(this, "Please insert product price", Toast.LENGTH_SHORT).show();
        }
        else
        {

            HashMap<String,Object> productMap = new HashMap<>();
            productMap.put("c_id", categoryID);
            productMap.put("Cname",Category_name);
            productMap.put("Cdes",Category_des);


            productRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(updateDeletecategory.this, "Product successfully updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(updateDeletecategory.this,displaycategory.class);
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
                    String C_Name =dataSnapshot.child("Cname").getValue().toString();
                    String C_Des = dataSnapshot.child("Cdes").getValue().toString();
                    String C_Image = dataSnapshot.child("image").getValue().toString();

                    categoryName.setText(C_Name);
                    categoryDes.setText(C_Des);
                    Picasso.get().load(C_Image).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}