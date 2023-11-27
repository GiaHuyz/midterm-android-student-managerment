package com.example.qlsv.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private String id;
    private String name;
    private String email;
    private int age;
    private String phone;
    private String status;
    private String role;

    private Map<String, Object> studentInfo;

    public User() {
    }

    public User(String id, String name, String email, int age, String phone, String status, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.status = status;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(Map<String, Object> studentInfo) {
        this.studentInfo = studentInfo;
    }

    public String getStudentId() {
        if (studentInfo != null) {
            return (String) studentInfo.get("studentId");
        }
        return null;
    }

    public List<Map<String, Object>> getCertificates() {
        if (studentInfo != null) {
            return (List<Map<String, Object>>) studentInfo.get("certificates");
        }
        return null;
    }
}
