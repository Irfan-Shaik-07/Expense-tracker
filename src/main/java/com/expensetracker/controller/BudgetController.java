package com.expensetracker.controller;

import com.expensetracker.dao.*;
import com.expensetracker.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/budgets/*")
public class BudgetController extends HttpServlet {

    private final BudgetDAO budgetDAO = new BudgetDAOImpl();
    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        List<Budget> budgets = budgetDAO.getBudgetsByUser(userId, month, year);
        Map<String, Double> categorySums = expenseDAO.getCategoryWiseExpenseSum(userId, month, year);
        double totalSpentMonth = expenseDAO.getTotalExpensesByMonth(userId, month, year);

        for (Budget b : budgets) {
            if (b.getCategoryId() == null) {
                b.setSpentAmount(totalSpentMonth);
            } else {
                b.setSpentAmount(categorySums.getOrDefault(b.getCategoryName(), 0.0));
            }
        }

        req.setAttribute("budgets", budgets);
        req.setAttribute("totalSpentMonth", totalSpentMonth);
        req.getRequestDispatcher("/jsp/budgets.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String categoryIdStr = req.getParameter("categoryId");
        String amountStr = req.getParameter("amount");
        String periodType = req.getParameter("periodType");

        if (periodType == null) periodType = "MONTHLY";
        double amount = 0.0;
        try { amount = Double.parseDouble(amountStr); } catch (Exception ignored) {}

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty() && !"0".equals(categoryIdStr)) ? Integer.parseInt(categoryIdStr) : null;

        Budget b = new Budget();
        b.setUserId(userId);
        b.setCategoryId(categoryId);
        b.setAmount(amount);
        b.setPeriodType(periodType);
        b.setMonth(month);
        b.setYear(year);

        budgetDAO.saveOrUpdateBudget(b);
        resp.sendRedirect(req.getContextPath() + "/budgets?msg=saved");
    }
}
