package com.expensetracker.controller;

import com.expensetracker.model.User;
import com.expensetracker.service.AIChatService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/chat")
public class AIChatController extends HttpServlet {

    private final AIChatService aiChatService = new AIChatService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;
        double income = (user != null) ? user.getMonthlyIncome() : 85000.0;

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String userMessage = "";
        try {
            JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
            if (json != null && json.has("message")) {
                userMessage = json.get("message").getAsString();
            }
        } catch (Exception e) {
            userMessage = req.getParameter("message");
        }

        if (userMessage == null) userMessage = "";

        String aiResponse = aiChatService.processUserQuery(userId, userMessage, income);

        Map<String, String> result = new HashMap<>();
        result.put("reply", aiResponse);
        result.put("status", "success");

        resp.getWriter().write(gson.toJson(result));
    }
}
