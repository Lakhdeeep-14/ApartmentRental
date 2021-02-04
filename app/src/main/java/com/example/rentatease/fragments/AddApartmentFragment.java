package com.example.rentatease.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.example.rentatease.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import static android.app.Activity.RESULT_OK;

public class AddApartmentFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST_NEW = 2;
    ImageView img1, img2;
    Button img1Btn, img2Btn;
    TextInputEditText price_edtTxt, description_edtTxt;
    private Uri mImageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_appartment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        img1 = view.findViewById(R.id.img1);
        img1Btn = view.findViewById(R.id.img1Btn);

        img2 = view.findViewById(R.id.img2);
        img2Btn = view.findViewById(R.id.img2Btn);

        img1Btn.setOnClickListener(
                v -> {
                    openFileChooser();
                });

        img2Btn.setOnClickListener(
                v -> {
                    openFileChooserForImage();
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
            mImageUri = data.getData();
            Picasso.with(getContext()).load(mImageUri).into(img1);
        }else if (requestCode == PICK_IMAGE_REQUEST_NEW && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(getContext()).load(mImageUri).into(img2);
        }
    }
}