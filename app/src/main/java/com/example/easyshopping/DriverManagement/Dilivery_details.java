package com.example.easyshopping.DriverManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyshopping.Dilivery;
import com.example.easyshopping.R;
import com.example.easyshopping.successOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dilivery_details extends AppCompatActivity {

    EditText cust_name, cust_phone,cust_h_address,cust_road,cust_city,cust_district;
    Button btn_insert,btn_show,btn_update,btn_delete;
    DatabaseReference dbref;
    Dilivery dil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilivery_details);


        cust_name = findViewById(R.id.et_cust_name);
        cust_phone =findViewById(R.id.et_cust_phone);
        cust_h_address =findViewById(R.id.et_cust_h_address);
        cust_road =findViewById(R.id.et_cust_road);
        cust_city =findViewById(R.id.et_cust_city);
        cust_district =findViewById(R.id.et_cust_district);


        btn_insert =findViewById(R.id.btn_insert);
        btn_show =findViewById(R.id.btn_show);
        btn_update =findViewById(R.id.btn_update);
        btn_delete =findViewById(R.id.btn_delete);

        dil = new Dilivery();

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("Dilivery");
                try {
                    if (TextUtils.isEmpty(cust_name.getText().toString()))
                        Toast.makeText(Dilivery_details.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cust_phone.getText().toString()))
                        Toast.makeText(Dilivery_details.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cust_h_address.getText().toString()))
                        Toast.makeText(Dilivery_details.this, "Please Enter Your Home No", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cust_road.getText().toString()))
                        Toast.makeText(Dilivery_details.this, "Please Enter Road Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cust_city.getText().toString()))
                        Toast.makeText(Dilivery_details.this, "Please Enter City Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(cust_district.getText().toString()))
                        Toast.makeText(Dilivery_details.this, "Please Enter District Name", Toast.LENGTH_SHORT).show();

                    else {
                        dil.setDdname(cust_name.getText().toString().trim());
                        dil.setDdphone(Integer.parseInt(cust_phone.getText().toString().trim()));
                        dil.setDdhouno(cust_h_address.getText().toString().trim());
                        dil.setDdroad(cust_road.getText().toString().trim());
                        dil.setDdcity(cust_city.getText().toString().trim());
                        dil.setDddistrict(cust_district.getText().toString().trim());
                        dbref.child("dil1").setValue(dil);

                        Intent intent = new Intent(getApplicationContext(), successOrder.class);
                        Toast.makeText(Dilivery_details.this, "Data inserted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        clearcontrol();

                    }
                }
                catch (NumberFormatException nfe){
                    Toast.makeText(Dilivery_details.this, "Invalid Contact number", Toast.LENGTH_SHORT).show();
                }
            }

        });


        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readref = FirebaseDatabase.getInstance().getReference().child("Dilivery/dil1");
                readref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            cust_name.setText(snapshot.child("ddname").getValue().toString());
                            cust_phone.setText(snapshot.child("ddphone").getValue().toString());
                            cust_h_address.setText(snapshot.child("ddhouno").getValue().toString());
                            cust_road.setText(snapshot.child("ddroad").getValue().toString());
                            cust_city.setText(snapshot.child("ddcity").getValue().toString());
                            cust_district.setText(snapshot.child("dddistrict").getValue().toString());
                        }
                        else
                            Toast.makeText(Dilivery_details.this, "No source to Display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference upref = FirebaseDatabase.getInstance().getReference().child("Dilivery");
                upref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("dil1")){
                            try{
                                dil.setDdname(cust_name.getText().toString().trim());
                                dil.setDdphone(Integer.parseInt(cust_phone.getText().toString().trim()));
                                dil.setDdhouno(cust_h_address.getText().toString().trim());
                                dil.setDdroad(cust_road.getText().toString().trim());
                                dil.setDdcity(cust_city.getText().toString().trim());
                                dil.setDddistrict(cust_district.getText().toString().trim());

                                dbref =FirebaseDatabase.getInstance().getReference().child("Dilivery").child("dil1");
                                dbref.setValue(dil);
                                clearcontrol();
                                Toast.makeText(Dilivery_details.this, "Data Update Success", Toast.LENGTH_SHORT).show();

                            }
                            catch (NumberFormatException e){
                                Toast.makeText(Dilivery_details.this, "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                            Toast.makeText(Dilivery_details.this, "No Source Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delref = FirebaseDatabase.getInstance().getReference().child("Dilivery");
                delref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("dil1")){
                            dbref = FirebaseDatabase.getInstance().getReference().child("Dilivey").child("dil1");
                            dbref.removeValue();
                            clearcontrol();
                            Toast.makeText(Dilivery_details.this, "Data Deletion Success", Toast.LENGTH_SHORT).show();


                        }
                        else
                            Toast.makeText(Dilivery_details.this, "No source to Delete", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });






    }
    private  void clearcontrol(){
        cust_name.setText("");
        cust_phone.setText("");
        cust_h_address.setText("");
        cust_road.setText("");
        cust_city.setText("");
        cust_district.setText("");

    }
}