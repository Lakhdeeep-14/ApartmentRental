package com.example.rentatease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rentatease.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    MaterialTextView signup;
    MaterialButton loginBtn;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.signup);
        loginBtn = findViewById(R.id.loginBtn);
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        signup.setOnClickListener(
                v -> {
                    Intent intent = new Intent(LoginActivity.this, Signup.class);
                    startActivity(intent);
                });

        loginBtn.setOnClickListener(
                v -> {
                    checkForLogin();
                });
    }

    public void checkForLogin(){
        TextInputEditText email = findViewById(R.id.email_text);
        TextInputEditText psd = findViewById(R.id.paswd_edtTxt);

        String emailText = email.getText().toString().trim();
        String pass = psd.getText().toString().trim();

        Query query = databaseUser.orderByChild("email").equalTo(emailText);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        User userObj = user.getValue(User.class);

                        if (userObj.getPassword().equals(pass)) {
                            Toast.makeText(LoginActivity.this, "Welcome " + userObj.getfName() , Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(this, MainActivity.class);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Password is wrong..", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*Query checkUser = databaseUser.orderByChild("email").equalTo(emailText);

        checkUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }

}