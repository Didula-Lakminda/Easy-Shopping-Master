package com.example.easyshopping.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyshopping.R;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private CardView mouse_cardView , computerCase_cardView , headphone_cardView , watches_carView , laptop_cardView, keyboard_cardView ;
    private Button maintainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        Toast.makeText(this,"Welcome Admin...",Toast.LENGTH_SHORT).show();

        mouse_cardView = (CardView)findViewById(R.id.mouse_cardView);
        computerCase_cardView =(CardView)findViewById(R.id.computerCase_cardView);
        headphone_cardView =(CardView)findViewById(R.id.headphone_cardView);
        watches_carView = (CardView)findViewById(R.id.watches_cardView);
        laptop_cardView =(CardView)findViewById(R.id.laptop_cardView);
        keyboard_cardView =(CardView)findViewById(R.id.keyboard_cardView);
        maintainButton = (Button)findViewById(R.id.adminMaintain_button);

        mouse_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this, AddProductActivity.class);
                intent.putExtra("Category","Mouse");
                startActivity(intent);
            }
        });

        computerCase_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this,AddProductActivity.class);
                intent.putExtra("Category","Computer Case");
                startActivity(intent);
            }
        });

        headphone_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this,AddProductActivity.class);
                intent.putExtra("Category","Headphone");
                startActivity(intent);
            }
        });

        keyboard_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this,AddProductActivity.class);
                intent.putExtra("Category","Keyboard");
                startActivity(intent);
            }
        });

        watches_carView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this,AddProductActivity.class);
                intent.putExtra("Category","Watches");
                startActivity(intent);
            }
        });

        laptop_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this,AddProductActivity.class);
                intent.putExtra("Category","Laptop");
                startActivity(intent);
            }
        });

        maintainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAddNewProductActivity.this, AdminHomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });
    }
}