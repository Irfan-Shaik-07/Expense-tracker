package com.expensetracker.service;

import com.expensetracker.model.Expense;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class ExportService {

    public String generateCSVReport(List<Expense> expenses) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Expense Name,Category,Label,Amount,Currency,Converted Amount (INR),Payment Mode,Date,Description\n");

        for (Expense e : expenses) {
            sb.append(e.getId()).append(",")
              .append(escapeCSV(e.getExpenseName())).append(",")
              .append(escapeCSV(e.getCategoryName())).append(",")
              .append(escapeCSV(e.getLabel())).append(",")
              .append(e.getAmount()).append(",")
              .append(e.getCurrency()).append(",")
              .append(e.getConvertedAmount()).append(",")
              .append(escapeCSV(e.getPaymentMode())).append(",")
              .append(e.getExpenseDate()).append(",")
              .append(escapeCSV(e.getDescription()))
              .append("\n");
        }
        return sb.toString();
    }

    private String escapeCSV(String input) {
        if (input == null) return "\"\"";
        return "\"" + input.replace("\"", "\"\"") + "\"";
    }
}
