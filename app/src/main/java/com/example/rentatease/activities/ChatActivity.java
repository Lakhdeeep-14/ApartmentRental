package com.example.rentatease.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.adapters.ApartmentAdapter;
import com.example.rentatease.adapters.ChatAdapter;
import com.example.rentatease.model.Apartment;
import com.example.rentatease.model.Chat;
import com.example.rentatease.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.rentatease.Const.UserId;

public class ChatActivity extends AppCompatActivity {

    EditText etMessage;
    ImageView ivSend;
    String ownerId, userId, ownerName, userName;

    private RecyclerView recyclerView;
    private List<Chat> chatList;
    private ChatAdapter adapter;
    boolean isChatExist = false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ownerId = getIntent().getStringExtra("ownerId");
        etMessage = findViewById(R.id.etMessage);
        ivSend = findViewById(R.id.ivSend);

        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Users users = dataSnapshot.getValue(Users.class);

                    if (TextUtils.equals(users.getUserId(), userId))
                        userName = users.getName();

                    if (TextUtils.equals(users.getUserId(), ownerId))
                        ownerName = users.getName();


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


        DatabaseReference databaseReferenceChats = FirebaseDatabase.getInstance().getReference("chats");

        databaseReferenceChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Chat chat = dataSnapshot.getValue(Chat.class);


                    if ((TextUtils.equals(chat.getMsgFrom(), userId) && TextUtils.equals(chat.getMsgTo(), ownerId)) ||
                            (TextUtils.equals(chat.getMsgTo(),userId) && TextUtils.equals(chat.getMsgFrom(), ownerId))) {
                        isChatExist = true;
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etMessage.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    etMessage.setError("");
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = new Date();
                if (!isChatExist) {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("chats");
                    String chatId = mDatabase.push().getKey();
                    Chat chat = new Chat(chatId, userId, ownerId, userName, ownerName, "Chat Start", formatter.format(date));
                    mDatabase.child(Objects.requireNonNull(chatId)).setValue(chat);
                    isChatExist = true;
                }


                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("chat_details");
                String chat_detailsId = mDatabase.push().getKey();
                Chat chat = new Chat(chat_detailsId,userId, ownerId,userName, ownerName, msg, formatter.format(date));
                mDatabase.child(Objects.requireNonNull(chat_detailsId)).setValue(chat);
                etMessage.setText("");
            }
        });
        chatList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerListView);
        recyclerView.setHasFixedSize(true);
        adapter = new ChatAdapter(ChatActivity.this, chatList, userId);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReferenceChat = FirebaseDatabase.getInstance().getReference("chat_details");

        databaseReferenceChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                chatList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Chat chat = dataSnapshot.getValue(Chat.class);


                    if ((TextUtils.equals(chat.getMsgFrom(), userId) && TextUtils.equals(chat.getMsgTo(), ownerId)) ||
                            (TextUtils.equals(chat.getMsgTo(), userId) && TextUtils.equals(chat.getMsgFrom(), ownerId)))
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
