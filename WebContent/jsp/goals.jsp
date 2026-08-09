<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensetracker.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Financial Goals - Expense Tracker</title>
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
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Financial Goal Tracker</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Track your savings progress towards major milestones</p>
                    </div>
                    <button class="btn-glass" data-bs-toggle="modal" data-bs-target="#createGoalModal">
                        <i class="fa-solid fa-plus"></i> Create New Goal
                    </button>
                </div>

                <!-- Goals Cards Grid -->
                <div class="grid-3 mb-4">
                    <% 
                        List<Goal> goals = (List<Goal>) request.getAttribute("goals");
                        if (goals != null && !goals.isEmpty()) {
                            for (Goal g : goals) {
                    %>
                    <div class="glass-card">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <div class="d-flex align-items-center gap-3">
                                <div class="stat-card-icon bg-lavender mb-0" style="width: 44px; height: 44px;">
                                    <i class="fa-solid <%= g.getIconClass() %>"></i>
                                </div>
                                <div>
                                    <h5 style="margin: 0; font-weight: 600;"><%= g.getTitle() %></h5>
                                    <span class="badge-glass badge-cyan"><%= g.getCategory() %></span>
                                </div>
                            </div>
                        </div>

                        <div class="mb-2">
                            <div class="d-flex justify-content-between" style="font-size: 13px;">
                                <span style="color: var(--text-muted);">Saved: ₹<%= String.format("%,.0f", g.getSavedAmount()) %></span>
                                <strong>Target: ₹<%= String.format("%,.0f", g.getTargetAmount()) %></strong>
                            </div>
                            <div class="progress-glass my-2">
                                <div class="progress-bar-fill safe" style="width: <%= String.format("%.1f", g.getProgressPercentage()) %>%;"></div>
                            </div>
                            <div class="d-flex justify-content-between align-items-center" style="font-size: 12px; color: var(--text-muted);">
                                <span><i class="fa-solid fa-calendar me-1"></i> Target: <%= g.getTargetDate() %></span>
                                <strong><%= String.format("%.1f", g.getProgressPercentage()) %>% Completed</strong>
                            </div>
                        </div>

                        <div class="mt-3 pt-3 border-top d-flex gap-2">
                            <button class="btn-glass-cyan w-100 py-2" style="font-size: 13px;" onclick="openAddMoneyModal(<%= g.getId() %>)">
                                <i class="fa-solid fa-plus-circle me-1"></i> Add Savings
                            </button>
                            <a href="${pageContext.request.contextPath}/goals/delete?id=<%= g.getId() %>" class="btn-outline-glass text-danger p-2" onclick="return confirm('Delete goal?');">
                                <i class="fa-solid fa-trash"></i>
                            </a>
                        </div>
                    </div>
                    <%      }
                        } else { %>
                    <div class="col-12 glass-card text-center py-5 text-muted">
                        <i class="fa-solid fa-bullseye mb-3" style="font-size: 42px; color: var(--primary-lavender);"></i>
                        <h5>No Financial Goals Found</h5>
                        <p>Create your first goal like a Laptop, Car, Vacation, or Emergency Fund!</p>
                    </div>
                    <% } %>
                </div>
            </div>

            <jsp:include page="/jsp/common/footer.jsp" />
        </div>
    </div>

    <!-- Create Goal Modal -->
    <div class="modal fade" id="createGoalModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-card" style="border-radius: 24px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title text-gradient" style="font-weight: 700;">Create Financial Goal</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/goals" method="POST">
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Goal Title *</label>
                            <input type="text" name="title" class="form-control-glass" placeholder="e.g. MacBook Pro M3" required>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Target Amount (₹) *</label>
                                <input type="number" step="0.01" name="targetAmount" class="form-control-glass" placeholder="180000" required>
                            </div>
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Initial Saved (₹)</label>
                                <input type="number" step="0.01" name="savedAmount" class="form-control-glass" placeholder="25000">
                            </div>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Category</label>
                                <select name="category" class="form-control-glass">
                                    <option value="Gadget">Gadget / Tech</option>
                                    <option value="Vehicle">Vehicle (Bike/Car)</option>
                                    <option value="Travel">Vacation / Travel</option>
                                    <option value="Savings">Emergency Fund</option>
                                    <option value="Home">Home / Real Estate</option>
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Target Date *</label>
                                <input type="date" name="targetDate" class="form-control-glass" value="2026-12-31" required>
                            </div>
                        </div>
                        <button type="submit" class="btn-glass w-100 py-3 justify-content-center">Create Goal</button>
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
