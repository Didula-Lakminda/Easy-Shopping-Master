package com.example.easyshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easyshopping.Admin.AddProductActivity;
import com.example.easyshopping.Admin.AdminAddNewProductActivity;
import com.example.easyshopping.Categories.addcategory;
import com.example.easyshopping.Categories.displaycategory;

public class AddSomething extends AppCompatActivity {

    Button addcateAdmin, addproAdmin, adddriAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_something);

        addcateAdmin = findViewById(R.id.addcateAdmin);
        addproAdmin = findViewById(R.id.addproAdmin);
        adddriAdmin = findViewById(R.id.adddriAdmin);

        addcateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSomething.this, addcategory.class);
                startActivity(intent);
            }
        });

        addproAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSomething.this, displaycategory.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });

        adddriAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSomething.this, DriverHome.class);
                startActivity(intent);
            }
        });

    }
}