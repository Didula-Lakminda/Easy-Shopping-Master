package com.example.easyshopping.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easyshopping.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    private String CategoryName , validateDescription, validatePrice,validateProductName , saveCurrentDate , saveCurrentTime, productRandomKey,downloadImageUrl;
    private Button addProductButton;
    private TextInputEditText productName , description,price;
    private ImageView inputProductImage;
    private static final int GalleryPick= 1;
    private Uri imageUri;
    private StorageReference productImageRef;
    private DatabaseReference productRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        CategoryName = getIntent().getExtras().get("Category").toString();
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product images");
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Toast.makeText(this, CategoryName,Toast.LENGTH_SHORT).show();

        addProductButton = (Button)findViewById(R.id.addprocuct_button);
        productName = (TextInputEditText)findViewById(R.id.productname_editText);
        description = (TextInputEditText)findViewById(R.id.description_editText);
        price =(TextInputEditText)findViewById(R.id.price_EditText);
        inputProductImage =(ImageView)findViewById(R.id.select_product_image);
        loadingBar = new ProgressDialog(this);

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });
    }


    private void openGallery() {

        Intent GalleryIntent = new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode== RESULT_OK && data != null)
        {
            imageUri = data.getData();
            inputProductImage.setImageURI(imageUri);

        }
    }

    private void validateProductData() {
        validateDescription = description.getText().toString();
        validatePrice = price.getText().toString();
        validateProductName = productName.getText().toString();

        if(imageUri==null)
        {
            Toast.makeText(this,"Please input product image",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(validateDescription))
        {
            Toast.makeText(this,"Please input description",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(validateProductName))
        {
            Toast.makeText(this,"Please input product name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(validatePrice))
        {
            Toast.makeText(this,"Please input price",Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding new product");
        loadingBar.setMessage("Please wait, Adding the new product");
        loadingBar.setCancelable(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime ;

        final StorageReference filePath = productImageRef.child(imageUri.getLastPathSegment()+productRandomKey+ ".jpg");

        final UploadTask uploadTask =filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                String message = e.toString();
                Toast.makeText(AddProductActivity.this, message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProductActivity.this, "Image uploaded successfully..", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AddProductActivity.this, "Image successfully saved to database...", Toast.LENGTH_SHORT).show();

                            saveProductInformation();
                        }
                    }
                });

            }
        });


    }

    private void saveProductInformation()
    {
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("product_id", productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",validateDescription);
        productMap.put("image",downloadImageUrl);
        productMap.put("product_name",validateProductName);
        productMap.put("price",validatePrice);
        productMap.put("categoryName",CategoryName);

        productRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {


                    Intent intent = new Intent(AddProductActivity.this, AdminAddNewProductActivity.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(AddProductActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                }
                else{

                    loadingBar.dismiss();
                    String message =task.getException().toString();
                    Toast.makeText(AddProductActivity.this, "Error "+message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}