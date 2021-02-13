package com.example.rentatease.model;

public class Users {

    private String userId, name, email, mobile, address, city, type;


    public Users() {
    }

    public Users(String userId, String name, String email, String mobile, String address, String city, String type) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.city = city;
        this.type = type;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
