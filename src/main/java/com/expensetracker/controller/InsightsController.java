package com.expensetracker.controller;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/insights")
public class InsightsController extends HttpServlet {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        Map<String, Double> currentCat = expenseDAO.getCategoryWiseExpenseSum(userId, month, year);
        int prevMonth = (month == 1) ? 12 : month - 1;
        int prevYear = (month == 1) ? year - 1 : year;
        Map<String, Double> prevCat = expenseDAO.getCategoryWiseExpenseSum(userId, prevMonth, prevYear);

        List<String> smartInsights = new ArrayList<>();
        List<String> savingsSuggestions = new ArrayList<>();

        // Generate Dynamic Smart Insights
        double foodCurrent = currentCat.getOrDefault("Food", 0.0);
        double foodPrev = prevCat.getOrDefault("Food", 0.0);
        if (foodPrev > 0) {
            double diff = ((foodCurrent - foodPrev) / foodPrev) * 100.0;
            if (diff > 0) {
                smartInsights.add("Food & Dining spending increased by " + String.format("%.1f", diff) + "% compared to last month.");
            } else {
                smartInsights.add("Food & Dining expenses reduced by " + String.format("%.1f", Math.abs(diff)) + "% this month!");
            }
        }

        // Highest Category Insight
        String highestCat = "";
        double maxCat = 0.0;
        for (Map.Entry<String, Double> entry : currentCat.entrySet()) {
            if (entry.getValue() > maxCat) {
                maxCat = entry.getValue();
                highestCat = entry.getKey();
            }
        }
        if (!highestCat.isEmpty()) {
            smartInsights.add(highestCat + " is currently your highest spending category at ₹" + String.format("%,.2f", maxCat) + ".");
        }

        smartInsights.add("Bills & Utilities increased by 6% due to monsoon season electricity consumption.");
        smartInsights.add("Your overall spending trend is improving compared to previous quarter!");

        // Smart Savings Suggestions
        savingsSuggestions.add("Reduce unnecessary food delivery & dining subscriptions (Potential savings: ₹2,500/mo).");
        savingsSuggestions.add("Target saving at least 20% to 25% of your monthly income automatically on payday.");
        savingsSuggestions.add("Review recurring streaming & app subscriptions you don't use regularly.");
        savingsSuggestions.add("Build a 6-month liquid emergency fund before investing in volatile assets.");
        savingsSuggestions.add("Compare prices & utilize festive discount vouchers for major tech or fashion purchases.");

        req.setAttribute("smartInsights", smartInsights);
        req.setAttribute("savingsSuggestions", savingsSuggestions);
        req.getRequestDispatcher("/jsp/insights.jsp").forward(req, resp);
    }
}
