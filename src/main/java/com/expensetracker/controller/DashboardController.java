package com.expensetracker.controller;

import com.expensetracker.dao.*;
import com.expensetracker.model.*;
import com.expensetracker.service.AnalyticsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    private final BudgetDAO budgetDAO = new BudgetDAOImpl();
    private final GoalDAO goalDAO = new GoalDAOImpl();
    private final AchievementDAO achievementDAO = new AchievementDAOImpl();
    private final NotificationDAO notificationDAO = new NotificationDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final AnalyticsService analyticsService = new AnalyticsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        if (user == null) {
            // Fallback for demo mode if direct visit
            user = userDAO.getUserById(1);
            if (user == null) {
                user = new User(1, "Alex Morgan", "alex@expensetracker.com", "hash", "+1 555-0199", "assets/images/default-avatar.png", "en", "INR", 85000.0, "Software Engineer");
            }
            if (session != null) {
                session.setAttribute("currentUser", user);
                session.setAttribute("userId", user.getId());
            }
        }

        int userId = user.getId();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1; // 1-indexed
        int year = cal.get(Calendar.YEAR);

        // Fetch Dashboard Metrics
        Map<String, Object> metrics = analyticsService.calculateDashboardMetrics(userId, month, year, user.getMonthlyIncome());
        List<Expense> recentTransactions = expenseDAO.getRecentTransactions(userId, 7);
        List<Budget> budgets = budgetDAO.getBudgetsByUser(userId, month, year);
        List<Goal> goals = goalDAO.getGoalsByUser(userId);
        List<Achievement> achievements = achievementDAO.getAchievementsByUser(userId);
        List<Notification> notifications = notificationDAO.getNotificationsByUser(userId);

        // Calculate Category Breakdown for Pie Chart
        Map<String, Double> categoryChart = expenseDAO.getCategoryWiseExpenseSum(userId, month, year);

        // Calculate Overall Budget Progress & Alerts
        double totalBudget = 0.0;
        double totalSpent = (Double) metrics.get("monthlyTotal");
        for (Budget b : budgets) {
            if (b.getCategoryId() == null) {
                totalBudget = b.getAmount();
                break;
            }
        }

        double remainingBudget = Math.max(0, totalBudget - totalSpent);
        metrics.put("totalBudget", totalBudget);
        metrics.put("remainingBudget", remainingBudget);

        // Compute Goal Progress Average
        double goalProgressAvg = 0.0;
        if (!goals.isEmpty()) {
            double sumP = 0;
            for (Goal g : goals) sumP += g.getProgressPercentage();
            goalProgressAvg = sumP / goals.size();
        }

        // Financial Health Score
        FinancialHealthScore healthScore = analyticsService.calculateFinancialHealthScore(userId, user.getMonthlyIncome(), totalSpent, totalBudget, goalProgressAvg);

        req.setAttribute("user", user);
        req.setAttribute("metrics", metrics);
        req.setAttribute("recentTransactions", recentTransactions);
        req.setAttribute("budgets", budgets);
        req.setAttribute("goals", goals);
        req.setAttribute("achievements", achievements);
        req.setAttribute("notifications", notifications);
        req.setAttribute("categoryChart", categoryChart);
        req.setAttribute("healthScore", healthScore);

        req.getRequestDispatcher("/jsp/dashboard.jsp").forward(req, resp);
    }
}
