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

public class Create_driver extends AppCompatActivity {

    private Button Addtbutton;
    private EditText addName,addPhoneNo,addPassword,addIdentityNo,addVehicleNo,addlicenseNo;
    private ProgressDialog AddLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_driver);

        Addtbutton = (Button) findViewById(R.id.btn_adddriver);
        addName = (EditText) findViewById(R.id.adddriver_Name);
        addPhoneNo = (EditText) findViewById(R.id.adddriver_phone_no);
        addPassword = (EditText) findViewById(R.id.adddriver_password);
        addIdentityNo = (EditText) findViewById(R.id.adddriver_ino);
        addVehicleNo = (EditText) findViewById(R.id.adddriver_vno);
        addlicenseNo = (EditText) findViewById(R.id.adddriver_lno);
        AddLoadingBar = new ProgressDialog(this);

        Addtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();

            }
        });



    }

    private void add() {
        String name = addName.getText().toString();
        String phone = addPhoneNo.getText().toString();
        String password = addPassword.getText().toString();
        String idno = addIdentityNo.getText().toString();
        String vno = addVehicleNo.getText().toString();
        String lno = addlicenseNo.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please Enter The Name of the Driver", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please Enter The Driver's Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter A password for the Driver", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(idno))
        {
            Toast.makeText(this, "Please Enter Driver's National Identity No", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(vno))
        {
            Toast.makeText(this, "Please Enter Driver's Vehicle No", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(lno))
        {
            Toast.makeText(this, "Please Enter Driver's License No", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AddLoadingBar.setTitle("Submitting Form");
            AddLoadingBar.setMessage("Please wait while we are checking the credentials");
            AddLoadingBar.setCanceledOnTouchOutside(false);
            AddLoadingBar.show();

            validatePhoneno(name,phone,password,idno,vno,lno);

        }



    }

    private void validatePhoneno(final String name, final String phone, final String password, final String idno, final String vno, final String lno) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Drivers").child(phone).exists()))
                {
                    HashMap<String, Object> Driversdatamap =new HashMap<>();
                    Driversdatamap.put("Name", name);
                    Driversdatamap.put("Phone", phone);
                    Driversdatamap.put("Password", password);
                    Driversdatamap.put("Identity_No", idno);
                    Driversdatamap.put("Vehicle_No", vno);
                    Driversdatamap.put("License_No", lno);

                    RootRef.child("Drivers").child(phone).updateChildren(Driversdatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Create_driver.this, "Application Details Sennded", Toast.LENGTH_SHORT).show();
                                        AddLoadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(Create_driver.this, "Network Error Please Try again Later", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }
                else
                {
                    Toast.makeText(Create_driver.this, "This "+phone +"alredy Exists", Toast.LENGTH_SHORT).show();
                    AddLoadingBar.dismiss();
                    Toast.makeText(Create_driver.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}