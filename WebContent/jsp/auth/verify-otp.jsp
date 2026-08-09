<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Verify OTP - Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
</head>
<body class="d-flex align-items-center justify-content-center p-3" style="min-height: 100vh;">
    <div class="glass-card" style="width: 100%; max-width: 420px; border-radius: 24px;">
        <h4 class="text-gradient mb-2 text-center" style="font-weight: 700;">OTP Verification</h4>
        <p class="text-center mb-4" style="color: var(--text-muted); font-size: 13px;">Enter the 6-digit OTP code sent to your email</p>

        <% if (request.getAttribute("infoMessage") != null) { %>
            <div class="alert alert-info" style="font-size: 13px;"><%= request.getAttribute("infoMessage") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/auth/verify-otp" method="POST">
            <input type="hidden" name="email" value="${email}">
            <div class="mb-3">
                <label class="form-label" style="font-size: 13px;">OTP Code</label>
                <input type="text" name="otp" class="form-control-glass text-center" placeholder="123456" style="letter-spacing: 6px; font-size: 20px; font-weight: 700;" required>
            </div>
            <div class="mb-4">
                <label class="form-label" style="font-size: 13px;">New Password</label>
                <input type="password" name="newPassword" class="form-control-glass" placeholder="••••••••" required>
            </div>
            <button type="submit" class="btn-glass w-100 py-3 justify-content-center">Verify OTP & Reset Password</button>
        </form>
    </div>
</body>
</html>
