package com.example.rentatease.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.activities.UpdateApartmentActivity;
import com.example.rentatease.model.Apartment;
import com.example.rentatease.model.Booking;
import com.example.rentatease.model.Chat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {


    private Context context;
    private List<Booking> bookingList;
    Booking booking;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvBookedBy, tvPrice, tvAddress, tvTitle, tvDate;
        AppCompatButton btnCancel, btnAccept;

        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvBookedBy = view.findViewById(R.id.tvBookedBy);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvDate = view.findViewById(R.id.tvDate);
            btnCancel = view.findViewById(R.id.btnCancel);
            btnAccept = view.findViewById(R.id.btnAccept);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    Date date = new Date();
                    booking = bookingList.get(getAdapterPosition());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("chat_details");
                    String chat_detailsId = mDatabase.push().getKey();
                    Chat chat = new Chat(chat_detailsId, booking.getOwnerId(), booking.getUserId(), "", "", "Owner rejected the booking request", formatter.format(date), booking.getApartmentId(), booking.getTitle(), booking.getAddress());
                    mDatabase.child(Objects.requireNonNull(chat_detailsId)).setValue(chat);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bookings/" + booking.getBookingId());
                    databaseReference.removeValue();
                }
            });
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booking = bookingList.get(getAdapterPosition());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("all_appartments/" + booking.getApartmentId());
                    mDatabase.child("booked").setValue(true);

                    DatabaseReference mDatabaseBooking = FirebaseDatabase.getInstance().getReference("bookings/" + booking.getBookingId());
                    mDatabaseBooking.child("checked").setValue(true);

                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    Date date = new Date();

                    DatabaseReference mDatabaseChat = FirebaseDatabase.getInstance().getReference("chat_details");
                    String chat_detailsId = mDatabaseChat.push().getKey();
                    Chat chat = new Chat(chat_detailsId, booking.getOwnerId(), booking.getUserId(), "", "", "Owner accepted the booking request", formatter.format(date), booking.getApartmentId(), booking.getTitle(), booking.getAddress());
                    mDatabaseChat.child(Objects.requireNonNull(chat_detailsId)).setValue(chat);

                }
            });


        }


    }

    public BookingAdapter(Context mContext, List<Booking> bookingList) {
        this.context = mContext;
        this.bookingList = bookingList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        booking = bookingList.get(position);
        holder.tvBookedBy.setText("Booking request by : " + booking.getBookedBy());
        holder.tvPrice.setText("Price : " + booking.getPrice());
        holder.tvAddress.setText(booking.getAddress());
        holder.tvDate.setText("Date : " + booking.getFromDate() + " - " + booking.getToDate());
        holder.tvTitle.setText(booking.getTitle());


    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }


}