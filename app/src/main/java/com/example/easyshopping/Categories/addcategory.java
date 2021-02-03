package com.example.easyshopping.Categories;

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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addcategory extends AppCompatActivity {


    Button add_category,admin_home;
    TextInputLayout category_name,category_description;
    ImageView category_image;
    static final int GalleryPick=1;
    Uri ImageUri;
    String name, des, saveDate,saveTime;
    String randomKey, downloadImageUri;
    StorageReference CategoryImageRef;
    DatabaseReference categoryRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcategory);

        CategoryImageRef = FirebaseStorage.getInstance().getReference().child("Category Image");
        categoryRef = FirebaseDatabase.getInstance().getReference().child("category");

        add_category = findViewById(R.id.add_category);
        category_image = findViewById(R.id.category_image);
        category_name = findViewById(R.id.category_name);
        category_description = findViewById(R.id.category_description);
        admin_home = findViewById(R.id.admin_home);
        loadingBar = new ProgressDialog(this);

        category_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateCategoryData();
            }
        });

        admin_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addcategory.this, displaycategory.class);
                startActivity(intent);
            }
        });

    }

    private void OpenGallery()
    {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            category_image.setImageURI(ImageUri);
        }
    }

    private void ValidateCategoryData()
    {
        name = category_name.getEditText().getText().toString();
        des = category_description.getEditText().getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Category Image Needed", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Category Name Needed", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(des))
        {
            Toast.makeText(this, "Category Description Needed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreCategoryInformation();
        }
    }

    private void StoreCategoryInformation()
    {
        loadingBar.setTitle("Please Wait");
        loadingBar.setMessage("Category Adding...Wait Few Seconds");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(calendar.getTime());

        randomKey = saveDate + saveTime;

        final StorageReference filePath = CategoryImageRef.child(ImageUri.getLastPathSegment() + randomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(addcategory.this, "Error : " +message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addcategory.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageUri = task.getResult().toString();
                            loadingBar.dismiss();

                            Toast.makeText(addcategory.this, "Image url get successfully", Toast.LENGTH_SHORT).show();


                            SaveCategoryInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveCategoryInfoToDatabase()
    {
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("date", saveDate);
        categoryMap.put("time", saveTime);
        categoryMap.put("Cname", name);
        categoryMap.put("Cdes", des);
        categoryMap.put("image", downloadImageUri);
        categoryMap.put("c_id", randomKey);

        categoryRef.child(randomKey).updateChildren(categoryMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //   Intent intent = new Intent(addcategory.this, path.class);
                            //   startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(addcategory.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(addcategory.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }

}