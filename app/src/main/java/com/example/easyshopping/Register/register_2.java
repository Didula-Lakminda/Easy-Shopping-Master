package com.example.easyshopping.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.easyshopping.R;

import java.util.Calendar;

public class register_2 extends AppCompatActivity {


    Button next, login,reg;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);

        next = findViewById(R.id.register);
        login = findViewById(R.id.login);
        radioGroup = findViewById(R.id.radiogroup);
        datePicker = findViewById(R.id.datepicker);
        reg = findViewById(R.id.rsignin);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validateGender() | !validateAge()) {
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), register_3.class);

                selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
                String gender = selectedGender.getText().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                String date = day + "/" + month + "/" + year;
                String name = getIntent().getExtras().getString("fullname");
                String em = getIntent().getExtras().getString("email");
                String un = getIntent().getExtras().getString("username");
                String pass = getIntent().getExtras().getString("pass");

                intent.putExtra("fullname", name);
                intent.putExtra("email", em);
                intent.putExtra("username", un);
                intent.putExtra("pass", pass);
                intent.putExtra("date", date);
                intent.putExtra("gender", gender);

                startActivity(intent);
                finish();



            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inlog = new Intent(register_2.this, com.example.easyshopping.ProfileHandle.login.class);
                startActivity(inlog);
            }
        });
    }

    public boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int current = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = current - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "Your Age is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
