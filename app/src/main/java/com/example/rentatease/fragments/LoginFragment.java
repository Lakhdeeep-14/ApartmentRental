package com.example.rentatease.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rentatease.R;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    MaterialTextView signupTxt;
    MaterialButton loginBtn;
    TextInputEditText email;
    TextInputEditText psd;
    DatabaseReference databaseUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        signupTxt = view.findViewById(R.id.signupTxt);
        loginBtn = view.findViewById(R.id.loginBtn);
        email = view.findViewById(R.id.email_text);
        psd = view.findViewById(R.id.paswd_edtTxt);

        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        signupTxt.setOnClickListener(
                v -> {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).
                            replace(R.id.fragment, new SignupFragment()).commit();
                });

        loginBtn.setOnClickListener(
                v -> {
                    checkForLogin();
                });
    }

    public void checkForLogin(){

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
                            if(userObj.getUserType().equals("Owner")){
                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).
                                        replace(R.id.fragment, new AddApartmentFragment()).commit();

                            }
                            Toast.makeText(getContext(), "Welcome " + userObj.getfName() , Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(this, MainActivity.class);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "User  not found", Toast.LENGTH_LONG).show();
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
