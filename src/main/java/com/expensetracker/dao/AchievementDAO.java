package com.expensetracker.dao;

import com.expensetracker.model.Achievement;
import java.util.List;

public interface AchievementDAO {
    boolean unlockBadge(int userId, String badgeKey, String badgeName, String description, String iconClass);
    List<Achievement> getAchievementsByUser(int userId);
    boolean hasBadge(int userId, String badgeKey);
}
