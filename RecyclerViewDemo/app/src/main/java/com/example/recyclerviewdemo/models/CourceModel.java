package com.example.recyclerviewdemo.models;

public class CourceModel {
    String courceTitle,courcePrice,courceImage;
    public CourceModel(){}
    public CourceModel(String courceImage,String courceTitle,String courcePrice){

        this.courceImage=courceImage;
        this.courceTitle=courceTitle;
        this.courcePrice=courcePrice;

    }

    public String getCourceTitle() {
        return courceTitle;
    }

    public void setCourceTitle(String courceTitle) {
        this.courceTitle = courceTitle;
    }

    public String getCourcePrice() {
        return courcePrice;
    }

    public void setCourcePrice(String courcePrice) {
        this.courcePrice = courcePrice;
    }

    public String getCourceImage() {
        return courceImage;
    }

    public void setCourceImage(String courceImage) {
        this.courceImage = courceImage;
    }
}
