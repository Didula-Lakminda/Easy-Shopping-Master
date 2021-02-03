package com.example.easyshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.easyshopping.Categories.showusercategory;
import com.example.easyshopping.FeedbackManagement.FeedbackHome;
import com.example.easyshopping.ProfileHandle.login;
import com.example.easyshopping.Register.register_1;
import com.example.easyshopping.User.HomeActivity;

public class dashboard extends AppCompatActivity {

    CardView dreg,dlog,dhome,dads,dfeed,dadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        dreg = findViewById(R.id.dreg);
        dlog = findViewById(R.id.dlog);
        dhome = findViewById(R.id.dhome);
        dads = findViewById(R.id.dads);
        dfeed = findViewById(R.id.dfeed);

        dreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, register_1.class);
                startActivity(intent);
            }
        });

        dlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, login.class);
                startActivity(intent);
            }
        });

        dhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        dads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(dashboard.this, register_1.class);
                // startActivity(intent);
            }
        });

        dfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(dashboard.this, FeedbackHome.class);
                 startActivity(intent);
            }
        });


    }
}