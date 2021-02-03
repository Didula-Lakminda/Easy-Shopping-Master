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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.easyshopping.Admin.AddProductActivity;
import com.example.easyshopping.Admin.AdminMaintainProductActivity;
import com.example.easyshopping.R;
import com.example.easyshopping.User.HomeActivity;
import com.example.easyshopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class displaycategory extends AppCompatActivity {

    private DatabaseReference CategoryRef, catTotalRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private RelativeLayout searchType;
    private String type ="";

    private TextView category_total;
    private int countCategory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycategory);

        //new
        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        if(bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        CategoryRef = FirebaseDatabase.getInstance().getReference().child("category");
        recyclerView = findViewById(R.id.menu_recycleView);
        searchType = (RelativeLayout)findViewById(R.id.categorySearch_relativeLayout);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        category_total = findViewById(R.id.category_total);
        catTotalRef = FirebaseDatabase.getInstance().getReference().child("category");

        searchType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(displaycategory.this, searchcategory.class);
                startActivity(intent);
            }
        });

        catTotalRef.orderByChild("c_id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    countCategory = (int) dataSnapshot.getChildrenCount();
                    category_total.setText(Integer.toString(countCategory));
                }
                else
                {
                    category_total.setText("0 Categories");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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


                        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(type.equals("Admin"))
                                {
                                    Intent intent = new Intent(displaycategory.this, AddProductActivity.class);
                                    intent.putExtra("Category", category.getCname());
                                    startActivity(intent);

                                }
                                else {
                                    Intent intent = new Intent(displaycategory.this, updateDeletecategory.class);
                                    intent.putExtra("c_id", category.getC_id());
                                    startActivity(intent);
                                }
                            }
                        });

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