package com.expensetracker.dao;

import com.expensetracker.model.Expense;
import java.util.List;
import java.util.Map;

public interface ExpenseDAO {
    Expense createExpense(Expense expense);
    Expense getExpenseById(int id);
    boolean updateExpense(Expense expense);
    boolean deleteExpense(int id, int userId);
    
    List<Expense> getExpensesByUserId(int userId);
    List<Expense> getExpensesByUserIdAndMonth(int userId, int month, int year);
    List<Expense> searchExpenses(int userId, String query, Integer categoryId, String paymentMode, Double minAmount, Double maxAmount);
    
    // Analytics & Metrics
    double getTotalExpensesByMonth(int userId, int month, int year);
    double getTotalExpensesByYear(int userId, int year);
    double getAverageMonthlyExpense(int userId, int year);
    double getHighestExpense(int userId, int month, int year);
    double getLowestExpense(int userId, int month, int year);
    
    Map<String, Double> getCategoryWiseExpenseSum(int userId, int month, int year);
    Map<Integer, Double> getMonthlyExpenseTrend(int userId, int year);
    List<Expense> getRecentTransactions(int userId, int limit);
}
