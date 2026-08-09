package com.expensetracker.service;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.dao.BudgetDAO;
import com.expensetracker.dao.BudgetDAOImpl;
import com.expensetracker.model.Budget;
import com.expensetracker.model.Expense;

import java.util.*;

public class AIChatService {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    private final BudgetDAO budgetDAO = new BudgetDAOImpl();

    public String processUserQuery(int userId, String query, double monthlyIncome) {
        if (query == null || query.trim().isEmpty()) {
            return "Hello! I am your AI Expense Assistant. Ask me anything about your spending, budgets, savings, or categories!";
        }

        String q = query.toLowerCase().trim();
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);
        int prevMonth = (currentMonth == 1) ? 12 : currentMonth - 1;
        int prevYear = (currentMonth == 1) ? currentYear - 1 : currentYear;

        // 1. "How much did I spend this month?"
        if (q.contains("this month") && (q.contains("spend") || q.contains("total") || q.contains("expense") || q.contains("much"))) {
            double total = expenseDAO.getTotalExpensesByMonth(userId, currentMonth, currentYear);
            return String.format("You have spent **₹%,.2f** so far in %s %d.", total, getMonthName(currentMonth), currentYear);
        }

        // 2. "How much did I spend last month?"
        if (q.contains("last month") && (q.contains("spend") || q.contains("total") || q.contains("expense") || q.contains("much"))) {
            double total = expenseDAO.getTotalExpensesByMonth(userId, prevMonth, prevYear);
            return String.format("In %s %d, your total spending was **₹%,.2f**.", getMonthName(prevMonth), prevYear, total);
        }

        // 3. "Compare this month with last month"
        if (q.contains("compare") || (q.contains("this month") && q.contains("last month"))) {
            double currentTotal = expenseDAO.getTotalExpensesByMonth(userId, currentMonth, currentYear);
            double prevTotal = expenseDAO.getTotalExpensesByMonth(userId, prevMonth, prevYear);
            double diff = currentTotal - prevTotal;
            if (prevTotal > 0) {
                double pct = (diff / prevTotal) * 100.0;
                if (diff > 0) {
                    return String.format("Your spending this month (₹%,.2f) is **₹%,.2f higher (+%.1f%%)** than last month (₹%,.2f).", currentTotal, diff, pct, prevTotal);
                } else {
                    return String.format("Great news! Your spending this month (₹%,.2f) is **₹%,.2f lower (-%.1f%%)** than last month (₹%,.2f).", currentTotal, Math.abs(diff), Math.abs(pct), prevTotal);
                }
            } else {
                return String.format("This month spending: ₹%,.2f. Last month spending: ₹%,.2f.", currentTotal, prevTotal);
            }
        }

        // 4. "Where did I spend the most?" / "What category costs me the most?"
        if (q.contains("most") || q.contains("highest") || q.contains("biggest")) {
            Map<String, Double> categorySums = expenseDAO.getCategoryWiseExpenseSum(userId, currentMonth, currentYear);
            String topCat = "";
            double topAmt = 0.0;
            for (Map.Entry<String, Double> entry : categorySums.entrySet()) {
                if (entry.getValue() > topAmt) {
                    topAmt = entry.getValue();
                    topCat = entry.getKey();
                }
            }
            if (!topCat.isEmpty()) {
                return String.format("Your highest spending category this month is **%s** at **₹%,.2f**.", topCat, topAmt);
            } else {
                return "You haven't recorded any expenses for this month yet.";
            }
        }

        // 5. Category specific queries: "How much did I spend on food / travel / shopping / bills?"
        String[] categories = {"food", "travel", "shopping", "bills", "entertainment", "education", "health", "others"};
        for (String cat : categories) {
            if (q.contains(cat)) {
                Map<String, Double> categorySums = expenseDAO.getCategoryWiseExpenseSum(userId, currentMonth, currentYear);
                double catTotal = 0.0;
                for (Map.Entry<String, Double> entry : categorySums.entrySet()) {
                    if (entry.getKey().toLowerCase().contains(cat)) {
                        catTotal += entry.getValue();
                    }
                }
                return String.format("You spent **₹%,.2f** on **%s** in %s %d.", catTotal, capitalize(cat), getMonthName(currentMonth), currentYear);
            }
        }

        // 6. "What is my average monthly expense?"
        if (q.contains("average")) {
            double avg = expenseDAO.getAverageMonthlyExpense(userId, currentYear);
            return String.format("Your average monthly expense for %d is **₹%,.2f**.", currentYear, avg);
        }

        // 7. "What is my yearly expense?"
        if (q.contains("yearly") || q.contains("year")) {
            double yearlyTotal = expenseDAO.getTotalExpensesByYear(userId, currentYear);
            return String.format("Your total expenses for the year %d are **₹%,.2f**.", currentYear, yearlyTotal);
        }

        // 8. "Am I over my budget?"
        if (q.contains("budget")) {
            double spent = expenseDAO.getTotalExpensesByMonth(userId, currentMonth, currentYear);
            List<Budget> budgets = budgetDAO.getBudgetsByUser(userId, currentMonth, currentYear);
            double totalBudget = 0.0;
            for (Budget b : budgets) {
                if (b.getCategoryId() == null) {
                    totalBudget = b.getAmount();
                    break;
                }
            }
            if (totalBudget > 0) {
                double remaining = totalBudget - spent;
                double pct = (spent / totalBudget) * 100.0;
                if (remaining >= 0) {
                    return String.format("You are **within budget**! Used **%.1f%%** (₹%,.2f of ₹%,.2f). Remaining budget: **₹%,.2f**.", pct, spent, totalBudget, remaining);
                } else {
                    return String.format("⚠️ You have **exceeded your overall budget** by **₹%,.2f**! Spent: ₹%,.2f / Target: ₹%,.2f.", Math.abs(remaining), spent, totalBudget);
                }
            } else {
                return String.format("You have spent ₹%,.2f this month. Set an overall budget limit in the Budget Planner to track threshold alerts!", spent);
            }
        }

        // 9. "How much have I saved?"
        if (q.contains("save") || q.contains("savings")) {
            double spent = expenseDAO.getTotalExpensesByMonth(userId, currentMonth, currentYear);
            double saved = Math.max(0, monthlyIncome - spent);
            double pct = monthlyIncome > 0 ? (saved / monthlyIncome) * 100.0 : 0;
            return String.format("Based on your monthly income of ₹%,.2f, your savings this month are **₹%,.2f (%.1f%%)**.", monthlyIncome, saved, pct);
        }

        // 10. "How can I reduce my expenses?" / Tips
        if (q.contains("reduce") || q.contains("cut") || q.contains("tip") || q.contains("advice")) {
            return "💡 **Smart Ways to Cut Expenses:**\n" +
                   "1. Trim dining out and delivery orders (save up to 20%).\n" +
                   "2. Audit recurring streaming & digital subscriptions.\n" +
                   "3. Set category alert limits at 75% in your Budget Planner.\n" +
                   "4. Automate 20% savings into SIPs or Index Funds on payday.";
        }

        // General smart fallback summary
        double monthlyTotal = expenseDAO.getTotalExpensesByMonth(userId, currentMonth, currentYear);
        return String.format("I found that your total expenses for %s %d are **₹%,.2f**. You can ask me questions like 'How much did I spend on food?', 'Am I over budget?', or 'Compare this month with last month'!", getMonthName(currentMonth), currentYear, monthlyTotal);
    }

    private String getMonthName(int m) {
        String[] months = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return (m >= 1 && m <= 12) ? months[m] : "";
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
