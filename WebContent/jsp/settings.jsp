<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensetracker.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Settings - Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="app-container">
        <jsp:include page="/jsp/common/sidebar.jsp" />

        <div class="main-content">
            <jsp:include page="/jsp/common/header.jsp" />

            <div class="content-wrapper">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Settings & User Profile</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Manage your profile, preferences, security, and data backups</p>
                    </div>
                </div>

                <div class="row g-4">
                    <div class="col-md-7">
                        <div class="glass-card mb-4">
                            <h5 class="text-gradient mb-3" style="font-weight: 600;">Personal Information</h5>
                            <form action="${pageContext.request.contextPath}/settings" method="POST">
                                <input type="hidden" name="action" value="updateProfile">
                                <div class="row g-3 mb-3">
                                    <div class="col-md-6">
                                        <label class="form-label" style="font-size: 13px;">Full Name</label>
                                        <input type="text" name="fullName" class="form-control-glass" value="${currentUser.fullName}" required>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label" style="font-size: 13px;">Phone Number</label>
                                        <input type="text" name="phone" class="form-control-glass" value="${currentUser.phoneNumber}">
                                    </div>
                                </div>

                                <div class="row g-3 mb-3">
                                    <div class="col-md-6">
                                        <label class="form-label" style="font-size: 13px;">Preferred Currency</label>
                                        <select name="currency" class="form-control-glass">
                                            <option value="INR" ${currentUser.preferredCurrency == 'INR' ? 'selected' : ''}>INR (₹)</option>
                                            <option value="USD" ${currentUser.preferredCurrency == 'USD' ? 'selected' : ''}>USD ($)</option>
                                            <option value="EUR" ${currentUser.preferredCurrency == 'EUR' ? 'selected' : ''}>EUR (€)</option>
                                            <option value="GBP" ${currentUser.preferredCurrency == 'GBP' ? 'selected' : ''}>GBP (£)</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label" style="font-size: 13px;">Preferred Language</label>
                                        <select name="language" class="form-control-glass">
                                            <option value="en" ${currentUser.preferredLanguage == 'en' ? 'selected' : ''}>English</option>
                                            <option value="hi" ${currentUser.preferredLanguage == 'hi' ? 'selected' : ''}>हिंदी (Hindi)</option>
                                            <option value="te" ${currentUser.preferredLanguage == 'te' ? 'selected' : ''}>తెలుగు (Telugu)</option>
                                            <option value="ta" ${currentUser.preferredLanguage == 'ta' ? 'selected' : ''}>தமிழ் (Tamil)</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="row g-3 mb-4">
                                    <div class="col-md-6">
                                        <label class="form-label" style="font-size: 13px;">Monthly Income (₹)</label>
                                        <input type="number" step="0.01" name="monthlyIncome" class="form-control-glass" value="${currentUser.monthlyIncome}">
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label" style="font-size: 13px;">Occupation</label>
                                        <input type="text" name="occupation" class="form-control-glass" value="${currentUser.occupation}">
                                    </div>
                                </div>

                                <button type="submit" class="btn-glass px-4 py-2">Save Profile Updates</button>
                            </form>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="glass-card mb-4">
                            <h5 class="text-gradient mb-3" style="font-weight: 600;">Data Backup & Restore</h5>
                            <p style="font-size: 13px; color: var(--text-muted);">Export a JSON snapshot backup of all your expenses, budgets, and financial goals.</p>
                            <a href="${pageContext.request.contextPath}/settings/backup-export" class="btn-glass-cyan w-100 text-center mb-3" style="text-decoration: none; display: inline-block;">
                                <i class="fa-solid fa-download me-1"></i> Export Backup (.JSON)
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:include page="/jsp/common/footer.jsp" />
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n-currency.js"></script>
</body>
</html>
