<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Expense Tracker Smart Personal Finance</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="d-flex align-items-center justify-content-center p-3" style="min-height: 100vh;">
    <div class="glass-card" style="width: 100%; max-width: 440px; border-radius: 24px;">
        <div class="text-center mb-4">
            <div class="stat-card-icon bg-lavender mx-auto mb-3" style="width: 64px; height: 64px; font-size: 28px;">
                <i class="fa-solid fa-wallet"></i>
            </div>
            <h3 class="text-gradient mb-1" style="font-weight: 700;">Welcome Back</h3>
            <p style="color: var(--text-muted); font-size: 14px;">Sign in to your Expense Tracker Account</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" style="border-radius: 12px; font-size: 14px;">
                <i class="fa-solid fa-triangle-exclamation me-2"></i><%= request.getAttribute("errorMessage") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/auth/login" method="POST">
            <div class="mb-3">
                <label class="form-label" style="font-weight: 500; font-size: 14px;">Email Address</label>
                <div class="position-relative">
                    <input type="email" name="email" class="form-control-glass ps-5" placeholder="alex@expensetracker.com" value="alex@expensetracker.com" required>
                    <i class="fa-solid fa-envelope position-absolute" style="left: 16px; top: 16px; color: var(--text-muted);"></i>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label" style="font-weight: 500; font-size: 14px;">Password</label>
                <div class="position-relative">
                    <input type="password" name="password" class="form-control-glass ps-5" placeholder="••••••••" value="Password@123" required>
                    <i class="fa-solid fa-lock position-absolute" style="left: 16px; top: 16px; color: var(--text-muted);"></i>
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center mb-4">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="rememberMe" id="rememberMe" checked>
                    <label class="form-check-label" for="rememberMe" style="font-size: 13px; color: var(--text-muted);">Remember Me</label>
                </div>
                <a href="${pageContext.request.contextPath}/auth/forgot-password" style="color: var(--primary-lavender); font-size: 13px; text-decoration: none; font-weight: 500;">Forgot Password?</a>
            </div>

            <button type="submit" class="btn-glass w-100 py-3 justify-content-center" style="border-radius: 12px; font-weight: 600; font-size: 16px;">
                <i class="fa-solid fa-right-to-bracket"></i> Sign In to Dashboard
            </button>
        </form>

        <div class="text-center mt-4 pt-3" style="border-top: 1px solid var(--card-glass-border);">
            <span style="font-size: 14px; color: var(--text-muted);">Don't have an account?</span>
            <a href="${pageContext.request.contextPath}/auth/register" style="color: var(--secondary-cyan); font-weight: 600; text-decoration: none; margin-left: 6px;">Create Account</a>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
