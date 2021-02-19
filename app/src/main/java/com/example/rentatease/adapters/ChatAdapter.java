package com.example.rentatease.adapters;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.R;
import com.example.rentatease.model.Chat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Chat chat;
    private Context context;
    private List<Chat> chatList;
    String email;


    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayoutLeft, linearLayoutRight;
        public TextView tvLeftEmail, tvLeftMsg, tvLeftDate, tvRightEmail, tvRightMsg, tvRightDate;

        ViewHolder(View view) {
            super(view);
            linearLayoutLeft = view.findViewById(R.id.linearLayoutLeft);
            linearLayoutRight = view.findViewById(R.id.linearLayoutRight);
            tvLeftEmail = view.findViewById(R.id.tvLeftEmail);
            tvLeftMsg = view.findViewById(R.id.tvLeftMsg);
            tvLeftDate = view.findViewById(R.id.tvLeftDate);
            tvRightEmail = view.findViewById(R.id.tvRightEmail);
            tvRightMsg = view.findViewById(R.id.tvRightMsg);
            tvRightDate = view.findViewById(R.id.tvRightDate);

        }


    }

    public ChatAdapter(Context mContext, List<Chat> chatList, String email) {
        this.context = mContext;
        this.chatList = chatList;
        this.email = email;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        chat = chatList.get(position);

        if (TextUtils.equals(chat.getMsgFrom(), email)) {
            holder.linearLayoutLeft.setVisibility(View.GONE);
            holder.linearLayoutRight.setVisibility(View.VISIBLE);

            holder.tvRightEmail.setText(chat.getMsgFromName());
            holder.tvRightMsg.setText(chat.getMessage());
            holder.tvRightDate.setText(chat.getDatetime());

        } else {
            holder.linearLayoutLeft.setVisibility(View.VISIBLE);
            holder.linearLayoutRight.setVisibility(View.GONE);

            holder.tvLeftEmail.setText(chat.getMsgFromName());
            holder.tvLeftMsg.setText(chat.getMessage());
            holder.tvLeftDate.setText(chat.getDatetime());

        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


}