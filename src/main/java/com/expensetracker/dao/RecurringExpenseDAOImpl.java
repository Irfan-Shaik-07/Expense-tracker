package com.expensetracker.dao;

import com.expensetracker.model.RecurringExpense;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecurringExpenseDAOImpl implements RecurringExpenseDAO {

    @Override
    public RecurringExpense createRecurringExpense(RecurringExpense item) {
        String sql = "INSERT INTO recurring_expenses (user_id, name, category_id, amount, frequency, next_due_date, is_active, payment_mode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, item.getUserId());
            pstmt.setString(2, item.getName());
            pstmt.setInt(3, item.getCategoryId());
            pstmt.setDouble(4, item.getAmount());
            pstmt.setString(5, item.getFrequency());
            pstmt.setDate(6, item.getNextDueDate());
            pstmt.setBoolean(7, item.isActive());
            pstmt.setString(8, item.getPaymentMode());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) item.setId(rs.getInt(1));
                }
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RecurringExpense> getRecurringExpensesByUser(int userId) {
        List<RecurringExpense> list = new ArrayList<>();
        String sql = "SELECT r.*, c.name as category_name FROM recurring_expenses r JOIN categories c ON r.category_id = c.id WHERE r.user_id = ? ORDER BY r.next_due_date ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteRecurringExpense(int id, int userId) {
        String sql = "DELETE FROM recurring_expenses WHERE id = ? AND user_id = ?";
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
    public List<RecurringExpense> getUpcomingReminders(int userId, int daysAhead) {
        List<RecurringExpense> list = new ArrayList<>();
        String sql = "SELECT r.*, c.name as category_name FROM recurring_expenses r JOIN categories c ON r.category_id = c.id WHERE r.user_id = ? AND r.is_active = TRUE AND r.next_due_date BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL ? DAY) ORDER BY r.next_due_date ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, daysAhead);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private RecurringExpense extractFromResultSet(ResultSet rs) throws SQLException {
        RecurringExpense re = new RecurringExpense();
        re.setId(rs.getInt("id"));
        re.setUserId(rs.getInt("user_id"));
        re.setName(rs.getString("name"));
        re.setCategoryId(rs.getInt("category_id"));
        re.setCategoryName(rs.getString("category_name"));
        re.setAmount(rs.getDouble("amount"));
        re.setFrequency(rs.getString("frequency"));
        re.setNextDueDate(rs.getDate("next_due_date"));
        re.setActive(rs.getBoolean("is_active"));
        re.setPaymentMode(rs.getString("payment_mode"));
        return re;
    }
}
