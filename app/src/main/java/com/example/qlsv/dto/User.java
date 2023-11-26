package com.example.qlsv.dto;


public class User {
    private String name;
    private int age;
    private String phone;
    private String status;

    public User() {
    }

    public User(String name, int age, String phone, String status) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
