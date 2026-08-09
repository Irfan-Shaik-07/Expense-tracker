package com.expensetracker.dao;

import com.expensetracker.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (full_name, email, password_hash, phone_number, preferred_language, preferred_currency, monthly_income, occupation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getPreferredLanguage() != null ? user.getPreferredLanguage() : "en");
            pstmt.setString(6, user.getPreferredCurrency() != null ? user.getPreferredCurrency() : "INR");
            pstmt.setDouble(7, user.getMonthlyIncome());
            pstmt.setString(8, user.getOccupation());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getInt(1));
                    }
                }
                // Initialize default user_settings row
                String settingsSql = "INSERT INTO user_settings (user_id) VALUES (?) ON DUPLICATE KEY UPDATE user_id=user_id";
                try (PreparedStatement sStmt = conn.prepareStatement(settingsSql)) {
                    sStmt.setInt(1, user.getId());
                    sStmt.executeUpdate();
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET full_name = ?, phone_number = ?, preferred_language = ?, preferred_currency = ?, monthly_income = ?, occupation = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setString(3, user.getPreferredLanguage());
            pstmt.setString(4, user.getPreferredCurrency());
            pstmt.setDouble(5, user.getMonthlyIncome());
            pstmt.setString(6, user.getOccupation());
            pstmt.setInt(7, user.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePassword(int userId, String newPasswordHash) {
        String sql = "UPDATE users SET password_hash = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPasswordHash);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateSettings(int userId, String language, String currency, String theme) {
        String userSql = "UPDATE users SET preferred_language = ?, preferred_currency = ? WHERE id = ?";
        String themeSql = "UPDATE user_settings SET theme = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement(userSql);
                 PreparedStatement p2 = conn.prepareStatement(themeSql)) {
                
                p1.setString(1, language);
                p1.setString(2, currency);
                p1.setInt(3, userId);
                p1.executeUpdate();

                p2.setString(1, theme);
                p2.setInt(2, userId);
                p2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setPreferredLanguage(rs.getString("preferred_language"));
        user.setPreferredCurrency(rs.getString("preferred_currency"));
        user.setMonthlyIncome(rs.getDouble("monthly_income"));
        user.setOccupation(rs.getString("occupation"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}
