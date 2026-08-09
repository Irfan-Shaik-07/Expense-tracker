package com.expensetracker.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String fullName;
    private String email;
    private String passwordHash;
    private String phoneNumber;
    private String avatarUrl;
    private String preferredLanguage;
    private String preferredCurrency;
    private double monthlyIncome;
    private String occupation;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User() {
        this.preferredLanguage = "en";
        this.preferredCurrency = "INR";
        this.avatarUrl = "assets/images/default-avatar.png";
    }

    public User(int id, String fullName, String email, String passwordHash, String phoneNumber,
                String avatarUrl, String preferredLanguage, String preferredCurrency,
                double monthlyIncome, String occupation) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.avatarUrl = avatarUrl;
        this.preferredLanguage = preferredLanguage;
        this.preferredCurrency = preferredCurrency;
        this.monthlyIncome = monthlyIncome;
        this.occupation = occupation;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getPreferredLanguage() { return preferredLanguage; }
    public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }

    public String getPreferredCurrency() { return preferredCurrency; }
    public void setPreferredCurrency(String preferredCurrency) { this.preferredCurrency = preferredCurrency; }

    public double getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(double monthlyIncome) { this.monthlyIncome = monthlyIncome; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
