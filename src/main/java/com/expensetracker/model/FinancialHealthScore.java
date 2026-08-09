package com.expensetracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FinancialHealthScore implements Serializable {
    private static final long serialVersionUID = 1L;

    private int overallScore; // 0 - 100
    private int savingsScore; // 0 - 25
    private int budgetAdherenceScore; // 0 - 25
    private int spendingConsistencyScore; // 0 - 25
    private int goalProgressScore; // 0 - 25
    private String statusLabel; // Excellent, Good, Fair, Needs Attention
    private String statusColor;
    private List<String> recommendations;

    public FinancialHealthScore() {
        this.recommendations = new ArrayList<>();
    }

    public int getOverallScore() { return overallScore; }
    public void setOverallScore(int overallScore) { this.overallScore = overallScore; }

    public int getSavingsScore() { return savingsScore; }
    public void setSavingsScore(int savingsScore) { this.savingsScore = savingsScore; }

    public int getBudgetAdherenceScore() { return budgetAdherenceScore; }
    public void setBudgetAdherenceScore(int budgetAdherenceScore) { this.budgetAdherenceScore = budgetAdherenceScore; }

    public int getSpendingConsistencyScore() { return spendingConsistencyScore; }
    public void setSpendingConsistencyScore(int spendingConsistencyScore) { this.spendingConsistencyScore = spendingConsistencyScore; }

    public int getGoalProgressScore() { return goalProgressScore; }
    public void setGoalProgressScore(int goalProgressScore) { this.goalProgressScore = goalProgressScore; }

    public String getStatusLabel() { return statusLabel; }
    public void setStatusLabel(String statusLabel) { this.statusLabel = statusLabel; }

    public String getStatusColor() { return statusColor; }
    public void setStatusColor(String statusColor) { this.statusColor = statusColor; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    public void addRecommendation(String rec) { this.recommendations.add(rec); }
}
