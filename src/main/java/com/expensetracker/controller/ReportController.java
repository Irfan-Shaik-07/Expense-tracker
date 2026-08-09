package com.expensetracker.controller;

import com.expensetracker.dao.ExpenseDAO;
import com.expensetracker.dao.ExpenseDAOImpl;
import com.expensetracker.model.Expense;
import com.expensetracker.model.User;
import com.expensetracker.service.ExportService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/reports/*")
public class ReportController extends HttpServlet {

    private final ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    private final ExportService exportService = new ExportService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String path = req.getPathInfo();
        if ("/export".equals(path)) {
            String format = req.getParameter("format");
            List<Expense> expenses = expenseDAO.getExpensesByUserId(userId);

            if ("csv".equalsIgnoreCase(format)) {
                resp.setContentType("text/csv");
                resp.setHeader("Content-Disposition", "attachment; filename=\"Expense_Report.csv\"");
                String csv = exportService.generateCSVReport(expenses);
                resp.getWriter().write(csv);
                return;
            } else if ("excel".equalsIgnoreCase(format)) {
                resp.setContentType("application/vnd.ms-excel");
                resp.setHeader("Content-Disposition", "attachment; filename=\"Expense_Report.csv\"");
                String csv = exportService.generateCSVReport(expenses);
                resp.getWriter().write(csv);
                return;
            } else {
                resp.setContentType("text/plain");
                resp.setHeader("Content-Disposition", "attachment; filename=\"Expense_Report_Summary.txt\"");
                resp.getWriter().write("EXPENSE TRACKER REPORT\nTotal Transactions: " + expenses.size());
                return;
            }
        }

        List<Expense> expenses = expenseDAO.getExpensesByUserId(userId);
        req.setAttribute("expenses", expenses);
        req.getRequestDispatcher("/jsp/reports.jsp").forward(req, resp);
    }
}
