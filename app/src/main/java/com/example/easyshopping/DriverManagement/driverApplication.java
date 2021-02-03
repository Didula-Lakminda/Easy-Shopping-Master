package com.example.easyshopping.DriverManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class driverApplication extends AppCompatActivity {

    private Button Submitbutton;
    private EditText applyName,applyPhoneNo,applyIdentityNo,applyVehicleNo,applylicenseNo;
    private ProgressDialog LoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_application);


        Submitbutton = (Button) findViewById(R.id.btn_submit);
        applyName = (EditText) findViewById(R.id.driverapply_Name);
        applyPhoneNo = (EditText) findViewById(R.id.driverapply_phone_no);
        applyIdentityNo = (EditText) findViewById(R.id.driverapply_idno);
        applyVehicleNo = (EditText) findViewById(R.id.driverapply_vno);
        applylicenseNo = (EditText) findViewById(R.id.driverapply_lno);
        LoadingBar = new ProgressDialog(this);

        Submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit();

            }
        });



    }

    private void Submit() {
        String name = applyName.getText().toString();
        String phone = applyPhoneNo.getText().toString();
        String idno = applyIdentityNo.getText().toString();
        String vno = applyVehicleNo.getText().toString();
        String lno = applylicenseNo.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Enter your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(idno))
        {
            Toast.makeText(this, "Please Enter your National Identity No", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(vno))
        {
            Toast.makeText(this, "Please Enter your Vehicle No", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(lno))
        {
            Toast.makeText(this, "Please Enter your License No", Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.setTitle("Submitting Form");
            LoadingBar.setMessage("Please wait while we are checking the credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            validatePhoneno(name,phone,idno,vno,lno);

        }



    }

    private void validatePhoneno(final String name, final String phone, final String idno, final String vno, final String lno) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Driversapplycationinfo").child(phone).exists()))
                {
                    HashMap<String, Object> driverdatamap =new HashMap<>();
                    driverdatamap.put("Name", name);
                    driverdatamap.put("Phone", phone);
                    driverdatamap.put("Identity_No", idno);
                    driverdatamap.put("Vehicle_No", vno);
                    driverdatamap.put("License_No", lno);

                    RootRef.child("Driversapplycationinfo").child(phone).updateChildren(driverdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(driverApplication.this, "Application Details Sennded", Toast.LENGTH_SHORT).show();
                                        LoadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(driverApplication.this, "Network Error Please Try again Later", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }
                else
                {
                    Toast.makeText(driverApplication.this, "This "+phone +"alredy Exists", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                    Toast.makeText(driverApplication.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}