package com.example.rentatease.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.model.Booking;
import com.example.rentatease.model.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.rentatease.Const.Name;
import static com.example.rentatease.Const.UserId;

public class BookingActivity extends AppCompatActivity {

    AppCompatEditText etFrom, etTo;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendarTo = Calendar.getInstance();
    String apartmentId, userId, ownerId, address, price, title, bookedBy;
    AppCompatButton btnBook;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btnBook = findViewById(R.id.btnBook);

        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");
        bookedBy = sharedPreferences.getString(Name, "");

        apartmentId = getIntent().getStringExtra("apartmentId");
        ownerId = getIntent().getStringExtra("ownerId");
        address = getIntent().getStringExtra("address");
        price = getIntent().getStringExtra("price");
        title = getIntent().getStringExtra("title");

        etFrom = findViewById(R.id.etFrom);
        etTo = findViewById(R.id.etTo);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BookingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarTo.set(Calendar.YEAR, year);
                myCalendarTo.set(Calendar.MONTH, monthOfYear);
                myCalendarTo.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateToLabel();
            }

        };

        etTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(BookingActivity.this, dateTo, myCalendarTo
                        .get(Calendar.YEAR), myCalendarTo.get(Calendar.MONTH),
                        myCalendarTo.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etFrom.getText().toString())) {
                    etFrom.setError("Invalid");
                    return;
                }

                if (TextUtils.isEmpty(etTo.getText().toString())) {
                    etTo.setError("Invalid");
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = new Date();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("bookings");
                String bookingId = mDatabase.push().getKey();
                Booking booking = new Booking(bookingId, etFrom.getText().toString(), etTo.getText().toString(), userId, apartmentId, formatter.format(date), ownerId, title, address, price, bookedBy,false);
                mDatabase.child(Objects.requireNonNull(bookingId)).setValue(booking);
                Toast.makeText(BookingActivity.this, "Booking request sent to owner",
                        Toast.LENGTH_SHORT).show();
                finish();


            }
        });


    }

    private void updateLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etFrom.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateToLabel() {
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTo.setText(sdf.format(myCalendarTo.getTime()));
    }
}
