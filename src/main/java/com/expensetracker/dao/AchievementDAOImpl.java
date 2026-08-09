package com.expensetracker.dao;

import com.expensetracker.model.Achievement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementDAOImpl implements AchievementDAO {

    @Override
    public boolean unlockBadge(int userId, String badgeKey, String badgeName, String description, String iconClass) {
        String sql = "INSERT INTO achievements (user_id, badge_key, badge_name, description, icon_class) VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE unlocked_at = unlocked_at";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, badgeKey);
            pstmt.setString(3, badgeName);
            pstmt.setString(4, description);
            pstmt.setString(5, iconClass);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Achievement> getAchievementsByUser(int userId) {
        List<Achievement> list = new ArrayList<>();
        String sql = "SELECT * FROM achievements WHERE user_id = ? ORDER BY unlocked_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Achievement a = new Achievement();
                    a.setId(rs.getInt("id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setBadgeKey(rs.getString("badge_key"));
                    a.setBadgeName(rs.getString("badge_name"));
                    a.setDescription(rs.getString("description"));
                    a.setIconClass(rs.getString("icon_class"));
                    a.setUnlockedAt(rs.getTimestamp("unlocked_at"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean hasBadge(int userId, String badgeKey) {
        String sql = "SELECT COUNT(*) FROM achievements WHERE user_id = ? AND badge_key = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, badgeKey);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
