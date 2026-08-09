package com.expensetracker.dao;

import com.expensetracker.model.Goal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalDAOImpl implements GoalDAO {

    @Override
    public Goal createGoal(Goal goal) {
        String sql = "INSERT INTO goals (user_id, title, target_amount, saved_amount, target_date, category, icon_class, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, goal.getUserId());
            pstmt.setString(2, goal.getTitle());
            pstmt.setDouble(3, goal.getTargetAmount());
            pstmt.setDouble(4, goal.getSavedAmount());
            pstmt.setDate(5, goal.getTargetDate());
            pstmt.setString(6, goal.getCategory());
            pstmt.setString(7, goal.getIconClass());
            pstmt.setString(8, goal.getStatus());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) goal.setId(rs.getInt(1));
                }
                return goal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Goal getGoalById(int id) {
        String sql = "SELECT * FROM goals WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return extractGoalFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateGoal(Goal goal) {
        String sql = "UPDATE goals SET title = ?, target_amount = ?, saved_amount = ?, target_date = ?, category = ?, icon_class = ?, status = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, goal.getTitle());
            pstmt.setDouble(2, goal.getTargetAmount());
            pstmt.setDouble(3, goal.getSavedAmount());
            pstmt.setDate(4, goal.getTargetDate());
            pstmt.setString(5, goal.getCategory());
            pstmt.setString(6, goal.getIconClass());
            pstmt.setString(7, goal.getStatus());
            pstmt.setInt(8, goal.getId());
            pstmt.setInt(9, goal.getUserId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSavedAmount(int goalId, int userId, double addAmount) {
        String sql = "UPDATE goals SET saved_amount = saved_amount + ?, status = CASE WHEN (saved_amount + ?) >= target_amount THEN 'COMPLETED' ELSE status END WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, addAmount);
            pstmt.setDouble(2, addAmount);
            pstmt.setInt(3, goalId);
            pstmt.setInt(4, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteGoal(int id, int userId) {
        String sql = "DELETE FROM goals WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Goal> getGoalsByUser(int userId) {
        List<Goal> list = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE user_id = ? ORDER BY target_date ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractGoalFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Goal extractGoalFromResultSet(ResultSet rs) throws SQLException {
        Goal g = new Goal();
        g.setId(rs.getInt("id"));
        g.setUserId(rs.getInt("user_id"));
        g.setTitle(rs.getString("title"));
        g.setTargetAmount(rs.getDouble("target_amount"));
        g.setSavedAmount(rs.getDouble("saved_amount"));
        g.setTargetDate(rs.getDate("target_date"));
        g.setCategory(rs.getString("category"));
        g.setIconClass(rs.getString("icon_class"));
        g.setStatus(rs.getString("status"));
        return g;
    }
}
