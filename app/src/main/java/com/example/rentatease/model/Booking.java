package com.example.rentatease.model;


public class Booking {

    String bookingId, fromDate, toDate, userId, apartmentId, bookingDate, ownerId, title, address, price, bookedBy;
    boolean isChecked;

    public Booking() {
    }

    public Booking(String bookingId, String fromDate, String toDate, String userId, String apartmentId, String bookingDate, String ownerId, String title, String address, String price, String bookedBy, boolean isChecked) {
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.userId = userId;
        this.apartmentId = apartmentId;
        this.bookingDate = bookingDate;
        this.ownerId = ownerId;
        this.title = title;
        this.address = address;
        this.price = price;
        this.bookedBy = bookedBy;
        this.isChecked=isChecked;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
