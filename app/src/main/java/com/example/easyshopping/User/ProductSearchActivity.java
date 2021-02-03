package com.example.easyshopping.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.easyshopping.Admin.AdminMaintainProductActivity;
import com.example.easyshopping.Model.Products;
import com.example.easyshopping.R;
import com.example.easyshopping.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductSearchActivity extends AppCompatActivity {

    private ImageView searchImage ;
    private EditText searchText ;
    private RecyclerView searchRecyclerView;
    private String searchInput ;
    private ImageView backButton;
    private String type = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        if(bundle != null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        searchImage= (ImageView) findViewById(R.id.search_imageView);
        searchText =(EditText)findViewById(R.id.productSearch_textView);
        searchRecyclerView = (RecyclerView)findViewById(R.id.searchList_recyclerView);
        backButton =(ImageView) findViewById(R.id.backMenu);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(ProductSearchActivity.this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductSearchActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products>options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(databaseReference.orderByChild("product_name").startAt(searchInput),Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull final Products model) {

                holder.productNameText.setText(model.getProduct_name());
                holder.productDescriptionText.setText((model.getDescription()));
                holder.productPriceText.setText("RS "+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.productImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (type.equals("Admin")) {
                            Intent intent = new Intent(ProductSearchActivity.this, AdminMaintainProductActivity.class);
                            intent.putExtra("product_id", model.getProduct_id());
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(ProductSearchActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("product_id", model.getProduct_id());
                            startActivity(intent);
                        }
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return  holder;
            }
        };
        searchRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}