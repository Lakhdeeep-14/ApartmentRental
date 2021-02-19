package com.example.rentatease.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.adapters.ChatUserAdapter;
import com.example.rentatease.model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.rentatease.Const.UserId;
import static java.security.AccessController.getContext;

public class OwnerChatsActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<Chat> chatList;
    private ChatUserAdapter adapter;
    SharedPreferences sharedPreferences;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_chats);
        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");
        chatList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setHasFixedSize(true);
        adapter = new ChatUserAdapter(OwnerChatsActivity.this, chatList, userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(OwnerChatsActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                chatList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Chat chat = dataSnapshot.getValue(Chat.class);

                    if ((TextUtils.equals(chat.getMsgTo(), userId)))
                        chatList.add(chat);


                }


                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
}
