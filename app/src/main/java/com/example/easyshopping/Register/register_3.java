package com.example.easyshopping.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.inputmethodservice.ExtractEditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyshopping.ProfileHandle.login;
import com.example.easyshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class register_3 extends AppCompatActivity {

    Button next, reg;
    EditText phoneNumber;
    CountryCodePicker countryCodePicker;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_3);

        next = findViewById(R.id.register);
        phoneNumber = findViewById(R.id.phonenumber);
        countryCodePicker = findViewById(R.id.country);
        reg = findViewById(R.id.rsignin);
        loadingBar = new ProgressDialog(this);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


      /*          if (!validatePhoneNumber()) {
                    return;
                } */
                String name = getIntent().getExtras().getString("fullname");
                String em = getIntent().getExtras().getString("email");
                String un = getIntent().getExtras().getString("username");
                String pass = getIntent().getExtras().getString("pass");
                String date = getIntent().getExtras().getString("date");
                String gender = getIntent().getExtras().getString("gender");

                String getUserEnterPhoneNumber = phoneNumber.getText().toString().trim();
                String phoneNo = "+" + countryCodePicker.getFullNumber() + getUserEnterPhoneNumber;

                validatePhoneNo(name, em, un, pass, date, gender, phoneNo);


              /*  user user = new user(name, em, un, phoneNo, pass, date, gender);
                reference.child(phoneNo).setValue(user);
                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show(); */


            }

            private void validatePhoneNo(String name, String em, String un, String pass, String date, String gender, final String getUserEnterPhoneNumber) {


                if (TextUtils.isEmpty(getUserEnterPhoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                } else {

                    loadingBar.setTitle("Please Wait");
                    loadingBar.setMessage("You Will Registered...Wait Few Seconds");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    reference = FirebaseDatabase.getInstance().getReference();

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (!(dataSnapshot.child("users").child(getUserEnterPhoneNumber).exists())) {
                                String name = getIntent().getExtras().getString("fullname");
                                String em = getIntent().getExtras().getString("email");
                                String un = getIntent().getExtras().getString("username");
                                String pass = getIntent().getExtras().getString("pass");
                                String date = getIntent().getExtras().getString("date");
                                String gender = getIntent().getExtras().getString("gender");

                                String getUserEnterPhoneNumber = phoneNumber.getText().toString().trim();
                                String phoneNo = "+" + countryCodePicker.getFullNumber() + getUserEnterPhoneNumber;


                                HashMap<String, Object> userdataMap = new HashMap<>();
                                userdataMap.put("fullname", name);
                                userdataMap.put("email", em);
                                userdataMap.put("username", un);
                                userdataMap.put("password", pass);
                                userdataMap.put("date", date);
                                userdataMap.put("gender", gender);
                                userdataMap.put("phoneNo", phoneNo);

                                reference.child("users").child(phoneNo).updateChildren(userdataMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Your Account Registered Successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(register_3.this, login.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Cannot Registered", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(getApplicationContext(), "This " + getUserEnterPhoneNumber + "already Exists", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inlog = new Intent(register_3.this, login.class);
                startActivity(inlog);
            }
        });

    }


}