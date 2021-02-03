package com.example.easyshopping.FeedbackManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyshopping.R;
import com.example.easyshopping.UserMaster;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackHome extends AppCompatActivity {

    Button button3,button4;
    TextView editText1,editText2,editText3;
    String myExtra = "text";
    DatabaseReference dbRef;
    UserMaster master;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_home);

        master = new UserMaster();

        editText1 = findViewById(R.id.editText1);
        editText1.setText(R.string.name);

        editText2 = findViewById(R.id.editText2);
        editText2.setText(R.string.email);

        editText3 = findViewById(R.id.editText3);
        editText3.setText(R.string.msg);


        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent().putExtra("MAIN_EXTRA", myExtra);
                Intent intent = new Intent(FeedbackHome.this, updateFeedback.class);
                startActivity(intent);
            }
        });

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Send();
            }

            private void Send() {
                dbRef = FirebaseDatabase.getInstance().getReference().child("UserMaster");

                String name = editText1.getText().toString();
                String email = editText2.getText().toString();
                String message = editText3.getText().toString();

                if (TextUtils.isEmpty(name))
                    Toast.makeText(getApplicationContext(), "Please enter name...", Toast.LENGTH_LONG).show();

                else if (TextUtils.isEmpty(email))
                    Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();

                else if (TextUtils.isEmpty(message))
                    Toast.makeText(getApplicationContext(), "Please enter message...", Toast.LENGTH_LONG).show();

                else {
                    master.setName(editText1.getText().toString().trim());
                    master.setEmail(editText2.getText().toString().trim());
                    master.setMessage(editText3.getText().toString().trim());
                    dbRef.child(name).setValue(master);
                    Toast.makeText(getApplicationContext(), "Thank you for your feedback", Toast.LENGTH_LONG).show();
                    clearControls();
                }
            }

        });
    }

    private void clearControls()
    {
        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
    }

}