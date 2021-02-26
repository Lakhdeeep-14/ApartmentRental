package com.example.rentatease.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.activities.BookingListActivity;
import com.example.rentatease.adapters.BookingAdapter;
import com.example.rentatease.model.Booking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.rentatease.Const.UserId;

public class BookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    private BookingAdapter adapter;
    SharedPreferences sharedPreferences;
    String userId;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_booking_list, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");

        bookingList = new ArrayList<>();
        recyclerView = root.findViewById(R.id.reclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
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

                adapter = new BookingAdapter(getContext(), bookingList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


        return root;
    }
}
