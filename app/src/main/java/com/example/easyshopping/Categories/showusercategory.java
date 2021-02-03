package com.example.easyshopping.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.easyshopping.Admin.AdminAddNewProductActivity;
import com.example.easyshopping.Admin.adminlog;
import com.example.easyshopping.R;
import com.example.easyshopping.User.HomeActivity;
import com.example.easyshopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class showusercategory extends AppCompatActivity {

    private DatabaseReference CategoryRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private RelativeLayout searchType;
    private ImageView menu;
    private ImageView back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showusercategory);

        CategoryRef = FirebaseDatabase.getInstance().getReference().child("category");
        recyclerView = findViewById(R.id.menu_recycleView);
        back = (ImageView)findViewById(R.id.backMenu);
        searchType = (RelativeLayout)findViewById(R.id.categorySearch_relativeLayout);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showusercategory.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showusercategory.this, searchcategory.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(CategoryRef,Category.class)
                .build();

        FirebaseRecyclerAdapter<Category, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Category, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Category category) {

                        productViewHolder.categoryName.setText(category.getCname());
                        productViewHolder.categoryDes.setText(category.getCdes());
                        Picasso.get().load(category.getImage()).into(productViewHolder.CategoryImage);


                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_recycler,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}