package com.example.rentatease.model;

public class ApartmentDetail {

    private User user;
    private int price;
    private String description;
    private String address;
    private String location;

    public ApartmentDetail(User user, int price, String description, String address, String location) {
        this.user = user;
        this.price = price;
        this.description = description;
        this.address = address;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
