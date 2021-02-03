package com.example.easyshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyshopping.Model.Driversapplycationinfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Driverapplicant extends AppCompatActivity {

    DatabaseReference refer;

    private FirebaseRecyclerOptions<Driversapplycationinfo>opt;
    private FirebaseRecyclerAdapter<Driversapplycationinfo,viewh>adapt;

    private RecyclerView rview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverapplicant);

        refer = FirebaseDatabase.getInstance().getReference().child("Driversapplycationinfo");


        rview = findViewById(R.id.review);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(this));

        opt=new FirebaseRecyclerOptions.Builder<Driversapplycationinfo>().setQuery(refer,Driversapplycationinfo.class).build();
        adapt=new FirebaseRecyclerAdapter<Driversapplycationinfo,viewh>(opt) {
            @Override
            protected void onBindViewHolder(@NonNull viewh holder, int position, @NonNull Driversapplycationinfo Driversapplycationinfo) {
                holder.appl_name.setText(Driversapplycationinfo.getApplyName());
                holder.appl_phone.setText(Driversapplycationinfo.getApplyPhone());

            }

            @NonNull
            @Override
            public viewh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View vv= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_layout2,parent,false);

                return new viewh(vv);
            }
        };

        adapt.startListening();
        rview.setAdapter(adapt);
    }
}