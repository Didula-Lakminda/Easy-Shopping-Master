package com.example.easyshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyshopping.AddSomething;
import com.example.easyshopping.Categories.addcategory;
import com.example.easyshopping.R;
import com.example.easyshopping.dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adminlog extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextInputLayout username, password;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlog);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        log = findViewById(R.id.login);

        firebaseAuth = FirebaseAuth.getInstance();

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //     if(!isConnected(adminlog.this))
                //   {
                //     showCustomDialog();
                //}

                if(!validateUsername() | !validatePassword() | !isConnected(adminlog.this)){
                    showCustomDialog();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(username.getEditText().getText().toString(),
                        password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(adminlog.this, AddSomething.class));
                        }
                        else{
                            Toast.makeText(adminlog.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });




            }
        });

    }

    private boolean isConnected(adminlog adminlog)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) adminlog.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected()))
        {
            return true;
        }
        else{
            return false;
        }
    }

    private void showCustomDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(adminlog.this);
        builder.setMessage("Please connect to the internet").setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), dashboard.class));
                finish();
            }
        });
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
}