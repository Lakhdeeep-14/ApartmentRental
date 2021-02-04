package com.example.rentatease.model;

public class ApartmentDetail {
    private User user;
    private int price;
    private String description;
    private String address;

    public ApartmentDetail(User user, int price, String description, String address) {
        this.user = user;
        this.price = price;
        this.description = description;
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
}
