<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="sidebar">
    <div class="sidebar-logo">
        <i class="fa-solid fa-wallet text-gradient"></i>
        <div class="logo-text">
            <h5 class="text-gradient" style="margin: 0; font-weight: 700;">Expense Tracker</h5>
            <small style="color: var(--text-muted); font-size: 11px;">FINTECH SUITE</small>
        </div>
    </div>

    <ul class="sidebar-nav">
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('dashboard.jsp') || pageContext.request.requestURI.endsWith('/dashboard') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/dashboard">
                <i class="fa-solid fa-chart-pie"></i>
                <span class="nav-text" data-i18n="dashboard">Dashboard</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('expenses.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/expenses">
                <i class="fa-solid fa-receipt"></i>
                <span class="nav-text" data-i18n="expenses">Expenses</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('analytics.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/analytics">
                <i class="fa-solid fa-chart-line"></i>
                <span class="nav-text" data-i18n="analytics">Analytics</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('budgets.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/budgets">
                <i class="fa-solid fa-piggy-bank"></i>
                <span class="nav-text" data-i18n="budgetPlanner">Budget Planner</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('goals.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/goals">
                <i class="fa-solid fa-bullseye"></i>
                <span class="nav-text" data-i18n="goalTracker">Goal Tracker</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('insights.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/insights">
                <i class="fa-solid fa-wand-magic-sparkles"></i>
                <span class="nav-text" data-i18n="insights">Smart Insights</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('investment.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/investment-learning">
                <i class="fa-solid fa-graduation-cap"></i>
                <span class="nav-text" data-i18n="investment">Investment Learning</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('reports.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/reports">
                <i class="fa-solid fa-file-export"></i>
                <span class="nav-text" data-i18n="reports">Reports & Export</span>
            </a>
        </li>
        <li class="sidebar-nav-item ${pageContext.request.requestURI.endsWith('settings.jsp') ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/settings">
                <i class="fa-solid fa-gear"></i>
                <span class="nav-text" data-i18n="settings">Settings</span>
            </a>
        </li>
    </ul>

    <div style="padding-top: 16px; border-top: 1px solid var(--card-glass-border);">
        <a href="${pageContext.request.contextPath}/auth/logout" class="btn-outline-glass" style="width: 100%; display: flex; align-items: center; justify-content: center; gap: 8px;">
            <i class="fa-solid fa-right-from-bracket"></i>
            <span class="nav-text">Logout</span>
        </a>
    </div>
</aside>
