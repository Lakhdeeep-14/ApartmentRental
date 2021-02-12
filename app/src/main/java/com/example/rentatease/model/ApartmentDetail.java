package com.example.rentatease.model;

import android.net.Uri;

public class ApartmentDetail {

    private String aptId;
    private User user;
    private String price;
    private String description;
    private String address;
    private Uri img1;
    private Uri img2;

    public ApartmentDetail(String aptId, User user, String price, String description, String address) {
        this.aptId = aptId;
        this.user = user;
        this.price = price;
        this.description = description;
        this.address = address;
    }

    public ApartmentDetail(String aptId, User user, String price, String description, String address, Uri img1, Uri img2) {
        this.aptId = aptId;
        this.user = user;
        this.price = price;
        this.description = description;
        this.address = address;
        this.img1 = img1;
        this.img2 = img2;
    }

    public String getAptId() {
        return aptId;
    }

    public void setAptId(String aptId) {
        this.aptId = aptId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Uri getImg1() {
        return img1;
    }

    public void setImg1(Uri img1) {
        this.img1 = img1;
    }

    public Uri getImg2() {
        return img2;
    }

    public void setImg2(Uri img2) {
        this.img2 = img2;
    }
}
