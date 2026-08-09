package com.expensetracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String expenseName;
    private int categoryId;
    private String categoryName;
    private String categoryIcon;
    private String categoryColor;
    private String label;
    private double amount;
    private String currency;
    private double convertedAmount;
    private String paymentMode;
    private Date expenseDate;
    private int month;
    private int year;
    private String description;
    private String receiptPath;
    private boolean isRecurring;
    private Timestamp createdAt;

    public Expense() {
        this.currency = "INR";
        this.label = "General";
        this.paymentMode = "UPI";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getExpenseName() { return expenseName; }
    public void setExpenseName(String expenseName) { this.expenseName = expenseName; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getCategoryIcon() { return categoryIcon; }
    public void setCategoryIcon(String categoryIcon) { this.categoryIcon = categoryIcon; }

    public String getCategoryColor() { return categoryColor; }
    public void setCategoryColor(String categoryColor) { this.categoryColor = categoryColor; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public double getConvertedAmount() { return convertedAmount; }
    public void setConvertedAmount(double convertedAmount) { this.convertedAmount = convertedAmount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public Date getExpenseDate() { return expenseDate; }
    public void setExpenseDate(Date expenseDate) { this.expenseDate = expenseDate; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReceiptPath() { return receiptPath; }
    public void setReceiptPath(String receiptPath) { this.receiptPath = receiptPath; }

    public boolean isRecurring() { return isRecurring; }
    public void setRecurring(boolean recurring) { isRecurring = recurring; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
