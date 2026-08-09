package com.expensetracker.controller;

import com.expensetracker.dao.UserDAO;
import com.expensetracker.dao.UserDAOImpl;
import com.expensetracker.model.User;
import com.expensetracker.utils.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/settings/*")
public class SettingsController extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (user == null) user = userDAO.getUserById(1);

        String path = req.getPathInfo();
        if ("/backup-export".equals(path)) {
            resp.setContentType("application/json");
            resp.setHeader("Content-Disposition", "attachment; filename=\"expense_tracker_backup.json\"");
            String jsonDump = "{\"user\": {\"email\": \"" + user.getEmail() + "\"}, \"backupDate\": \"" + new java.util.Date() + "\", \"version\": \"1.0\"}";
            resp.getWriter().write(jsonDump);
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/jsp/settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String action = req.getParameter("action");

        if ("updateProfile".equals(action)) {
            String fullName = req.getParameter("fullName");
            String phone = req.getParameter("phone");
            String language = req.getParameter("language");
            String currency = req.getParameter("currency");
            String incomeStr = req.getParameter("monthlyIncome");
            String occupation = req.getParameter("occupation");
            String theme = req.getParameter("theme");

            double income = 0.0;
            try { income = Double.parseDouble(incomeStr); } catch (Exception ignored) {}

            user.setFullName(fullName);
            user.setPhoneNumber(phone);
            user.setPreferredLanguage(language);
            user.setPreferredCurrency(currency);
            user.setMonthlyIncome(income);
            user.setOccupation(occupation);

            userDAO.updateUserProfile(user);
            userDAO.updateSettings(userId, language, currency, theme != null ? theme : "light");

            session.setAttribute("currentUser", user);
            session.setAttribute("userLanguage", language);
            session.setAttribute("userCurrency", currency);

            resp.sendRedirect(req.getContextPath() + "/settings?msg=profile_updated");
        } else if ("changePassword".equals(action)) {
            String currentPassword = req.getParameter("currentPassword");
            String newPassword = req.getParameter("newPassword");
            
            if (PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
                userDAO.updatePassword(userId, PasswordUtil.hashPassword(newPassword));
                user.setPasswordHash(PasswordUtil.hashPassword(newPassword));
                resp.sendRedirect(req.getContextPath() + "/settings?msg=password_changed");
            } else {
                resp.sendRedirect(req.getContextPath() + "/settings?error=invalid_current_password");
            }
        }
    }
}
