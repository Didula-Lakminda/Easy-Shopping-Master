package com.example.easyshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easyshopping.Categories.showusercategory;

public class successOrder extends AppCompatActivity {

    Button orderHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_order);

        orderHome = findViewById(R.id.orderHome);

        orderHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), showusercategory.class);
                startActivity(intent);
            }
        });

    }
}