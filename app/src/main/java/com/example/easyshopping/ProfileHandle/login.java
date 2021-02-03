package com.example.easyshopping.ProfileHandle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyshopping.R;
import com.example.easyshopping.session.sessionManager;
import com.example.easyshopping.Register.register_1;
import com.example.easyshopping.Admin.adminlog;
import com.example.easyshopping.ForgetPassword.forgetpassword;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class login extends AppCompatActivity {

    Button reg,log,forget,dadmin;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    FirebaseAuth firebaseAuth;
    CountryCodePicker countryCodePicker;
    TextInputEditText login_phone_number_edittext, login_password_edittext;

    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        reg = findViewById(R.id.lregister);
        log = findViewById(R.id.login);
        image = findViewById(R.id.logoImage);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget);
        countryCodePicker = findViewById(R.id.country);
        dadmin = findViewById(R.id.dadmin);

        rememberMe = findViewById(R.id.rememberMe);
        login_phone_number_edittext = findViewById(R.id.login_phone_number_edittext);
        login_password_edittext = findViewById(R.id.login_password_edittext);

        //already saved or not check
        sessionManager sessionManager = new sessionManager(login.this, com.example.easyshopping.session.sessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String, String> rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            login_phone_number_edittext.setText(rememberMeDetails.get(sessionManager.KEY_SESSIONPHONENUMBER));
            login_password_edittext.setText(rememberMeDetails.get(sessionManager.KEY_SESSIONPASSWORD));
        }

        dadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, adminlog.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateUsername() | !validatePassword()){
                    return;
                }

                String getUserEnterPhoneNumber = username.getEditText().getText().toString().trim();
                final String userEnteredUsername = "+" +countryCodePicker.getFullNumber() + getUserEnterPhoneNumber;
                final String userEnteredPassword = password.getEditText().getText().toString().trim();

                if(rememberMe.isChecked())
                {
                    sessionManager sessionManager = new sessionManager(login.this, com.example.easyshopping.session.sessionManager.SESSION_REMEMBERME);
                    sessionManager.createRememberMeSession(getUserEnterPhoneNumber, userEnteredPassword);
                }

                Query checkUser = FirebaseDatabase.getInstance().getReference("users").orderByChild("phoneNo").equalTo(userEnteredUsername);

                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            username.setError(null);
                            username.setErrorEnabled(false);

                            String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                            if(passwordFromDB.equals(userEnteredPassword)){

                                username.setError(null);
                                username.setErrorEnabled(false);


                                String fullname = dataSnapshot.child(userEnteredUsername).child("fullname").getValue(String.class);
                                String email = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                                String phoneNo = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                                String username = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                                String date = dataSnapshot.child(userEnteredUsername).child("date").getValue(String.class);

                           /*     //Create Sessions
                                sessionManager sessionManager = new sessionManager(login.this);
                                sessionManager.createLoginSession(fullname, email, phoneNo, username, date);
                                startActivity(new Intent(getApplicationContext(), Retailer.class)); */



                                Intent in = new Intent(getApplicationContext(), userprofile.class);
                                in.putExtra("fullname", fullname);
                                in.putExtra("email", email);
                                in.putExtra("phoneNo", phoneNo);
                                in.putExtra("username", username);
                                in.putExtra("date", date);

                                startActivity(in);
                            }
                            else{
                                password.setError("Wrong Password");
                                password.requestFocus();
                            }
                        }
                        else{
                            username.setError("No such user exist");
                            username.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

            /*   firebaseAuth.signInWithEmailAndPassword(username.getEditText().getText().toString(),
                       password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           startActivity(new Intent(login.this, userprofile.class));
                       }
                       else{
                           Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });

            }
        }); */

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register_1.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logoText,"logo_text");
                pairs[2] = new Pair<View,String>(sloganText,"logo_continue");
                pairs[3] = new Pair<View,String>(username,"input_username");
                pairs[4] = new Pair<View,String>(password,"input_password");
                pairs[5] = new Pair<View,String>(log,"btn_log");
                pairs[6] = new Pair<View,String>(reg,"btn_sign");

                startActivity(intent);


            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, forgetpassword.class);
                startActivity(intent);
            }
        });

    }


    private void isUser(){




    }

    private boolean validateUsername() {
        String un = username.getEditText().getText().toString();

        if (un.isEmpty()) {
            username.setError("Field Cannot be empty");
            return false;
        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String pass = password.getEditText().getText().toString();

        if (pass.isEmpty()) {
            password.setError("Field Cannot be empty");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void callForgetPassword(View view)
    {
        startActivity(new Intent(getApplicationContext(),forgetpassword.class));
    }


}