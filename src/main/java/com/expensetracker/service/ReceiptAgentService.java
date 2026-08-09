package com.expensetracker.service;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.model.Expense;

import java.io.File;
import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiptAgentService {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();

    public Map<String, Object> processReceipt(int userId, String fileName, String fileContentOrPath) {
        Map<String, Object> result = new HashMap<>();

        // Simulated OCR & AI text extraction logic
        String lowerName = (fileName != null) ? fileName.toLowerCase() : "";
        String merchant = "Restaurant / Merchant";
        double amount = 750.0;
        String dateStr = new Date(System.currentTimeMillis()).toString();
        int categoryId = 1; // Default Food
        String categoryName = "Food";

        if (lowerName.contains("uber") || lowerName.contains("ola") || lowerName.contains("fuel") || lowerName.contains("flight") || lowerName.contains("travel")) {
            merchant = "Uber Ride / Travel";
            amount = 450.0;
            categoryId = 2;
            categoryName = "Travel";
        } else if (lowerName.contains("amazon") || lowerName.contains("zara") || lowerName.contains("shop")) {
            merchant = "Amazon / Shopping Store";
            amount = 2800.0;
            categoryId = 3;
            categoryName = "Shopping";
        } else if (lowerName.contains("electric") || lowerName.contains("bill") || lowerName.contains("power")) {
            merchant = "State Electricity Board";
            amount = 1850.0;
            categoryId = 4;
            categoryName = "Bills";
        } else if (lowerName.contains("netflix") || lowerName.contains("cinema") || lowerName.contains("movie")) {
            merchant = "IMAX / Cinema Entertainment";
            amount = 999.0;
            categoryId = 5;
            categoryName = "Entertainment";
        } else if (lowerName.contains("doctor") || lowerName.contains("pharmacy") || lowerName.contains("med")) {
            merchant = "Apollo Pharmacy / Hospital";
            amount = 1250.0;
            categoryId = 7;
            categoryName = "Health";
        } else {
            merchant = "ABC Restaurant / Cafe";
            amount = 750.0;
            categoryId = 1;
            categoryName = "Food";
        }

        // Duplicate Expense Protection Check
        List<Expense> existing = expenseDAO.getExpensesByUserId(userId);
        boolean isPossibleDuplicate = false;
        String duplicateMsg = "";

        for (Expense e : existing) {
            boolean amtMatch = Math.abs(e.getAmount() - amount) < 1.0;
            boolean merchantMatch = e.getExpenseName() != null && e.getExpenseName().toLowerCase().contains(merchant.toLowerCase().split(" ")[0]);
            if (amtMatch || merchantMatch) {
                isPossibleDuplicate = true;
                duplicateMsg = String.format("A similar expense ('%s' - ₹%,.2f on %s) already exists.", e.getExpenseName(), e.getAmount(), e.getExpenseDate());
                break;
            }
        }

        result.put("merchant", merchant);
        result.put("amount", amount);
        result.put("date", dateStr);
        result.put("categoryId", categoryId);
        result.put("categoryName", categoryName);
        result.put("confidence", "94%");
        result.put("isDuplicate", isPossibleDuplicate);
        result.put("duplicateMessage", duplicateMsg);
        result.put("status", "success");

        return result;
    }
}
