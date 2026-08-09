<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensetracker.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Budget Planner - Expense Tracker</title>
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
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Budget Planner</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Set targets and receive automated alerts at 50%, 75%, 90%, and 100%</p>
                    </div>
                    <button class="btn-glass" data-bs-toggle="modal" data-bs-target="#setBudgetModal">
                        <i class="fa-solid fa-plus"></i> Set Category Budget
                    </button>
                </div>

                <!-- Alert Banners -->
                <div class="alert glass-card border-warning mb-4 d-flex align-items-center gap-3" style="background: rgba(243, 156, 18, 0.1);">
                    <i class="fa-solid fa-triangle-exclamation text-warning" style="font-size: 24px;"></i>
                    <div>
                        <strong class="text-warning">Budget Alert (78%)</strong>
                        <div style="font-size: 13px;">Food category spending has passed 75% threshold (₹9,350 / ₹12,000).</div>
                    </div>
                </div>

                <!-- Budgets Cards Grid -->
                <div class="grid-3 mb-4">
                    <div class="glass-card">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h5 style="margin: 0; font-weight: 600;">Overall Monthly</h5>
                            <span class="badge-glass badge-green">Safe (37%)</span>
                        </div>
                        <h3 class="text-gradient">₹18,550 <small style="font-size: 14px; color: var(--text-muted);">/ ₹50,000</small></h3>
                        <div class="progress-glass my-3">
                            <div class="progress-bar-fill safe" style="width: 37.1%;"></div>
                        </div>
                        <small style="color: var(--text-muted);">Remaining: ₹31,450</small>
                    </div>

                    <div class="glass-card">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h5 style="margin: 0; font-weight: 600;">Food & Dining</h5>
                            <span class="badge-glass badge-orange">Warning (78%)</span>
                        </div>
                        <h3 class="text-gradient">₹9,350 <small style="font-size: 14px; color: var(--text-muted);">/ ₹12,000</small></h3>
                        <div class="progress-glass my-3">
                            <div class="progress-bar-fill warning" style="width: 77.9%;"></div>
                        </div>
                        <small style="color: var(--warning-orange);">Remaining: ₹2,650</small>
                    </div>

                    <div class="glass-card">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h5 style="margin: 0; font-weight: 600;">Travel & Commute</h5>
                            <span class="badge-glass badge-green">Safe (62.5%)</span>
                        </div>
                        <h3 class="text-gradient">₹5,000 <small style="font-size: 14px; color: var(--text-muted);">/ ₹8,000</small></h3>
                        <div class="progress-glass my-3">
                            <div class="progress-bar-fill safe" style="width: 62.5%;"></div>
                        </div>
                        <small style="color: var(--text-muted);">Remaining: ₹3,000</small>
                    </div>

                    <div class="glass-card">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h5 style="margin: 0; font-weight: 600;">Shopping</h5>
                            <span class="badge-glass badge-green">Safe (48%)</span>
                        </div>
                        <h3 class="text-gradient">₹4,800 <small style="font-size: 14px; color: var(--text-muted);">/ ₹10,000</small></h3>
                        <div class="progress-glass my-3">
                            <div class="progress-bar-fill safe" style="width: 48%;"></div>
                        </div>
                        <small style="color: var(--text-muted);">Remaining: ₹5,200</small>
                    </div>
                </div>
            </div>

            <jsp:include page="/jsp/common/footer.jsp" />
        </div>
    </div>

    <!-- Set Budget Modal -->
    <div class="modal fade" id="setBudgetModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-card" style="border-radius: 24px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title text-gradient" style="font-weight: 700;">Set Budget Limit</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/budgets" method="POST">
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Target Category</label>
                            <select name="categoryId" class="form-control-glass">
                                <option value="0">Overall Total Budget</option>
                                <option value="1">Food</option>
                                <option value="2">Travel</option>
                                <option value="3">Shopping</option>
                                <option value="4">Bills</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Monthly Budget Limit (₹) *</label>
                            <input type="number" step="0.01" name="amount" class="form-control-glass" placeholder="15000" required>
                        </div>
                        <button type="submit" class="btn-glass w-100 py-3 justify-content-center">Save Budget Target</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n-currency.js"></script>
</body>
</html>
