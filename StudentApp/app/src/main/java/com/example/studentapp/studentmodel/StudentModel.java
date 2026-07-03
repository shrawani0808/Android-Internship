package com.example.studentapp.studentmodel;

public class StudentModel {

    String id,name,course;
    int age,price;
    public StudentModel(){}
    public StudentModel(String id,String name,int age,String course,int price){
        this.id=id;
        this.name=name;
        this.age=age;
        this.course=course;
        this.price=price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
