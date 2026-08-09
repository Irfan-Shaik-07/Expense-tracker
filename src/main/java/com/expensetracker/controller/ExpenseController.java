package com.expensetracker.controller;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.utils.CurrencyConverter;
import com.expensetracker.utils.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@WebServlet("/expenses/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ExpenseController extends HttpServlet {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String path = req.getPathInfo();
        if (path == null) path = "/list";

        if ("/delete".equals(path)) {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                expenseDAO.deleteExpense(Integer.parseInt(idStr), userId);
            }
            resp.sendRedirect(req.getContextPath() + "/expenses?msg=deleted");
            return;
        }

        String search = req.getParameter("search");
        String categoryIdStr = req.getParameter("category");
        String paymentMode = req.getParameter("paymentMode");
        String minAmountStr = req.getParameter("minAmount");
        String maxAmountStr = req.getParameter("maxAmount");

        Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty()) ? Integer.parseInt(categoryIdStr) : null;
        Double minAmount = (minAmountStr != null && !minAmountStr.isEmpty()) ? Double.parseDouble(minAmountStr) : null;
        Double maxAmount = (maxAmountStr != null && !maxAmountStr.isEmpty()) ? Double.parseDouble(maxAmountStr) : null;

        List<Expense> expenses;
        if (search != null || categoryId != null || paymentMode != null || minAmount != null || maxAmount != null) {
            expenses = expenseDAO.searchExpenses(userId, search, categoryId, paymentMode, minAmount, maxAmount);
        } else {
            expenses = expenseDAO.getExpensesByUserId(userId);
        }

        req.setAttribute("expenses", expenses);
        req.setAttribute("searchQuery", search);
        req.getRequestDispatcher("/jsp/expenses.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String idStr = req.getParameter("id");
        String expenseName = req.getParameter("expenseName");
        String categoryIdStr = req.getParameter("categoryId");
        String label = req.getParameter("label");
        String amountStr = req.getParameter("amount");
        String currency = req.getParameter("currency");
        String paymentMode = req.getParameter("paymentMode");
        String dateStr = req.getParameter("expenseDate");
        String description = req.getParameter("description");

        if (currency == null || currency.isEmpty()) currency = "INR";
        if (label == null || label.isEmpty()) label = "General";

        double amount = 0.0;
        try { amount = Double.parseDouble(amountStr); } catch (Exception ignored) {}

        if (!ValidationUtil.isPositive(amount) || !ValidationUtil.isNotEmpty(expenseName) || !ValidationUtil.isValidDate(dateStr)) {
            resp.sendRedirect(req.getContextPath() + "/expenses?error=invalid_inputs");
            return;
        }

        Date date = Date.valueOf(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int categoryId = Integer.parseInt(categoryIdStr);
        double converted = CurrencyConverter.convertToINR(amount, currency);

        // Handle Receipt Upload
        String receiptPath = null;
        try {
            Part filePart = req.getPart("receipt");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                String uploadDir = getServletContext().getRealPath("/") + "uploads";
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) uploadFolder.mkdirs();
                filePart.write(uploadDir + File.separator + fileName);
                receiptPath = "uploads/" + fileName;
            }
        } catch (Exception e) {
            System.err.println("Receipt upload error: " + e.getMessage());
        }

        Expense expense = new Expense();
        expense.setUserId(userId);
        expense.setExpenseName(expenseName);
        expense.setCategoryId(categoryId);
        expense.setLabel(label);
        expense.setAmount(amount);
        expense.setCurrency(currency);
        expense.setConvertedAmount(converted);
        expense.setPaymentMode(paymentMode);
        expense.setExpenseDate(date);
        expense.setMonth(month);
        expense.setYear(year);
        expense.setDescription(description);
        if (receiptPath != null) expense.setReceiptPath(receiptPath);

        if (idStr != null && !idStr.isEmpty()) {
            expense.setId(Integer.parseInt(idStr));
            expenseDAO.updateExpense(expense);
        } else {
            expenseDAO.createExpense(expense);
        }

        resp.sendRedirect(req.getContextPath() + "/expenses?msg=success");
    }
}
