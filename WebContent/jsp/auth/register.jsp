<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="d-flex align-items-center justify-content-center p-3" style="min-height: 100vh;">
    <div class="glass-card" style="width: 100%; max-width: 520px; border-radius: 24px;">
        <div class="text-center mb-4">
            <h3 class="text-gradient mb-1" style="font-weight: 700;">Create Account</h3>
            <p style="color: var(--text-muted); font-size: 14px;">Start your intelligent personal finance journey</p>
        </div>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" style="border-radius: 12px; font-size: 14px;"><%= request.getAttribute("errorMessage") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/auth/register" method="POST">
            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label" style="font-size: 13px; font-weight: 500;">Full Name *</label>
                    <input type="text" name="fullName" class="form-control-glass" placeholder="John Doe" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label" style="font-size: 13px; font-weight: 500;">Email Address *</label>
                    <input type="email" name="email" class="form-control-glass" placeholder="john@example.com" required>
                </div>
            </div>

            <div class="row g-3 mb-3">
                <div class="col-md-6">
                    <label class="form-label" style="font-size: 13px; font-weight: 500;">Password *</label>
                    <input type="password" name="password" class="form-control-glass" placeholder="••••••••" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label" style="font-size: 13px; font-weight: 500;">Confirm Password *</label>
                    <input type="password" name="confirmPassword" class="form-control-glass" placeholder="••••••••" required>
                </div>
            </div>

            <div class="row g-3 mb-4">
                <div class="col-md-6">
                    <label class="form-label" style="font-size: 13px; font-weight: 500;">Monthly Income (₹)</label>
                    <input type="number" name="monthlyIncome" class="form-control-glass" placeholder="75000">
                </div>
                <div class="col-md-6">
                    <label class="form-label" style="font-size: 13px; font-weight: 500;">Occupation</label>
                    <input type="text" name="occupation" class="form-control-glass" placeholder="Engineer, Doctor, etc.">
                </div>
            </div>

            <button type="submit" class="btn-glass w-100 py-3 justify-content-center" style="border-radius: 12px; font-weight: 600;">
                <i class="fa-solid fa-user-plus"></i> Register & Launch App
            </button>
        </form>

        <div class="text-center mt-4 pt-3" style="border-top: 1px solid var(--card-glass-border);">
            <span style="font-size: 14px; color: var(--text-muted);">Already registered?</span>
            <a href="${pageContext.request.contextPath}/auth/login" style="color: var(--primary-lavender); font-weight: 600; text-decoration: none; margin-left: 6px;">Sign In</a>
        </div>
    </div>
</body>
</html>
