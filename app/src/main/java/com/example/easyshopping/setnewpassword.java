package com.example.easyshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyshopping.ProfileHandle.login;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class setnewpassword extends AppCompatActivity {

    Button change;
    TextInputLayout current, newpassword;
    user user;
    DatabaseReference dbref;
    CountryCodePicker countryCodePicker;

    private void clearControls(){
        newpassword.getEditText().setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setnewpassword);

        current = findViewById(R.id.current);
        newpassword = findViewById(R.id.newpassword);
        change = findViewById(R.id.change);
        countryCodePicker = findViewById(R.id.country);

        user = new user();

        dbref = FirebaseDatabase.getInstance().getReference().child("users");

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updef = FirebaseDatabase.getInstance().getReference().child("users");
                updef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String getUserEnterPhoneNumber = current.getEditText().getText().toString().trim();

                        final String userEnteredUsername = "+" + countryCodePicker.getFullNumber() + getUserEnterPhoneNumber;

                        if(dataSnapshot.hasChild(userEnteredUsername)){
                            try{
                                user.setPassword(newpassword.getEditText().getText().toString().trim());

                                dbref = FirebaseDatabase.getInstance().getReference().child("users").child(userEnteredUsername).child("password");
                                dbref.setValue(user.password);
                                clearControls();

                                Intent intent = new Intent(setnewpassword.this, login.class);
                                startActivity(intent);
                                finish();

                                Toast.makeText(getApplicationContext(), "Password Reset Successfully", Toast.LENGTH_SHORT).show();


                            }
                            catch(NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No Contact Number exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }

}
