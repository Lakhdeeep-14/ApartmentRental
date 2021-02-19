package com.example.rentatease.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.adapters.ApartmentAdapter;
import com.example.rentatease.adapters.ApartmentRenteeAdapter;
import com.example.rentatease.model.Apartment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApartmentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Apartment> apartmentList;
    private ApartmentRenteeAdapter adapter;
    AppCompatButton btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_list);
        apartmentList = new ArrayList<>();
        recyclerView = findViewById(R.id.reclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ApartmentListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_appartments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                apartmentList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Apartment apartment = dataSnapshot.getValue(Apartment.class);
                    apartmentList.add(apartment);
                }

                adapter = new ApartmentRenteeAdapter(ApartmentListActivity.this, apartmentList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(ApartmentListActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
