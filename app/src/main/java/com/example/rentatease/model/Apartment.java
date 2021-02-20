package com.example.rentatease.model;


public class Apartment {

    String apartmentId, price, desc, address, userId, image1Url, image2Url, title;
    boolean isBooked;

    public Apartment() {
    }

    public Apartment(String apartmentId, String price, String desc, String address, String userId, String image1Url, String image2Url, String title, boolean isBooked) {
        this.apartmentId = apartmentId;
        this.price = price;
        this.desc = desc;
        this.address = address;
        this.userId = userId;
        this.image1Url = image1Url;
        this.image2Url = image2Url;
        this.title = title;
        this.isBooked = isBooked;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
