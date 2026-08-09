package com.expensetracker.model;

import java.io.Serializable;
import java.sql.Date;

public class RecurringExpense implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userId;
    private String name;
    private int categoryId;
    private String categoryName;
    private double amount;
    private String frequency; // DAILY, WEEKLY, MONTHLY, YEARLY
    private Date nextDueDate;
    private boolean active;
    private String paymentMode;

    public RecurringExpense() {
        this.frequency = "MONTHLY";
        this.active = true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public Date getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(Date nextDueDate) { this.nextDueDate = nextDueDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
}
