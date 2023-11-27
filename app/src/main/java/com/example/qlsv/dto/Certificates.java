package com.example.qlsv.dto;

public class Certificates {
    private String title;
    private String issueDate;
    private String expiryDate;

    public Certificates(String title, String issueDate, String expiryDate) {
        this.title = title;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
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
