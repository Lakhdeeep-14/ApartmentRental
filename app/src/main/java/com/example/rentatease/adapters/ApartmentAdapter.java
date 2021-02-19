package com.example.rentatease.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.activities.UpdateApartmentActivity;
import com.example.rentatease.model.Apartment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.ViewHolder> {


    private Context context;
    private List<Apartment> apartmentDetailList;
    Apartment apartment;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvDesc, tvPrice, tvAddress, tvTitle;
        AppCompatImageView ivImage1, ivImage2;
        ImageView ivDelete, ivEdit;


        ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvAddress = view.findViewById(R.id.tvAddress);
            ivImage1 = view.findViewById(R.id.ivImage1);
            ivImage2 = view.findViewById(R.id.ivImage2);
            ivDelete = view.findViewById(R.id.ivDel);
            ivEdit = view.findViewById(R.id.ivEdit);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apartment = apartmentDetailList.get(getAdapterPosition());
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_appartments/" + apartment.getApartmentId());
                    databaseReference.removeValue();
                }
            });
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apartment = apartmentDetailList.get(getAdapterPosition());
                    Intent intent = new Intent(context, UpdateApartmentActivity.class);
                    intent.putExtra("title", apartment.getTitle());
                    intent.putExtra("apartmentId", apartment.getApartmentId());
                    intent.putExtra("price", apartment.getPrice());
                    intent.putExtra("desc", apartment.getDesc());
                    intent.putExtra("address", apartment.getAddress());
                    intent.putExtra("image1", apartment.getImage1Url());
                    intent.putExtra("image2", apartment.getImage2Url());
                    context.startActivity(intent);
                }
            });


        }


    }

    public ApartmentAdapter(Context mContext, List<Apartment> apartmentDetailList) {
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
        holder.tvTitle.setText(apartment.getTitle());

        Log.e("URL1", apartment.getImage1Url());
        Log.e("URL2", apartment.getImage2Url());
        Picasso.with(context).load(apartment.getImage1Url()).into(holder.ivImage1);
        Picasso.with(context).load(apartment.getImage2Url()).into(holder.ivImage2);

    }

    @Override
    public int getItemCount() {
        return apartmentDetailList.size();
    }


}