package com.expensetracker.controller;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.model.User;
import com.expensetracker.service.AnalyticsService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/analytics/*")
public class AnalyticsController extends HttpServlet {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    private final AnalyticsService analyticsService = new AnalyticsService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;
        double income = (user != null) ? user.getMonthlyIncome() : 85000.0;

        String path = req.getPathInfo();
        if ("/api/charts-data".equals(path)) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);

            Map<String, Object> data = new HashMap<>();
            data.put("monthlyTrend", expenseDAO.getMonthlyExpenseTrend(userId, year));
            data.put("categoryPie", expenseDAO.getCategoryWiseExpenseSum(userId, month, year));
            data.put("forecast", analyticsService.generateExpenseForecast(userId, year));

            resp.getWriter().write(gson.toJson(data));
            return;
        }

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        Map<String, Object> metrics = analyticsService.calculateDashboardMetrics(userId, month, year, income);
        Map<String, Double> categorySum = expenseDAO.getCategoryWiseExpenseSum(userId, month, year);

        req.setAttribute("metrics", metrics);
        req.setAttribute("categorySum", categorySum);
        req.getRequestDispatcher("/jsp/analytics.jsp").forward(req, resp);
    }
}
