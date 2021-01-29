package com.example.rentatease.model;

public class User {
    String fName;
    String phoneNumber;
    String email;
    String password;
    String userType;

    public User() {
    }

    public User(String fName, String phoneNumber, String email, String password, String userType) {
        this.fName = fName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.userType = userType;
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
