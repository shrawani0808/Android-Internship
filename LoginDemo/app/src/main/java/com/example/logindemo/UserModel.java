package com.example.logindemo;

public class UserModel {

    String uid, name,email,phone;
    public UserModel(String uid,String name,String email,String phone){
        this.uid=uid;
        this.name=name;
        this.email=email;
        this.phone=phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
