package com.example.easyshopping.FeedbackManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyshopping.R;
import com.example.easyshopping.UserMaster;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateFeedback extends AppCompatActivity {

    Button b5,b6;
    TextView editText4,editText5,editText6;
    String takeExtra;
    DatabaseReference dbRef;
    UserMaster master;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_feedback);

        Intent myIntent = getIntent();
        takeExtra = myIntent.getStringExtra("MAIN_EXTRA");

        editText4 = findViewById(R.id.editText4);
        editText4.setText(R.string.msg2);

        editText5 = findViewById(R.id.editText5);
        editText5.setText(R.string.msg3);

        editText6 = findViewById(R.id.editText6);
        editText6.setText(R.string.msg4);

        b5 = findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference();
                dbRef.child("UserMaster/master/name").setValue(editText4.getText().toString().trim());
                dbRef.child("UserMaster/master/email").setValue(editText5.getText().toString().trim());
                dbRef.child("UserMaster/master/message").setValue(editText6.getText().toString().trim());
                Toast.makeText(getApplicationContext(),"Successfully updated!",Toast.LENGTH_LONG).show();
                clearControls();
            }
        });


        b6 = findViewById(R.id.b6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("UserMaster");
                dbRef.removeValue();
                Toast.makeText(getApplicationContext(),"Successfully deleted!",Toast.LENGTH_LONG).show();
                clearControls();
            }
        });

    }

    private void clearControls()
    {
        editText4.setText("");
        editText5.setText("");
        editText6.setText("");
    }
}