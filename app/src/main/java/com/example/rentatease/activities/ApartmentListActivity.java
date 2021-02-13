package com.example.rentatease.activities;


import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.adapters.ApartmentAdapter;
import com.example.rentatease.adapters.ApartmentRenteeAdapter;
import com.example.rentatease.model.Apartment;
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

    }
}
