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
import com.example.rentatease.model.Apartment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.rentatease.Const.UserId;

public class DashboardActivity extends AppCompatActivity {

    AppCompatButton btnAdd;
    private RecyclerView recyclerView;
    private List<Apartment> apartmentList;
    private ApartmentAdapter adapter;
    String userId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this, AddApartmentActivity.class);
                startActivity(i);

            }
        });

        apartmentList = new ArrayList<>();
        recyclerView = findViewById(R.id.reclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_appartments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                apartmentList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Apartment apartment = dataSnapshot.getValue(Apartment.class);

                    if (TextUtils.equals(apartment.getUserId(), userId))
                        apartmentList.add(apartment);



                }

                adapter = new ApartmentAdapter(DashboardActivity.this, apartmentList);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
}
