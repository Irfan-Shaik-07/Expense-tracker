<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensetracker.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Expense Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/glassmorphism.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="app-container">
        <!-- Sidebar Component -->
        <jsp:include page="/jsp/common/sidebar.jsp" />

        <!-- Main Content Area -->
        <div class="main-content">
            <!-- Header Component -->
            <jsp:include page="/jsp/common/header.jsp" />

            <div class="content-wrapper">
                <!-- Welcome Banner & Health Score Summary -->
                <div class="glass-card mb-4">
                    <div class="row align-items-center">
                        <div class="col-md-7">
                            <h2 class="text-gradient" style="font-weight: 700;">Hello, Alex! 👋</h2>
                            <p style="color: var(--text-muted); font-size: 15px; margin-bottom: 12px;">Here is your real-time financial health summary and spending intelligence.</p>
                            <div class="d-flex flex-wrap gap-2">
                                <span class="badge-glass badge-lavender"><i class="fa-solid fa-crown me-1"></i> Pro Member</span>
                                <span class="badge-glass badge-cyan"><i class="fa-solid fa-shield-check me-1"></i> Safe Spending</span>
                                <span class="badge-glass badge-green"><i class="fa-solid fa-piggy-bank me-1"></i> 22% Savings Ratio</span>
                            </div>
                        </div>
                        <div class="col-md-5 mt-3 mt-md-0">
                            <!-- Financial Health Score Card -->
                            <div class="p-3" style="background: rgba(181, 126, 220, 0.1); border-radius: 16px; border: 1.5px solid var(--primary-lavender);">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <span style="font-weight: 600; font-size: 14px;">Financial Health Score</span>
                                    <span class="badge-glass" style="background: ${healthScore.statusColor}; color: #fff;">${healthScore.statusLabel}</span>
                                </div>
                                <div class="d-flex align-items-baseline gap-2">
                                    <h1 class="animate-counter text-gradient" data-target="${healthScore.overallScore}" style="font-weight: 800; font-size: 42px; margin: 0;">${healthScore.overallScore}</h1>
                                    <span style="color: var(--text-muted); font-size: 16px;">/ 100</span>
                                </div>
                                <div class="progress-glass mt-2">
                                    <div class="progress-bar-fill" style="width: ${healthScore.overallScore}%;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Animated Counter Stat Cards (4 Grid) -->
                <div class="grid-3 mb-4">
                    <!-- Stat Card 1 -->
                    <div class="glass-card">
                        <div class="stat-card-icon bg-lavender">
                            <i class="fa-solid fa-receipt"></i>
                        </div>
                        <span style="color: var(--text-muted); font-size: 13px; font-weight: 500;" data-i18n="totalExpenses">Monthly Total Expenses</span>
                        <h3 class="mt-1" style="font-weight: 700;">
                            <span class="curr-symbol">₹</span><span class="animate-counter" data-target="18550">18,550</span>
                        </h3>
                        <small style="color: var(--success-green);"><i class="fa-solid fa-arrow-down me-1"></i> 12.4% vs last month</small>
                    </div>

                    <!-- Stat Card 2 -->
                    <div class="glass-card">
                        <div class="stat-card-icon bg-cyan">
                            <i class="fa-solid fa-calendar-day"></i>
                        </div>
                        <span style="color: var(--text-muted); font-size: 13px; font-weight: 500;" data-i18n="monthlyAverage">Monthly Average</span>
                        <h3 class="mt-1" style="font-weight: 700;">
                            <span class="curr-symbol">₹</span><span class="animate-counter" data-target="42180">42,180</span>
                        </h3>
                        <small style="color: var(--text-muted);">Yearly projected trend</small>
                    </div>

                    <!-- Stat Card 3 -->
                    <div class="glass-card">
                        <div class="stat-card-icon bg-orange">
                            <i class="fa-solid fa-wallet"></i>
                        </div>
                        <span style="color: var(--text-muted); font-size: 13px; font-weight: 500;" data-i18n="remainingBudget">Remaining Budget</span>
                        <h3 class="mt-1" style="font-weight: 700;">
                            <span class="curr-symbol">₹</span><span class="animate-counter" data-target="31450">31,450</span>
                        </h3>
                        <small style="color: var(--warning-orange);"><i class="fa-solid fa-clock me-1"></i> 24 days left in month</small>
                    </div>

                    <!-- Stat Card 4 -->
                    <div class="glass-card">
                        <div class="stat-card-icon bg-green">
                            <i class="fa-solid fa-vault"></i>
                        </div>
                        <span style="color: var(--text-muted); font-size: 13px; font-weight: 500;" data-i18n="savings">Total Saved</span>
                        <h3 class="mt-1" style="font-weight: 700;">
                            <span class="curr-symbol">₹</span><span class="animate-counter" data-target="355000">355,000</span>
                        </h3>
                        <small style="color: var(--success-green);"><i class="fa-solid fa-arrow-up me-1"></i> +₹22,000 added</small>
                    </div>
                </div>

                <!-- Charts Row 1: Expense Trend & Category Pie -->
                <div class="grid-2 mb-4">
                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-chart-line text-gradient me-2"></i> Expense Trend (Daily)</h5>
                            <span class="badge-glass badge-lavender">Aug 2026</span>
                        </div>
                        <div style="height: 280px;">
                            <canvas id="expenseTrendChart"></canvas>
                        </div>
                    </div>

                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-chart-pie text-gradient me-2"></i> Category Breakdown</h5>
                            <span class="badge-glass badge-cyan">Current Month</span>
                        </div>
                        <div style="height: 280px;">
                            <canvas id="categoryPieChart"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Charts Row 2: Monthly Comparison Bar & Budget vs Actual -->
                <div class="grid-2 mb-4">
                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-chart-column text-gradient me-2"></i> Monthly Expense Comparison</h5>
                            <span class="badge-glass badge-green">8 Months</span>
                        </div>
                        <div style="height: 280px;">
                            <canvas id="monthlyBarChart"></canvas>
                        </div>
                    </div>

                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-scale-balanced text-gradient me-2"></i> Budget vs. Actual Spending</h5>
                            <span class="badge-glass badge-orange">Alerts Active</span>
                        </div>
                        <div style="height: 280px;">
                            <canvas id="budgetVsActualChart"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Charts Row 3: Expense Forecast Estimate & Running Balance -->
                <div class="grid-2 mb-4">
                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-wand-magic-sparkles text-gradient me-2"></i> Expense Forecast (Estimate)</h5>
                            <span class="badge-glass badge-lavender">Predictive Engine</span>
                        </div>
                        <div style="height: 280px;">
                            <canvas id="expenseForecastChart"></canvas>
                        </div>
                    </div>

                    <div class="glass-card">
                        <div class="glass-card-header">
                            <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-building-columns text-gradient me-2"></i> Running Account Balance</h5>
                            <span class="badge-glass badge-cyan">Real-time</span>
                        </div>
                        <div style="height: 280px;">
                            <canvas id="runningBalanceChart"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Recent Transactions & Quick Action -->
                <div class="glass-card mb-4">
                    <div class="glass-card-header">
                        <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-clock-rotate-left text-gradient me-2"></i> Recent Transactions</h5>
                        <a href="${pageContext.request.contextPath}/expenses" class="btn-outline-glass" style="font-size: 13px;">View All Expenses</a>
                    </div>

                    <div class="table-responsive">
                        <table class="table-glass">
                            <thead>
                                <tr>
                                    <th>Transaction Name</th>
                                    <th>Category</th>
                                    <th>Date</th>
                                    <th>Payment Mode</th>
                                    <th>Amount</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    List<Expense> recent = (List<Expense>) request.getAttribute("recentTransactions");
                                    if (recent != null && !recent.isEmpty()) {
                                        for (Expense e : recent) {
                                %>
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center gap-3">
                                            <div class="stat-card-icon bg-lavender mb-0" style="width: 38px; height: 38px; font-size: 16px;">
                                                <i class="fa-solid <%= e.getCategoryIcon() != null ? e.getCategoryIcon() : "fa-tag" %>"></i>
                                            </div>
                                            <div>
                                                <strong style="font-size: 14px;"><%= e.getExpenseName() %></strong>
                                                <div style="font-size: 11px; color: var(--text-muted);"><%= e.getLabel() %></div>
                                            </div>
                                        </div>
                                    </td>
                                    <td><span class="badge-glass badge-cyan"><%= e.getCategoryName() %></span></td>
                                    <td><%= e.getExpenseDate() %></td>
                                    <td><%= e.getPaymentMode() %></td>
                                    <td><strong style="color: var(--danger-coral);"><%= e.getCurrency() %> <%= String.format("%,.2f", e.getAmount()) %></strong></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/expenses/delete?id=<%= e.getId() %>" class="btn-outline-glass text-danger p-2" onclick="return confirm('Delete this expense?');">
                                            <i class="fa-solid fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                                <%      }
                                    } else { %>
                                <tr><td colspan="6" class="text-center py-4 text-muted">No transactions recorded yet.</td></tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Footer Component -->
            <jsp:include page="/jsp/common/footer.jsp" />
        </div>
    </div>

    <!-- Floating Action Button (Quick Add Expense) -->
    <button class="fab-btn" data-bs-toggle="modal" data-bs-target="#addExpenseModal">
        <i class="fa-solid fa-plus"></i>
    </button>

    <!-- Add Expense Modal -->
    <div class="modal fade" id="addExpenseModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-card" style="border-radius: 24px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title text-gradient" style="font-weight: 700;">Add New Expense</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/expenses" method="POST" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Expense Title *</label>
                            <input type="text" name="expenseName" class="form-control-glass" placeholder="e.g. Grocery at Supermarket" required>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Category *</label>
                                <select name="categoryId" class="form-control-glass" required>
                                    <option value="1">Food</option>
                                    <option value="2">Travel</option>
                                    <option value="3">Shopping</option>
                                    <option value="4">Bills</option>
                                    <option value="5">Entertainment</option>
                                    <option value="6">Education</option>
                                    <option value="7">Health</option>
                                    <option value="8">Others</option>
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Amount *</label>
                                <input type="number" step="0.01" name="amount" class="form-control-glass" placeholder="2500.00" required>
                            </div>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Payment Mode</label>
                                <select name="paymentMode" class="form-control-glass">
                                    <option value="UPI">UPI</option>
                                    <option value="Credit Card">Credit Card</option>
                                    <option value="Debit Card">Debit Card</option>
                                    <option value="Cash">Cash</option>
                                    <option value="Net Banking">Net Banking</option>
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Date *</label>
                                <input type="date" name="expenseDate" class="form-control-glass" value="2026-08-07" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Receipt Upload (Image/PDF)</label>
                            <input type="file" name="receipt" class="form-control-glass" accept="image/*,application/pdf">
                        </div>
                        <button type="submit" class="btn-glass w-100 py-3 justify-content-center mt-2">Save Expense</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/js/charts.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n-currency.js"></script>
</body>
</html>
