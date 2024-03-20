package com.example.myapplication1;

public class User {
    private int id;
    private String name;
    private String phone;
    private String img;

    public User(int id, String name, String phone, String img) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.img = img;
    }
    private User(){}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getImg() {
        return img;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
