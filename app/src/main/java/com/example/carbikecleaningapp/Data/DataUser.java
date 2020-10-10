package com.example.carbikecleaningapp.Data;

public class DataUser {
    private String address, phone, name;

    public DataUser(String address, String phone, String name) {
        this.address = address;
        this.phone = phone;
        this.name = name;
    }

    public DataUser(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
