package com.expensetracker.service;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.model.FinancialHealthScore;
import com.expensetracker.model.Expense;

import java.util.*;

public class AnalyticsService {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();

    public Map<String, Object> calculateDashboardMetrics(int userId, int month, int year, double monthlyIncome) {
        Map<String, Object> metrics = new HashMap<>();

        double monthlyTotal = expenseDAO.getTotalExpensesByMonth(userId, month, year);
        double yearlyTotal = expenseDAO.getTotalExpensesByYear(userId, year);
        double monthlyAvg = expenseDAO.getAverageMonthlyExpense(userId, year);
        double highest = expenseDAO.getHighestExpense(userId, month, year);
        double lowest = expenseDAO.getLowestExpense(userId, month, year);
        
        // Daily & Weekly Averages for current month
        Calendar cal = Calendar.getInstance();
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        double dailyAvg = monthlyTotal / Math.max(1, daysInMonth);
        double weeklyAvg = monthlyTotal / 4.0;
        
        double savings = Math.max(0, monthlyIncome - monthlyTotal);
        double savingsPercentage = monthlyIncome > 0 ? (savings / monthlyIncome) * 100.0 : 0.0;

        metrics.put("monthlyTotal", monthlyTotal);
        metrics.put("yearlyTotal", yearlyTotal);
        metrics.put("monthlyAverage", monthlyAvg);
        metrics.put("highestExpense", highest);
        metrics.put("lowestExpense", lowest);
        metrics.put("dailyAverage", dailyAvg);
        metrics.put("weeklyAverage", weeklyAvg);
        metrics.put("savings", savings);
        metrics.put("savingsPercentage", savingsPercentage);

        return metrics;
    }

    public List<Double> generateExpenseForecast(int userId, int year) {
        Map<Integer, Double> trend = expenseDAO.getMonthlyExpenseTrend(userId, year);
        List<Double> forecast = new ArrayList<>();
        
        // Calculate simple moving average / linear trend prediction for upcoming 3 months
        double sum = 0.0;
        int count = 0;
        for (Map.Entry<Integer, Double> entry : trend.entrySet()) {
            if (entry.getValue() > 0) {
                sum += entry.getValue();
                count++;
            }
        }
        double avg = count > 0 ? sum / count : 15000.0;

        // Add historical 12 months + 3 estimated forecast months
        for (int m = 1; m <= 12; m++) {
            forecast.add(trend.getOrDefault(m, 0.0));
        }
        // Month 13, 14, 15 estimate (Forecast)
        forecast.add(avg * 1.05); // Estimate 5% seasonal trend bump
        forecast.add(avg * 0.98);
        forecast.add(avg * 1.02);

        return forecast;
    }

    public FinancialHealthScore calculateFinancialHealthScore(int userId, double monthlyIncome, double totalSpent, double totalBudget, double goalsProgressAvg) {
        FinancialHealthScore health = new FinancialHealthScore();

        // 1. Savings Score (0 - 25)
        double savingsRatio = monthlyIncome > 0 ? Math.max(0, (monthlyIncome - totalSpent) / monthlyIncome) : 0;
        int savingsScore = (int) Math.min(25, Math.round(savingsRatio * 100 * 0.8));

        // 2. Budget Adherence Score (0 - 25)
        int budgetScore = 25;
        if (totalBudget > 0) {
            double budgetRatio = totalSpent / totalBudget;
            if (budgetRatio <= 0.8) budgetScore = 25;
            else if (budgetRatio <= 1.0) budgetScore = 20;
            else if (budgetRatio <= 1.2) budgetScore = 10;
            else budgetScore = 5;
        }

        // 3. Spending Consistency (0 - 25)
        int consistencyScore = 20; // Base baseline score for regular tracking

        // 4. Goal Progress (0 - 25)
        int goalScore = (int) Math.min(25, Math.round((goalsProgressAvg / 100.0) * 25));

        int overall = savingsScore + budgetScore + consistencyScore + goalScore;
        health.setOverallScore(overall);
        health.setSavingsScore(savingsScore);
        health.setBudgetAdherenceScore(budgetScore);
        health.setSpendingConsistencyScore(consistencyScore);
        health.setGoalProgressScore(goalScore);

        if (overall >= 80) {
            health.setStatusLabel("Excellent");
            health.setStatusColor("#2ECC71");
            health.addRecommendation("Your financial discipline is top notch! Consider investing surplus into SIPs or Index Funds.");
            health.addRecommendation("Maintain your 6-month emergency fund.");
        } else if (overall >= 60) {
            health.setStatusLabel("Good");
            health.setStatusColor("#00CFCF");
            health.addRecommendation("Great job! Try to trim dining and online shopping to bump savings to 25%.");
            health.addRecommendation("Review recurring monthly subscriptions.");
        } else if (overall >= 40) {
            health.setStatusLabel("Fair");
            health.setStatusColor("#F39C12");
            health.addRecommendation("Your spending is close to your monthly budget limit. Set category alert triggers at 75%.");
            health.addRecommendation("Build an emergency fund before making major impulse purchases.");
        } else {
            health.setStatusLabel("Needs Attention");
            health.setStatusColor("#FF6B6B");
            health.addRecommendation("Expenses have exceeded your monthly income or target budget. Pause non-essential purchases.");
            health.addRecommendation("Prioritize paying off high-interest debt and setting up a strict monthly budget.");
        }

        return health;
    }
}
