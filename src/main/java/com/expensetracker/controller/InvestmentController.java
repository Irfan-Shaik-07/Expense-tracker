package com.expensetracker.controller;

import com.expensetracker.service.InvestmentService;
import com.expensetracker.model.InvestmentOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/investment-learning")
public class InvestmentController extends HttpServlet {

    private final InvestmentService investmentService = new InvestmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<InvestmentOption> options = investmentService.getAllInvestmentOptions();
        req.setAttribute("investmentOptions", options);
        req.getRequestDispatcher("/jsp/investment.jsp").forward(req, resp);
    }
}
