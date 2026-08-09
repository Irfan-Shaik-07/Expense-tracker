<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password - Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
</head>
<body class="d-flex align-items-center justify-content-center p-3" style="min-height: 100vh;">
    <div class="glass-card" style="width: 100%; max-width: 420px; border-radius: 24px;">
        <h4 class="text-gradient mb-2 text-center" style="font-weight: 700;">Reset Password</h4>
        <p class="text-center mb-4" style="color: var(--text-muted); font-size: 13px;">Enter your email to receive an OTP verification code</p>

        <form action="${pageContext.request.contextPath}/auth/forgot-password" method="POST">
            <div class="mb-4">
                <label class="form-label" style="font-size: 13px; font-weight: 500;">Email Address</label>
                <input type="email" name="email" class="form-control-glass" placeholder="alex@expensetracker.com" required>
            </div>
            <button type="submit" class="btn-glass w-100 py-3 justify-content-center">Send OTP Code</button>
        </form>
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/auth/login" style="color: var(--text-muted); text-decoration: none; font-size: 13px;">Back to Login</a>
        </div>
    </div>
</body>
</html>
