package com.example.rentatease.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.model.Apartment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;
import java.util.Random;

import static com.example.rentatease.Const.UserId;

public class UpdateApartmentActivity extends AppCompatActivity {

    private AppCompatEditText etAddress, etPrice, etDesc;
    private AppCompatButton btnUpdate;
    private AppCompatImageView ivImage1, ivImage2;
    String address, price, desc, image1, image2, userId,apartmentId;
    private static final int PICK_IMAGE_REQUEST1 = 1;
    private static final int PICK_IMAGE_REQUEST2 = 2;
    private Uri mImageUri1, mImageUri2;
    private String selectedImagePath = "";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_apartment);

        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");

        etAddress = findViewById(R.id.etAddress);
        etPrice = findViewById(R.id.etPrice);
        etDesc = findViewById(R.id.etDesc);
        btnUpdate = findViewById(R.id.btnUpdate);

        ivImage1 = findViewById(R.id.ivImage1);
        ivImage2 = findViewById(R.id.ivImage2);

        address=getIntent().getStringExtra("address");
        price=getIntent().getStringExtra("price");
        desc=getIntent().getStringExtra("desc");
        image1=getIntent().getStringExtra("image1");
        image2=getIntent().getStringExtra("image2");
        apartmentId=getIntent().getStringExtra("apartmentId");

        Picasso.with(UpdateApartmentActivity.this).load(image1).into(ivImage1);
        Picasso.with(UpdateApartmentActivity.this).load(image2).into(ivImage2);
        etAddress.setText(address);
        etDesc.setText(desc);
        etPrice.setText(price);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = etAddress.getText().toString();
                price = etPrice.getText().toString();
                desc = etDesc.getText().toString();

                if (TextUtils.isEmpty(address)) {
                    etAddress.setError("Please enter address");
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    etPrice.setError("Please enter price");
                    return;
                }
                if (TextUtils.isEmpty(desc)) {
                    etDesc.setError("Please enter description");
                    return;
                }
                if (TextUtils.isEmpty(image1)) {
                    Toast.makeText(UpdateApartmentActivity.this, "Please select first image", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(image2)) {
                    Toast.makeText(UpdateApartmentActivity.this, "Please select second image", Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("all_appartments");
                Apartment apartment = new Apartment(apartmentId, price, desc, address, userId, image1, image2);
                mDatabase.child(Objects.requireNonNull(apartmentId)).setValue(apartment);

                Toast.makeText(UpdateApartmentActivity.this, "Apartment updated Successfully!", Toast.LENGTH_LONG).show();
                etAddress.setText("");
                etPrice.setText("");
                etDesc.setText("");
                finish();


            }
        });
        ivImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_IMAGE_REQUEST1);
            }
        });

        ivImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser(PICK_IMAGE_REQUEST2);
            }
        });

    }

    //method to show file chooser
    private void showFileChooser(int request) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), request);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri1 = data.getData();
            Picasso.with(UpdateApartmentActivity.this).load(mImageUri1).into(ivImage1);
            uploadImage1(mImageUri1, PICK_IMAGE_REQUEST1);
        } else if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri2 = data.getData();
            Picasso.with(UpdateApartmentActivity.this).load(mImageUri2).into(ivImage2);
            uploadImage1(mImageUri1, PICK_IMAGE_REQUEST2);
        }
    }

    private void uploadImage1(Uri mImageUri, int request) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        File file = new File(String.valueOf(mImageUri));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("pics" + generateSessionKey(5));

        UploadTask uploadTask = storageRef.putFile(mImageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (request == 1)
                        image1 = downloadUri.toString();
                    else
                        image2 = downloadUri.toString();
                    progressDialog.dismiss();
                }
            }
        });

    }
    public static String generateSessionKey(int length){
        String alphabet =
                new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // 9

        int n = alphabet.length(); // 10

        String result = new String();
        Random r = new Random(); // 11

        for (int i=0; i<length; i++) // 12
            result = result + alphabet.charAt(r.nextInt(n)); //13

        return result;
    }
}
