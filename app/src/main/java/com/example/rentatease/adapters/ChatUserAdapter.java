package com.example.rentatease.adapters;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.activities.ChatActivity;
import com.example.rentatease.model.Chat;

import java.util.List;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

    private Chat chat;
    private Context context;
    private List<Chat> chatList;
    String email;


    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView tvUser, tvTitle, tvAddress;
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            tvUser = view.findViewById(R.id.tvFromUser);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvAddress = view.findViewById(R.id.tvAddress);
            cardView = view.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chat = chatList.get(getAdapterPosition());
                    Intent i = new Intent(context, ChatActivity.class);
                    i.putExtra("ownerId", chat.getMsgFrom());
                    i.putExtra("apartmentId", chat.getApartmentId());
                    i.putExtra("title", chat.getTitle());
                    i.putExtra("address", chat.getAddress());
                    context.startActivity(i);
                }
            });


        }


    }

    public ChatUserAdapter(Context mContext, List<Chat> chatList, String email) {
        this.context = mContext;
        this.chatList = chatList;
        this.email = email;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        chat = chatList.get(position);


        holder.tvUser.setText(chat.getMsgFromName());
        holder.tvTitle.setText(chat.getTitle());
        holder.tvAddress.setText(chat.getAddress());

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


}