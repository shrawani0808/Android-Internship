package com.example.contentprovider.models;

public class ContactModel {
    String contactName;

    public ContactModel(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
