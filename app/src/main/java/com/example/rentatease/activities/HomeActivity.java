package com.example.rentatease.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.rentatease.R;
import com.example.rentatease.fragments.LoginFragment;

public class HomeActivity extends AppCompatActivity {

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addFragment(new LoginFragment());
    }

    public void addFragment(Fragment fragment){
        ft.add(R.id.fragment , fragment).addToBackStack(null);
        ft.commit();
    }

    public void replaceFragment(Fragment fragment){
        ft.replace(R.id.fragment , fragment).addToBackStack(null);
        ft.commit();
    }
}