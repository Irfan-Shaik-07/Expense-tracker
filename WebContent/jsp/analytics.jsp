<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Advanced Analytics - Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="app-container">
        <jsp:include page="/jsp/common/sidebar.jsp" />

        <div class="main-content">
            <jsp:include page="/jsp/common/header.jsp" />

            <div class="content-wrapper">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Advanced Financial Analytics</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Detailed breakdowns, historical averages, and predictive forecasting</p>
                    </div>
                </div>

                <!-- Stats Summary -->
                <div class="grid-3 mb-4">
                    <div class="glass-card">
                        <span style="color: var(--text-muted); font-size: 13px;">Daily Average Spending</span>
                        <h3 class="mt-1 text-gradient">₹598.38</h3>
                    </div>
                    <div class="glass-card">
                        <span style="color: var(--text-muted); font-size: 13px;">Weekly Average Spending</span>
                        <h3 class="mt-1 text-gradient">₹4,637.50</h3>
                    </div>
                    <div class="glass-card">
                        <span style="color: var(--text-muted); font-size: 13px;">Most Expensive Category</span>
                        <h3 class="mt-1 text-danger">Bills & Utilities</h3>
                    </div>
                </div>

                <!-- Charts Grid -->
                <div class="grid-2 mb-4">
                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;">Expense Distribution Doughnut</h5>
                        </div>
                        <div style="height: 300px;">
                            <canvas id="distributionDoughnutChart"></canvas>
                        </div>
                    </div>

                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;">Savings Accumulation Progress</h5>
                        </div>
                        <div style="height: 300px;">
                            <canvas id="savingsProgressChart"></canvas>
                        </div>
                    </div>
                </div>

                <div class="glass-card">
                    <div class="glass-card-header">
                        <h5 style="margin: 0; font-weight: 600;">Full Year Predictive Forecast</h5>
                        <span class="badge-glass badge-lavender">Linear Projection Engine</span>
                    </div>
                    <div style="height: 320px;">
                        <canvas id="expenseForecastChart"></canvas>
                    </div>
                </div>
            </div>

            <jsp:include page="/jsp/common/footer.jsp" />
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/js/charts.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n-currency.js"></script>
</body>
</html>
