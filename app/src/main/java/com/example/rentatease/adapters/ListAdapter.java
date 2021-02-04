package com.example.rentatease.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rentatease.R;
import com.example.rentatease.model.ApartmentDetail;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ApartmentDetail[] listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(ApartmentDetail[] listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_apt_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ApartmentDetail myListData = listdata[position];

        holder.priceTV.setText(listdata[position].getPrice());
        holder.textDescription.setText(listdata[position].getAddress());


        holder.removeApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                //Delete Apartment
            }
        });

//        holder.textView.setText(listdata[position].getDescription());
//        holder.imageView.setImageResource(listdata[position].getImgId());
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView, removeApt;
        public TextView priceTV;
        public TextView textDescription;
        public ConstraintLayout itemLL;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.priceTV = (TextView) itemView.findViewById(R.id.priceTV);
            this.textDescription = (TextView) itemView.findViewById(R.id.textDescription);
            this.removeApt = (ImageView) itemView.findViewById(R.id.removeApt);

            itemLL = (ConstraintLayout) itemView.findViewById(R.id.itemLL);
        }
    }
}