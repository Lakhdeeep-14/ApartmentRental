package com.example.rentatease.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rentatease.R;
import com.example.rentatease.model.ApartmentDetail;
import com.example.rentatease.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddApartmentFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_NEW = 2;
    ImageView img1, img2;
    Button img1Btn, img2Btn;
    MaterialButton addAptBtn;
    TextInputEditText price_edtTxt, description_edtTxt, location_edtTxt;
    private Uri mImageUri1,mImageUri2 ;
    FirebaseStorage storage;
    User user;
    DatabaseReference databaseApt;
    String aptID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseApt = FirebaseDatabase.getInstance().getReference("Apartment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = this.getArguments();
        user = (User) bundle.getSerializable("User");

        return inflater.inflate(R.layout.add_appartment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        img1 = view.findViewById(R.id.img1);
        img1Btn = view.findViewById(R.id.img1Btn);
        img2 = view.findViewById(R.id.img2);
        img2Btn = view.findViewById(R.id.img2Btn);
        addAptBtn = view.findViewById(R.id.addAptBtn);
        location_edtTxt = view.findViewById(R.id.location_edtTxt);
        price_edtTxt = view.findViewById(R.id.price_edtTxt);
        description_edtTxt = view.findViewById(R.id.description_edtTxt);

        storage = FirebaseStorage.getInstance();

        img1Btn.setOnClickListener(
                v -> {
                    openFileChooser();
                });

        img2Btn.setOnClickListener(
                v -> {
                    openFileChooserForImage();
                });

        addAptBtn.setOnClickListener(
                v -> {
                    insertNewApt();
                }
        );
    }

    private void insertNewApt(){

        aptID = databaseApt.push().getKey();
        ApartmentDetail apartmentDetail = new ApartmentDetail(aptID, user,
                price_edtTxt.getText().toString(),
                description_edtTxt.getText().toString(),
                location_edtTxt.getText().toString());

        databaseApt.child(aptID).setValue(apartmentDetail , (error, ref) -> {
            if(error == null){
                if(mImageUri1 != null || mImageUri2 !=null) {
                    if (mImageUri1 != null) {
                        uploadImageAndSaveUri(mImageUri1);
                    }
                    if (mImageUri2 != null) {
                        uploadImageAndSaveUri(mImageUri2);
                    }
                }
                else {
                    Toast.makeText(getContext(), "Upload Image", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getContext(), "Apartment Added", Toast.LENGTH_LONG).show();
            }
            System.err.println("Value was set. Error = "+error);
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openFileChooserForImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_NEW);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri1 = data.getData();
            Picasso.with(getContext()).load(mImageUri1).into(img1);
        }else if (requestCode == PICK_IMAGE_REQUEST_NEW && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri2 = data.getData();
            Picasso.with(getContext()).load(mImageUri2).into(img2);
        }
    }

    private void uploadImageAndSaveUri(Uri mImageUri){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child("pics" + user.getUserId() + aptID);

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                exception.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                taskSnapshot.getUploadSessionUri();
            }
        });

    }
}