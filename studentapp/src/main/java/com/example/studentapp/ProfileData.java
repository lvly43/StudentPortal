package com.example.studentapp;

public class ProfileData {
    String name;
    String mobileNumber;
    String address;
    String Image;
    String key;
    String email;

    public ProfileData(){

    }

    public ProfileData(String name, String mobileNumber, String address, String image, String key,String email) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.address = address;
        Image = image;
        this.key = key;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
