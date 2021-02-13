package com.example.rentatease.activities;


import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.rentatease.R;
import com.example.rentatease.adapters.ApartmentAdapter;
import com.example.rentatease.adapters.ViewPagerAdapter;
import com.example.rentatease.model.Apartment;
import com.example.rentatease.model.User;
import com.example.rentatease.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApartmentDetailsActivity extends AppCompatActivity {

    ViewPager viewPager;
    AppCompatTextView tvName, tvEmail, tvPhone, tvAddress, tvDesc, tvPrice;
    String image1 = "", image2 = "", address, desc, price, userId;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        image1 = getIntent().getStringExtra("image1");
        image2 = getIntent().getStringExtra("image2");
        address = getIntent().getStringExtra("address");
        desc = getIntent().getStringExtra("desc");
        price = getIntent().getStringExtra("price");
        userId = getIntent().getStringExtra("userId");

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvPrice = findViewById(R.id.tvPrice);
        tvDesc = findViewById(R.id.tvDesc);

        tvPrice.setText("Price : " + price);
        tvAddress.setText("Address : " + address);
        tvDesc.setText("Description : " + desc);

        String[] images = {image1, image2};
        viewPager = findViewById(R.id.viewPager);

        sliderDotspanel = findViewById(R.id.SliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, images);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Users user = dataSnapshot.getValue(Users.class);

                    if (TextUtils.equals(user.getUserId(), userId)) {
                        tvEmail.setText(user.getEmail());
                        tvPhone.setText(user.getMobile());
                        tvName.setText(user.getName());

                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
}
