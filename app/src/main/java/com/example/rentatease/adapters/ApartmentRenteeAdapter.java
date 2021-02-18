package com.example.rentatease.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.activities.ApartmentDetailsActivity;
import com.example.rentatease.model.Apartment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApartmentRenteeAdapter extends RecyclerView.Adapter<ApartmentRenteeAdapter.ViewHolder> {


    private Context context;
    private List<Apartment> apartmentDetailList;
    Apartment apartment;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvDesc, tvPrice, tvAddress;
        AppCompatImageView ivImage1, ivImage2;
        ImageView ivDelete,ivEdit;
        CardView cardView;

        ViewHolder(View view) {
            super(view);

            tvDesc = view.findViewById(R.id.tvDesc);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvAddress = view.findViewById(R.id.tvAddress);
            ivImage1 = view.findViewById(R.id.ivImage1);
            ivImage2 = view.findViewById(R.id.ivImage2);
            ivDelete = view.findViewById(R.id.ivDel);
            ivEdit = view.findViewById(R.id.ivEdit);
            cardView = view.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apartment = apartmentDetailList.get(getAdapterPosition());
                    Intent i = new Intent(context, ApartmentDetailsActivity.class);
                    i.putExtra("image1", apartment.getImage1Url());
                    i.putExtra("image2", apartment.getImage1Url());
                    i.putExtra("address", apartment.getAddress());
                    i.putExtra("desc", apartment.getDesc());
                    i.putExtra("price", apartment.getPrice());
                    i.putExtra("userId", apartment.getUserId());
                    context.startActivity(i);
                }
            });


        }


    }

    public ApartmentRenteeAdapter(Context mContext, List<Apartment> apartmentDetailList) {
        this.context = mContext;
        this.apartmentDetailList = apartmentDetailList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apartment, parent, false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        apartment = apartmentDetailList.get(position);
        holder.tvDesc.setText(String.format(apartment.getDesc()));
        holder.tvPrice.setText(String.format(apartment.getPrice()));
        holder.tvAddress.setText(apartment.getAddress());
        holder.ivDelete.setVisibility(View.GONE);
        holder.ivEdit.setVisibility(View.GONE);
        Picasso.with(context).load(apartment.getImage1Url()).into(holder.ivImage1);
        Picasso.with(context).load(apartment.getImage2Url()).into(holder.ivImage2);

    }

    @Override
    public int getItemCount() {
        return apartmentDetailList.size();
    }


}