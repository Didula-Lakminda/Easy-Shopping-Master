package com.example.easyshopping.Register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyshopping.ProfileHandle.login;
import com.example.easyshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register_1 extends AppCompatActivity {

    TextInputLayout fullname,email,username,password;
    Button next,reg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_1);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        next = findViewById(R.id.register);
        reg = findViewById(R.id.rsignin);
        firebaseAuth = FirebaseAuth.getInstance();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateFullName() | !validateEmail() | !validateUserName() | !validatePassword()){
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),
                        password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register_1.this,"User Authenticated", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(register_1.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                Intent intent = new Intent(getApplicationContext(), register_2.class);
                //Data Insertation
                String name = fullname.getEditText().getText().toString();
                String em = email.getEditText().getText().toString();
                String un = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();

                intent.putExtra("fullname", name);
                intent.putExtra("email", em);
                intent.putExtra("username", un);
                intent.putExtra("pass", pass);

                startActivity(intent);
                finish();

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inlog = new Intent(register_1.this, login.class);
                startActivity(inlog);
            }
        });

    }

    private boolean validateFullName() {
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

    private boolean validateUserName() {
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