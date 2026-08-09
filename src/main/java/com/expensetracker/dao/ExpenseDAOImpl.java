package com.expensetracker.dao;

import com.expensetracker.model.Expense;
import java.sql.*;
import java.util.*;

public class ExpenseDAOImpl implements ExpenseDAO {

    @Override
    public Expense createExpense(Expense expense) {
        String sql = "INSERT INTO expenses (user_id, expense_name, category_id, label, amount, currency, converted_amount, payment_mode, expense_date, month, year, description, receipt_path, is_recurring) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, expense.getUserId());
            pstmt.setString(2, expense.getExpenseName());
            pstmt.setInt(3, expense.getCategoryId());
            pstmt.setString(4, expense.getLabel());
            pstmt.setDouble(5, expense.getAmount());
            pstmt.setString(6, expense.getCurrency());
            pstmt.setDouble(7, expense.getConvertedAmount());
            pstmt.setString(8, expense.getPaymentMode());
            pstmt.setDate(9, expense.getExpenseDate());
            pstmt.setInt(10, expense.getMonth());
            pstmt.setInt(11, expense.getYear());
            pstmt.setString(12, expense.getDescription());
            pstmt.setString(13, expense.getReceiptPath());
            pstmt.setBoolean(14, expense.isRecurring());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        expense.setId(rs.getInt(1));
                    }
                }
                return expense;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Expense getExpenseById(int id) {
        String sql = "SELECT e.*, c.name as category_name, c.icon_class as category_icon, c.color_hex as category_color FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractExpenseFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        String sql = "UPDATE expenses SET expense_name = ?, category_id = ?, label = ?, amount = ?, currency = ?, converted_amount = ?, payment_mode = ?, expense_date = ?, month = ?, year = ?, description = ?, receipt_path = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, expense.getExpenseName());
            pstmt.setInt(2, expense.getCategoryId());
            pstmt.setString(3, expense.getLabel());
            pstmt.setDouble(4, expense.getAmount());
            pstmt.setString(5, expense.getCurrency());
            pstmt.setDouble(6, expense.getConvertedAmount());
            pstmt.setString(7, expense.getPaymentMode());
            pstmt.setDate(8, expense.getExpenseDate());
            pstmt.setInt(9, expense.getMonth());
            pstmt.setInt(10, expense.getYear());
            pstmt.setString(11, expense.getDescription());
            pstmt.setString(12, expense.getReceiptPath());
            pstmt.setInt(13, expense.getId());
            pstmt.setInt(14, expense.getUserId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteExpense(int id, int userId) {
        String sql = "DELETE FROM expenses WHERE id = ? AND user_id = ?";
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
    public List<Expense> getExpensesByUserId(int userId) {
        String sql = "SELECT e.*, c.name as category_name, c.icon_class as category_icon, c.color_hex as category_color FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? ORDER BY e.expense_date DESC, e.id DESC";
        return fetchExpenseList(sql, userId);
    }

    @Override
    public List<Expense> getExpensesByUserIdAndMonth(int userId, int month, int year) {
        String sql = "SELECT e.*, c.name as category_name, c.icon_class as category_icon, c.color_hex as category_color FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? AND e.month = ? AND e.year = ? ORDER BY e.expense_date DESC";
        List<Expense> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractExpenseFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Expense> searchExpenses(int userId, String query, Integer categoryId, String paymentMode, Double minAmount, Double maxAmount) {
        StringBuilder sql = new StringBuilder("SELECT e.*, c.name as category_name, c.icon_class as category_icon, c.color_hex as category_color FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (query != null && !query.trim().isEmpty()) {
            sql.append("AND (LOWER(e.expense_name) LIKE ? OR LOWER(c.name) LIKE ? OR LOWER(e.label) LIKE ? OR LOWER(e.description) LIKE ?) ");
            String q = "%" + query.trim().toLowerCase() + "%";
            params.add(q); params.add(q); params.add(q); params.add(q);
        }
        if (categoryId != null && categoryId > 0) {
            sql.append("AND e.category_id = ? ");
            params.add(categoryId);
        }
        if (paymentMode != null && !paymentMode.trim().isEmpty()) {
            sql.append("AND e.payment_mode = ? ");
            params.add(paymentMode);
        }
        if (minAmount != null) {
            sql.append("AND e.amount >= ? ");
            params.add(minAmount);
        }
        if (maxAmount != null) {
            sql.append("AND e.amount <= ? ");
            params.add(maxAmount);
        }

        sql.append("ORDER BY e.expense_date DESC");

        List<Expense> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractExpenseFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public double getTotalExpensesByMonth(int userId, int month, int year) {
        String sql = "SELECT SUM(converted_amount) FROM expenses WHERE user_id = ? AND month = ? AND year = ?";
        return getDoubleResult(sql, userId, month, year);
    }

    @Override
    public double getTotalExpensesByYear(int userId, int year) {
        String sql = "SELECT SUM(converted_amount) FROM expenses WHERE user_id = ? AND year = ?";
        return getDoubleResult(sql, userId, year);
    }

    @Override
    public double getAverageMonthlyExpense(int userId, int year) {
        String sql = "SELECT AVG(monthly_total) FROM (SELECT SUM(converted_amount) as monthly_total FROM expenses WHERE user_id = ? AND year = ? GROUP BY month) as sub";
        return getDoubleResult(sql, userId, year);
    }

    @Override
    public double getHighestExpense(int userId, int month, int year) {
        String sql = "SELECT MAX(converted_amount) FROM expenses WHERE user_id = ? AND month = ? AND year = ?";
        return getDoubleResult(sql, userId, month, year);
    }

    @Override
    public double getLowestExpense(int userId, int month, int year) {
        String sql = "SELECT MIN(converted_amount) FROM expenses WHERE user_id = ? AND month = ? AND year = ?";
        return getDoubleResult(sql, userId, month, year);
    }

    @Override
    public Map<String, Double> getCategoryWiseExpenseSum(int userId, int month, int year) {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT c.name, SUM(e.converted_amount) as total FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? AND e.month = ? AND e.year = ? GROUP BY c.name ORDER BY total DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, month);
            pstmt.setInt(3, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getString("name"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<Integer, Double> getMonthlyExpenseTrend(int userId, int year) {
        Map<Integer, Double> map = new LinkedHashMap<>();
        for (int m = 1; m <= 12; m++) map.put(m, 0.0);
        String sql = "SELECT month, SUM(converted_amount) as total FROM expenses WHERE user_id = ? AND year = ? GROUP BY month ORDER BY month";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getInt("month"), rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<Expense> getRecentTransactions(int userId, int limit) {
        String sql = "SELECT e.*, c.name as category_name, c.icon_class as category_icon, c.color_hex as category_color FROM expenses e JOIN categories c ON e.category_id = c.id WHERE e.user_id = ? ORDER BY e.expense_date DESC, e.id DESC LIMIT ?";
        List<Expense> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractExpenseFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Expense> fetchExpenseList(String sql, int userId) {
        List<Expense> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(extractExpenseFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private double getDoubleResult(String sql, Object... params) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private Expense extractExpenseFromResultSet(ResultSet rs) throws SQLException {
        Expense expense = new Expense();
        expense.setId(rs.getInt("id"));
        expense.setUserId(rs.getInt("user_id"));
        expense.setExpenseName(rs.getString("expense_name"));
        expense.setCategoryId(rs.getInt("category_id"));
        expense.setCategoryName(rs.getString("category_name"));
        expense.setCategoryIcon(rs.getString("category_icon"));
        expense.setCategoryColor(rs.getString("category_color"));
        expense.setLabel(rs.getString("label"));
        expense.setAmount(rs.getDouble("amount"));
        expense.setCurrency(rs.getString("currency"));
        expense.setConvertedAmount(rs.getDouble("converted_amount"));
        expense.setPaymentMode(rs.getString("payment_mode"));
        expense.setExpenseDate(rs.getDate("expense_date"));
        expense.setMonth(rs.getInt("month"));
        expense.setYear(rs.getInt("year"));
        expense.setDescription(rs.getString("description"));
        expense.setReceiptPath(rs.getString("receipt_path"));
        expense.setRecurring(rs.getBoolean("is_recurring"));
        expense.setCreatedAt(rs.getTimestamp("created_at"));
        return expense;
    }
}
