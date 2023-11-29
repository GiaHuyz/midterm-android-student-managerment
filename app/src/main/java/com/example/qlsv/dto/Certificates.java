package com.example.qlsv.dto;

import java.io.Serializable;

public class Certificates implements Serializable {
    private String id;
    private String title;
    private String issueDate;
    private String expiryDate;

    public Certificates() {}

    public Certificates(String title, String issueDate, String expiryDate) {
        this.title = title;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
