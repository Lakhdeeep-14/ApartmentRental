package com.example.rentatease.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rentatease.R;
import com.example.rentatease.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupFragment extends Fragment {

    DatabaseReference databaseUser;
    MaterialButton signupBtn;
    TextInputEditText fname_text;
    TextInputEditText pnumber_edtTxt;
    TextInputEditText email_edtTxt;
    TextInputEditText paswd_edtTxt;
    RadioGroup radioGrp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.signup_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fname_text = view.findViewById(R.id.fname_text);
        email_edtTxt = view.findViewById(R.id.email_edtTxt);
        pnumber_edtTxt = view.findViewById(R.id.pnumber_edtTxt);
        paswd_edtTxt = view.findViewById(R.id.paswd_edtTxt);
        radioGrp = view.findViewById(R.id.radioGrp);
        signupBtn = view.findViewById(R.id.signupBtn);

        int userId = radioGrp.getCheckedRadioButtonId();
        RadioButton radioButton = view.findViewById(userId);
        String userRadio =radioButton.getText().toString();

        signupBtn.setOnClickListener(
                v -> {
                    addUserToDatabase(fname_text.getText().toString() , pnumber_edtTxt.getText().toString() , email_edtTxt.getText().toString() ,
                            paswd_edtTxt.getText().toString() , userRadio);
                });
    }

    private void addUserToDatabase(String fname, String phNumber, String email, String passwrd , String userType){
        String id = databaseUser.push().getKey();
        User user = new User(id, fname, phNumber, email, passwrd, userType);



        databaseUser.child(id).setValue(user , (error, ref) -> {
            if(error == null){
                Toast.makeText(getContext(), "User Sign up success", Toast.LENGTH_LONG).show();
            }
            System.err.println("Value was set. Error = "+error);
            // Or: throw error.toException();
        });
    }
}