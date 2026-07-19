package com.example.contentprovider.models;

public class ContactModel {
    String contactName , contactImg;

    public ContactModel(String contactName,String contactImg) {
        this.contactName = contactName;
        this.contactImg=contactImg;
    }

    public String getContactImg() {
        return contactImg;
    }

    public void setContactImg(String contactImg) {
        this.contactImg = contactImg;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
