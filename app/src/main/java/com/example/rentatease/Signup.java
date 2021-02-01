package com.example.rentatease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rentatease.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Signup extends AppCompatActivity {

    DatabaseReference databaseUser;
    MaterialButton signup;
    TextInputEditText fname_text;
    TextInputEditText pnumber_edtTxt;
    TextInputEditText email_edtTxt;
    TextInputEditText paswd_edtTxt;
    RadioGroup radioGrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        signup = findViewById(R.id.signupBtn);

        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        signup.setOnClickListener(
                v -> {
                    addUserToDatabase();
                });
    }

    private void addUserToDatabase(){
        fname_text = findViewById(R.id.fname_text);
        pnumber_edtTxt = findViewById(R.id.pnumber_edtTxt);
        email_edtTxt = findViewById(R.id.email_edtTxt);
        paswd_edtTxt = findViewById(R.id.paswd_edtTxt);
        radioGrp = findViewById(R.id.radioGrp);

        String id = databaseUser.push().getKey();

        int userId = radioGrp.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(userId);
        String userRadio =radioButton.getText().toString();

        User user = new User(fname_text.getText().toString() , pnumber_edtTxt.getText().toString() , email_edtTxt.getText().toString() ,
                paswd_edtTxt.getText().toString() , userRadio);

        databaseUser.child(id).setValue(user , (error, ref) -> {
            if(error == null){
                Toast.makeText(this, "User Sign up is successfully", Toast.LENGTH_LONG).show();
            }
            System.err.println("Value was set. Error = "+error);
            // Or: throw error.toException();
        });
    }
}