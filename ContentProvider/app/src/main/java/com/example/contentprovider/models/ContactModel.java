package com.example.contentprovider.models;

public class ContactModel {
    String contactName , contactImg, contact_no;

    public ContactModel(String contactName,String contactImg,String contact_no) {
        this.contactName = contactName;
        this.contactImg=contactImg;
        this.contact_no = contact_no;
    }

    public String getContactImg() {
        return contactImg;
    }

    public void setContactImg(String contactImg) {
        this.contactImg = contactImg;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
