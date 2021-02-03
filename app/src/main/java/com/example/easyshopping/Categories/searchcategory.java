package com.example.easyshopping.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyshopping.R;
import com.example.easyshopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class searchcategory extends AppCompatActivity {


    private Button searchButton ;
    private EditText searchText ;
    private RecyclerView searchRecyclerView;
    private String searchInput ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchcategory);
        searchButton= (Button)findViewById(R.id.productSearch_button);
        searchText =(EditText)findViewById(R.id.productSearch_textView);
        searchRecyclerView = (RecyclerView)findViewById(R.id.searchList_recyclerView);
        // backButton =(ImageView) findViewById(R.id.backMenu);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(searchcategory.this));


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = searchText.getText().toString();
                onStart();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("category");

        FirebaseRecyclerOptions<Category> options=new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(databaseReference.orderByChild("Cname").startAt(searchInput),Category.class).build();

        FirebaseRecyclerAdapter<Category, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Category, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Category model) {

                holder.categoryName.setText(model.getCname());
                holder.categoryDes.setText(model.getCdes());
                Picasso.get().load(model.getImage()).into(holder.CategoryImage);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(searchcategory.this, displaycategory.class);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_recycler,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return  holder;
            }
        };
        searchRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}