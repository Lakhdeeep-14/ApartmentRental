package com.example.rentatease.model;

import android.net.Uri;

public class ApartmentDetail {

    private String aptId;
    private User user;
    private String price;
    private String description;
    private String address;
    private String img1path;
    private String img2path;

    public ApartmentDetail(String aptId, User user, String price, String description, String address) {
        this.aptId = aptId;
        this.user = user;
        this.price = price;
        this.description = description;
        this.address = address;
    }

    public ApartmentDetail(String aptId, User user, String price, String description, String address, String img1path, String img2path) {
        this.aptId = aptId;
        this.user = user;
        this.price = price;
        this.description = description;
        this.address = address;
        this.img1path = img1path;
        this.img2path = img2path;
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

    public String getImg1path() {
        return img1path;
    }

    public void setImg1path(String img1path) {
        this.img1path = img1path;
    }

    public String getImg2path() {
        return img2path;
    }

    public void setImg2path(String img2path) {
        this.img2path = img2path;
    }
}
