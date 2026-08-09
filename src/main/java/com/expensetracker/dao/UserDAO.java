package com.expensetracker.dao;

import com.expensetracker.model.User;
import java.util.List;

public interface UserDAO {
    User createUser(User user);
    User getUserById(int id);
    User getUserByEmail(String email);
    boolean updateUserProfile(User user);
    boolean updatePassword(int userId, String newPasswordHash);
    boolean updateSettings(int userId, String language, String currency, String theme);
    List<User> getAllUsers();
}
