package com.expensetracker.dao;

import com.expensetracker.model.Goal;
import java.util.List;

public interface GoalDAO {
    Goal createGoal(Goal goal);
    Goal getGoalById(int id);
    boolean updateGoal(Goal goal);
    boolean updateSavedAmount(int goalId, int userId, double addAmount);
    boolean deleteGoal(int id, int userId);
    List<Goal> getGoalsByUser(int userId);
}
