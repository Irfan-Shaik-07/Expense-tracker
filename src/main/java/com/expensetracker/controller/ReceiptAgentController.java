package com.expensetracker.controller;

import com.expensetracker.model.User;
import com.expensetracker.service.ReceiptAgentService;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@WebServlet("/api/scan-receipt")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ReceiptAgentController extends HttpServlet {

    private final ReceiptAgentService receiptAgentService = new ReceiptAgentService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;
        int userId = (user != null) ? user.getId() : 1;

        String fileName = "receipt_bill.png";
        try {
            Part filePart = req.getPart("receiptFile");
            if (filePart != null && filePart.getSize() > 0) {
                fileName = filePart.getSubmittedFileName();
                String uploadDir = getServletContext().getRealPath("/") + "uploads";
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) uploadFolder.mkdirs();
                filePart.write(uploadDir + File.separator + System.currentTimeMillis() + "_" + fileName);
            }
        } catch (Exception e) {
            System.err.println("Scan receipt file error: " + e.getMessage());
        }

        Map<String, Object> scanResult = receiptAgentService.processReceipt(userId, fileName, null);
        resp.getWriter().write(gson.toJson(scanResult));
    }
}
