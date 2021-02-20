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
import com.example.rentatease.adapters.ApartmentRenteeAdapter;
import com.example.rentatease.adapters.BookingAdapter;
import com.example.rentatease.model.Apartment;
import com.example.rentatease.model.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.rentatease.Const.UserId;

public class BookingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    private BookingAdapter adapter;
    SharedPreferences sharedPreferences;
    String userId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");

        bookingList = new ArrayList<>();
        recyclerView = findViewById(R.id.reclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BookingListActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bookings");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookingList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Booking booking = dataSnapshot.getValue(Booking.class);
                    if (TextUtils.equals(booking.getOwnerId(), userId) && !booking.isChecked())
                        bookingList.add(booking);
                }

                adapter = new BookingAdapter(BookingListActivity.this, bookingList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


    }
}
