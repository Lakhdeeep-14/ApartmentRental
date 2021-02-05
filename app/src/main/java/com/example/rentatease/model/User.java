package com.example.rentatease.model;

import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String fName;
    private String phoneNumber;
    private String email;
    private String password;
    private String userType;

    public User(){

    }

    public User(String userId, String fName, String phoneNumber, String email, String password, String userType) {
        this.userId = userId;
        this.fName = fName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
