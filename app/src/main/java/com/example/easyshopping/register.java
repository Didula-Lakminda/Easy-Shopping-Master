package com.example.easyshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyshopping.ProfileHandle.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    Button log, reg;
    TextInputLayout fullname, email, username, phoneNo, password;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        phoneNo = findViewById(R.id.phoneNo);
        password = findViewById(R.id.password);
        log = findViewById(R.id.rsignin);
        reg = findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),
                        password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateName() {
        String name = fullname.getEditText().getText().toString();

        if (name.isEmpty()) {
            fullname.setError("Field Cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String em = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (em.isEmpty()) {
            email.setError("Field Cannot be empty");
            return false;
        }
        else if(!em.matches(emailPattern)){
            email.setError("Email Pattern Doesn't Match");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String un = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (un.isEmpty()) {
            username.setError("Field Cannot be empty");
            return false;
        }
        else if(un.length() >= 15){
            username.setError("Field Must use only 15 characters");
            return false;
        }
        else if(!un.matches(noWhiteSpace)){
            username.setError("No Space allowed");
            return false;
        }
        else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNo() {
        String pn = phoneNo.getEditText().getText().toString();

        if (pn.isEmpty()) {
            phoneNo.setError("Field Cannot be empty");
            return false;
        } else {
            phoneNo.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pass = password.getEditText().getText().toString();
        String passwordVal = "^"+ "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";

        if (pass.isEmpty()) {
            password.setError("Field Cannot be empty");
            return false;
        }
        else if(!pass.matches(passwordVal)){
            password.setError("Please Enter Strong Password");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

}