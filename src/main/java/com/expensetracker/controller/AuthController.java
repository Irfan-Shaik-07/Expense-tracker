package com.expensetracker.controller;

import com.expensetracker.dao.UserDAO;
import com.expensetracker.dao.UserDAOImpl;
import com.expensetracker.model.User;
import com.expensetracker.utils.PasswordUtil;
import com.expensetracker.utils.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) path = "/login";

        switch (path) {
            case "/register":
                req.getRequestDispatcher("/jsp/auth/register.jsp").forward(req, resp);
                break;
            case "/forgot-password":
                req.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(req, resp);
                break;
            case "/logout":
                HttpSession session = req.getSession(false);
                if (session != null) session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/auth/login?msg=logged_out");
                break;
            case "/login":
            default:
                req.getRequestDispatcher("/jsp/auth/login.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/login".equals(path)) {
            handleLogin(req, resp);
        } else if ("/register".equals(path)) {
            handleRegister(req, resp);
        } else if ("/forgot-password".equals(path)) {
            handleForgotPassword(req, resp);
        } else if ("/verify-otp".equals(path)) {
            handleVerifyOTP(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("rememberMe");

        if (!ValidationUtil.isValidEmail(email) || !ValidationUtil.isNotEmpty(password)) {
            req.setAttribute("errorMessage", "Please provide a valid email and password.");
            req.getRequestDispatcher("/jsp/auth/login.jsp").forward(req, resp);
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            HttpSession session = req.getSession(true);
            session.setAttribute("currentUser", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLanguage", user.getPreferredLanguage());
            session.setAttribute("userCurrency", user.getPreferredCurrency());

            if ("on".equals(rememberMe)) {
                Cookie cookie = new Cookie("userEmail", email);
                cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
                cookie.setPath(req.getContextPath());
                resp.addCookie(cookie);
            }

            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("errorMessage", "Invalid email or password. Please try again.");
            req.getRequestDispatcher("/jsp/auth/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String phone = req.getParameter("phone");
        String incomeStr = req.getParameter("monthlyIncome");
        String occupation = req.getParameter("occupation");

        if (!ValidationUtil.isNotEmpty(fullName) || !ValidationUtil.isValidEmail(email) || !ValidationUtil.isNotEmpty(password)) {
            req.setAttribute("errorMessage", "All required fields must be filled correctly.");
            req.getRequestDispatcher("/jsp/auth/register.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Passwords do not match.");
            req.getRequestDispatcher("/jsp/auth/register.jsp").forward(req, resp);
            return;
        }

        if (userDAO.getUserByEmail(email) != null) {
            req.setAttribute("errorMessage", "Email is already registered. Please login.");
            req.getRequestDispatcher("/jsp/auth/register.jsp").forward(req, resp);
            return;
        }

        double income = 0.0;
        try {
            if (incomeStr != null && !incomeStr.trim().isEmpty()) income = Double.parseDouble(incomeStr);
        } catch (NumberFormatException ignored) {}

        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPasswordHash(PasswordUtil.hashPassword(password));
        newUser.setPhoneNumber(phone);
        newUser.setMonthlyIncome(income);
        newUser.setOccupation(occupation);

        User created = userDAO.createUser(newUser);
        if (created != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("currentUser", created);
            session.setAttribute("userId", created.getId());
            session.setAttribute("userLanguage", created.getPreferredLanguage());
            session.setAttribute("userCurrency", created.getPreferredCurrency());
            resp.sendRedirect(req.getContextPath() + "/dashboard?msg=registered");
        } else {
            req.setAttribute("errorMessage", "Failed to register user. System error.");
            req.getRequestDispatcher("/jsp/auth/register.jsp").forward(req, resp);
        }
    }

    private void handleForgotPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (userDAO.getUserByEmail(email) != null) {
            req.setAttribute("infoMessage", "OTP sent to " + email + ". Use OTP: 123456 to reset password.");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/jsp/auth/verify-otp.jsp").forward(req, resp);
        } else {
            req.setAttribute("errorMessage", "Email not found in our records.");
            req.getRequestDispatcher("/jsp/auth/forgot-password.jsp").forward(req, resp);
        }
    }

    private void handleVerifyOTP(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String otp = req.getParameter("otp");
        String newPassword = req.getParameter("newPassword");

        if ("123456".equals(otp) && ValidationUtil.isNotEmpty(newPassword)) {
            User user = userDAO.getUserByEmail(email);
            if (user != null) {
                userDAO.updatePassword(user.getId(), PasswordUtil.hashPassword(newPassword));
                resp.sendRedirect(req.getContextPath() + "/auth/login?msg=password_reset_success");
                return;
            }
        }
        req.setAttribute("errorMessage", "Invalid OTP or details. Enter OTP: 123456");
        req.getRequestDispatcher("/jsp/auth/verify-otp.jsp").forward(req, resp);
    }
}
