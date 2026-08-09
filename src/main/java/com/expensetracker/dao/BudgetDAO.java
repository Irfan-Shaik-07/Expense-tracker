package com.expensetracker.dao;

import com.expensetracker.model.Budget;
import java.util.List;

public interface BudgetDAO {
    Budget saveOrUpdateBudget(Budget budget);
    Budget getBudgetByUserAndCategory(int userId, Integer categoryId, String periodType, int month, int year);
    List<Budget> getBudgetsByUser(int userId, int month, int year);
    boolean deleteBudget(int id, int userId);
}
