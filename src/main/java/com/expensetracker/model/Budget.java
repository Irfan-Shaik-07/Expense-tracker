package com.expensetracker.model;

import java.io.Serializable;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private Integer categoryId; // Nullable for overall budget
    private String categoryName;
    private double amount;
    private String periodType; // MONTHLY or YEARLY
    private Integer month;
    private int year;
    private double spentAmount; // Calculated dynamically

    public Budget() {
        this.periodType = "MONTHLY";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPeriodType() { return periodType; }
    public void setPeriodType(String periodType) { this.periodType = periodType; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getSpentAmount() { return spentAmount; }
    public void setSpentAmount(double spentAmount) { this.spentAmount = spentAmount; }

    public double getPercentageUsed() {
        if (amount <= 0) return 0;
        return Math.min(100.0, (spentAmount / amount) * 100.0);
    }
}
