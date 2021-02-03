package com.example.easyshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easyshopping.DriverManagement.Dilivery_details;
import com.example.easyshopping.DriverManagement.Manage_drivers;
import com.example.easyshopping.DriverManagement.driverApplication;

public class DriverHome extends AppCompatActivity {

    private Button Apply_btn,Adminbtn,DiliveryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        Apply_btn = (Button) findViewById(R.id.apply_dirver);

        Apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverHome.this, driverApplication.class);
                startActivity(intent);

            }
        });
        Adminbtn = (Button) findViewById(R.id.adminbtn);

        Adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverHome.this, Manage_drivers.class);
                startActivity(intent);

            }
        });

        DiliveryBtn = (Button) findViewById(R.id.dili_btn) ;

        DiliveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverHome.this, Dilivery_details.class);
                startActivity(intent);

            }
        });

    }
}