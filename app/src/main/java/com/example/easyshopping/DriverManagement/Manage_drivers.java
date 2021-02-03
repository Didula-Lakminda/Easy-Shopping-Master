package com.example.easyshopping.DriverManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easyshopping.Driverapplicant;
import com.example.easyshopping.R;
import com.example.easyshopping.driver_up_del;

public class Manage_drivers extends AppCompatActivity {

    private Button Adddrivers;
    private Button Managedrivers;
    private Button Viewapplicants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_drivers);

        Adddrivers = (Button) findViewById(R.id.btn_add_dri) ;

        Adddrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Manage_drivers.this, Create_driver.class);
                startActivity(intent1);

            }
        });
        Managedrivers = (Button) findViewById(R.id.btn_man_dri) ;

        Managedrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Manage_drivers.this, driver_up_del.class);
                startActivity(intent1);

            }
        });
        Viewapplicants = (Button) findViewById(R.id.btn_view_apply) ;

        Viewapplicants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Manage_drivers.this, Driverapplicant.class);
                startActivity(intent1);

            }
        });
    }
}