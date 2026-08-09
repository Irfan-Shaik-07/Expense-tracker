package com.expensetracker.dao;

import com.expensetracker.model.RecurringExpense;
import java.util.List;

public interface RecurringExpenseDAO {
    RecurringExpense createRecurringExpense(RecurringExpense item);
    List<RecurringExpense> getRecurringExpensesByUser(int userId);
    boolean deleteRecurringExpense(int id, int userId);
    List<RecurringExpense> getUpcomingReminders(int userId, int daysAhead);
}
