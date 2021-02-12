package com.example.rentatease.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.model.ApartmentDetail;

import java.util.List;

public class ApartmentAdapter extends  RecyclerView.Adapter<ApartmentAdapter.ViewHolder> {


private Context context;
private List<ApartmentDetail> apartmentDetailList;
 String userType;


public class ViewHolder extends RecyclerView.ViewHolder {

    public AppCompatTextView tvDesc, tvPrice, tvAddress;


    ViewHolder(View view) {
        super(view);

        tvDesc = view.findViewById(R.id.tvDesc);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvAddress = view.findViewById(R.id.tvAddress);



    }


}

    public ApartmentAdapter(Context mContext, List<ApartmentDetail> apartmentDetailList, String userType) {
        this.context = mContext;
        this.apartmentDetailList = apartmentDetailList;
        this.userType = userType;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apartment, parent, false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {





    }

    @Override
    public int getItemCount() {
        return apartmentDetailList.size();
    }



}