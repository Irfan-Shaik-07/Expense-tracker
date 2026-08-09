package com.expensetracker.dao;

import com.expensetracker.model.Budget;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAOImpl implements BudgetDAO {

    @Override
    public Budget saveOrUpdateBudget(Budget budget) {
        String sql = "INSERT INTO budgets (user_id, category_id, amount, period_type, month, year) VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE amount = VALUES(amount)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, budget.getUserId());
            if (budget.getCategoryId() != null && budget.getCategoryId() > 0) {
                pstmt.setInt(2, budget.getCategoryId());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            pstmt.setDouble(3, budget.getAmount());
            pstmt.setString(4, budget.getPeriodType());
            if (budget.getMonth() != null) pstmt.setInt(5, budget.getMonth()); else pstmt.setNull(5, java.sql.Types.INTEGER);
            pstmt.setInt(6, budget.getYear());

            pstmt.executeUpdate();
            return budget;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Budget getBudgetByUserAndCategory(int userId, Integer categoryId, String periodType, int month, int year) {
        String sql = "SELECT b.*, c.name as category_name FROM budgets b LEFT JOIN categories c ON b.category_id = c.id WHERE b.user_id = ? AND (b.category_id = ? OR (b.category_id IS NULL AND ? IS NULL)) AND b.period_type = ? AND (b.month = ? OR b.month IS NULL) AND b.year = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            if (categoryId != null) pstmt.setInt(2, categoryId); else pstmt.setNull(2, Types.INTEGER);
            if (categoryId != null) pstmt.setInt(3, categoryId); else pstmt.setNull(3, Types.INTEGER);
            pstmt.setString(4, periodType);
            pstmt.setInt(5, month);
            pstmt.setInt(6, year);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractBudgetFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Budget> getBudgetsByUser(int userId, int month, int year) {
        List<Budget> list = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM budgets b LEFT JOIN categories c ON b.category_id = c.id WHERE b.user_id = ? AND (b.month = ? OR b.month IS NULL) AND b.year = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractBudgetFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteBudget(int id, int userId) {
        String sql = "DELETE FROM budgets WHERE id = ? AND user_id = ?";
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

    private Budget extractBudgetFromResultSet(ResultSet rs) throws SQLException {
        Budget b = new Budget();
        b.setId(rs.getInt("id"));
        b.setUserId(rs.getInt("user_id"));
        int catId = rs.getInt("category_id");
        if (!rs.wasNull()) b.setCategoryId(catId);
        b.setCategoryName(rs.getString("category_name"));
        b.setAmount(rs.getDouble("amount"));
        b.setPeriodType(rs.getString("period_type"));
        int m = rs.getInt("month");
        if (!rs.wasNull()) b.setMonth(m);
        b.setYear(rs.getInt("year"));
        return b;
    }
}
