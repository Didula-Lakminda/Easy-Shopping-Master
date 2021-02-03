package com.example.easyshopping.ProfileHandle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyshopping.R;
import com.example.easyshopping.User.HomeActivity;
import com.example.easyshopping.session.*;
import com.example.easyshopping.Categories.showusercategory;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class userprofile extends AppCompatActivity {

    Button update, Add, delete;
    CardView logout;
    TextInputLayout fullname, email, phoneNo, password;
    TextView fulllable, username;
    DatabaseReference reference, first, delref;
    String user_username, user_fullname, user_email, user_PhoneNo, user_date;
    CardView profile_category;

    //AddImage
    //Button AddNewProduct;
    ImageView ProductImage;
    static final int GalleryPic = 1;
    Uri ImageUri;
    String saveCurrentDate, saveCurrentTime, phone;
    String ProductRandomKey, downloadUImageUrl;
    StorageReference ProductImagesRef;
    ProgressDialog loadingBar;



    private void clearControls(){
        fullname.getEditText().setText("");
        email.getEditText().setText("");
        phoneNo.getEditText().setText("");
        password.getEditText().setText("");
    }
    //End Of Added


    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        Intent intent = getIntent();
        user_PhoneNo = intent.getStringExtra("phoneNo");

        reference = FirebaseDatabase.getInstance().getReference("users");
        first = FirebaseDatabase.getInstance().getReference("users").child(user_PhoneNo).child("image");
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("User Images");

        update = findViewById(R.id.update);
        fullname = findViewById(R.id.Pfullname);
        email = findViewById(R.id.Pemail);
        phoneNo = findViewById(R.id.PphoneNo);
        password = findViewById(R.id.Ppassword);
        fulllable = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        Add = findViewById(R.id.Add);
        delete = findViewById(R.id.delete);
        profile_category = findViewById(R.id.profile_category);
        loadingBar = new ProgressDialog(this);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(userprofile.this, login.class);
                sessionManager sessionManager = new sessionManager(userprofile.this, com.example.easyshopping.session.sessionManager.SESSION_REMEMBERME);
                sessionManager.logout();
                startActivity(intent1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedata();
            }
        });



        //Added Image
        ProductImage = findViewById(R.id.profile);

        ProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenGallery();

            }
        });

        //End Of Added

        firebaseAuth = FirebaseAuth.getInstance();


        showAllUserData();
        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });


        profile_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

    }



    private void showAllUserData() {

        Intent intent = getIntent();
        user_username = intent.getStringExtra("username");
        user_fullname = intent.getStringExtra("fullname");
        user_email = intent.getStringExtra("email");
        user_PhoneNo = intent.getStringExtra("phoneNo");
        user_date = intent.getStringExtra("date");

        fulllable.setText(user_fullname);
        username.setText(user_username);
        fullname.getEditText().setText(user_fullname);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_PhoneNo);
        password.getEditText().setText(user_date);
    }

    public void update(View view) {
        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Data is same.. can not b updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNameChanged() {
        if (!user_fullname.equals(fullname.getEditText().getText().toString())) {

            reference.child(user_PhoneNo).child("fullname").setValue(fullname.getEditText().getText().toString());
            user_fullname =fullname.getEditText().getText().toString();
            return true;

        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if (!user_date.equals(password.getEditText().getText().toString())) {

            reference.child(user_PhoneNo).child("date").setValue(password.getEditText().getText().toString());
            user_date = password.getEditText().getText().toString();
            return true;

        } else {
            return false;
        }

    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GalleryPic && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            ProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData()
    {


        if(ImageUri == null)
        {
            Toast.makeText(getApplicationContext(), "Cannot Null", Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //ProductRandomKey = saveCurrentDate + saveCurrentTime;
        phone = getIntent().getStringExtra("phoneNo");

        final StorageReference filePath  = ProductImagesRef.child(ImageUri.getLastPathSegment() + phone + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(userprofile.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(userprofile.this, "Image Uploaded Success", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadUImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadUImageUrl = task.getResult().toString();
                            loadingBar.dismiss();
                            Toast.makeText(userprofile.this, "Product Image Save TO Database Successfully", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
    private void saveProductInfoToDatabase()
    {
        loadingBar.setTitle("Please Wait");
        loadingBar.setMessage("Image Will Upload...Wait Few Seconds");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        HashMap<String, Object> productMap = new HashMap<>();
        // productMap.put("date", saveCurrentDate);
        // productMap.put("time", saveCurrentTime);
        productMap.put("image", downloadUImageUrl);

        reference.child(phone).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            loadingBar.dismiss();
                            Intent intent = new Intent(userprofile.this, login.class);
                            Toast.makeText(userprofile.this, "Profile Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(userprofile.this, "Error:" +message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        first.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(ProductImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void deletedata(){

        loadingBar.setTitle("Please Wait");
        loadingBar.setMessage("Your Account Deleting...Wait Few Seconds");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_PhoneNo)){
                    delref = FirebaseDatabase.getInstance().getReference().child("users").child(user_PhoneNo);
                    delref.removeValue();
                    clearControls();
                    Toast.makeText(getApplicationContext(), "Your Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent intent = new Intent(userprofile.this, login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Your Account Can not Deleted", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });
    }

}