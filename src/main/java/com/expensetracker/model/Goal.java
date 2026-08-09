package com.expensetracker.model;

import java.io.Serializable;
import java.sql.Date;

public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String title;
    private double targetAmount;
    private double savedAmount;
    private Date targetDate;
    private String category;
    private String iconClass;
    private String status; // IN_PROGRESS, COMPLETED, CANCELLED

    public Goal() {
        this.status = "IN_PROGRESS";
        this.iconClass = "fa-bullseye";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }

    public double getSavedAmount() { return savedAmount; }
    public void setSavedAmount(double savedAmount) { this.savedAmount = savedAmount; }

    public Date getTargetDate() { return targetDate; }
    public void setTargetDate(Date targetDate) { this.targetDate = targetDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getIconClass() { return iconClass; }
    public void setIconClass(String iconClass) { this.iconClass = iconClass; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getRemainingAmount() {
        return Math.max(0, targetAmount - savedAmount);
    }

    public double getProgressPercentage() {
        if (targetAmount <= 0) return 0;
        return Math.min(100.0, (savedAmount / targetAmount) * 100.0);
    }
}
