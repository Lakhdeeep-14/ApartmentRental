package com.example.rentatease.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.rentatease.Const;
import com.example.rentatease.R;
import com.example.rentatease.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.rentatease.Const.UserId;


public class LoginActivity extends AppCompatActivity {

    AppCompatButton btnLogin, btnSignUp;
    AppCompatEditText etEmail, etPassword;
    String email, password, userId;

    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    AppCompatTextView tvForgot;
    String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window mWindow = getWindow();
        mWindow.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        userId = sharedPreferences.getString(UserId, "0");


        if (!TextUtils.equals(userId, "0")) {
            userType = sharedPreferences.getString(Const.UserType, "");
            if (TextUtils.equals(userType, "Owner")) {
                Intent main = new Intent(LoginActivity.this, DashboardActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                finish();
            } else {
                Intent main = new Intent(LoginActivity.this, ApartmentListActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                finish();

            }


        }
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signup);
            }
        });
        tvForgot = findViewById(R.id.tvForgot);
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(forgot);
            }
        });
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("This field is empty !");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("This field is empty !");
                    return;
                }
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("all_users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            Users user = data.getValue(Users.class);

                            if (TextUtils.equals(user.getEmail(), email)) {

                                sharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(UserId, user.getUserId());
                                editor.putString(Const.Mobile, user.getMobile());
                                editor.putString(Const.Name, user.getName());
                                editor.putString(Const.UserType, user.getType());
                                editor.apply();

                                mAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    //  Log.d(TAG, "signInWithEmail:success");
                                                    FirebaseUser user = mAuth.getCurrentUser();
                                                    userType = sharedPreferences.getString(Const.UserType, "");
                                                    if (TextUtils.equals(userType, "Owner")) {
                                                        Intent main = new Intent(LoginActivity.this, DashboardActivity.class);
                                                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(main);
                                                        finish();
                                                    } else {
                                                        Intent main = new Intent(LoginActivity.this, ApartmentListActivity.class);
                                                        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(main);
                                                        finish();

                                                    }
                                                    Toast.makeText(LoginActivity.this, "Login successfully",
                                                            Toast.LENGTH_SHORT).show();
                                                    finish();


                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    //  Log.w(TAG, "signInWithEmail:failure", task.getException());
                                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();

                                                }

                                                // ...
                                            }
                                        });


                            } else {
                                Log.e("user", "not here");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });

            }
        });
    }
}
