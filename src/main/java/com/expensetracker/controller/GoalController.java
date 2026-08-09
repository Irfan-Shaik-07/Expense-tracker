package com.expensetracker.controller;

import com.expensetracker.dao.GoalDAO;
import com.expensetracker.dao.GoalDAOImpl;
import com.expensetracker.model.Goal;
import com.expensetracker.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/goals/*")
public class GoalController extends HttpServlet {

    private final GoalDAO goalDAO = new GoalDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String path = req.getPathInfo();
        if ("/delete".equals(path)) {
            String idStr = req.getParameter("id");
            if (idStr != null) goalDAO.deleteGoal(Integer.parseInt(idStr), userId);
            resp.sendRedirect(req.getContextPath() + "/goals?msg=deleted");
            return;
        }

        List<Goal> goals = goalDAO.getGoalsByUser(userId);
        req.setAttribute("goals", goals);
        req.getRequestDispatcher("/jsp/goals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String action = req.getPathInfo();

        if ("/update-progress".equals(action)) {
            int goalId = Integer.parseInt(req.getParameter("goalId"));
            double addAmount = Double.parseDouble(req.getParameter("addAmount"));
            goalDAO.updateSavedAmount(goalId, userId, addAmount);
            resp.sendRedirect(req.getContextPath() + "/goals?msg=progress_updated");
            return;
        }

        String title = req.getParameter("title");
        String targetAmountStr = req.getParameter("targetAmount");
        String savedAmountStr = req.getParameter("savedAmount");
        String targetDateStr = req.getParameter("targetDate");
        String category = req.getParameter("category");

        double targetAmount = Double.parseDouble(targetAmountStr);
        double savedAmount = (savedAmountStr != null && !savedAmountStr.isEmpty()) ? Double.parseDouble(savedAmountStr) : 0.0;

        Goal g = new Goal();
        g.setUserId(userId);
        g.setTitle(title);
        g.setTargetAmount(targetAmount);
        g.setSavedAmount(savedAmount);
        g.setTargetDate(Date.valueOf(targetDateStr));
        g.setCategory(category != null ? category : "General");
        g.setIconClass("fa-bullseye");

        goalDAO.createGoal(g);
        resp.sendRedirect(req.getContextPath() + "/goals?msg=goal_created");
    }
}
