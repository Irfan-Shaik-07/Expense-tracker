<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensetracker.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Investment Learning Center - Expense Tracker</title>
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
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Investment Learning Center</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Comprehensive educational guide on asset classes, risks, tax rules, and historical returns</p>
                    </div>
                </div>

                <div class="alert glass-card border-info mb-4" style="background: rgba(0, 207, 207, 0.08);">
                    <i class="fa-solid fa-circle-info text-cyan me-2"></i>
                    <strong>Educational Disclaimer:</strong> Historical returns marked below represent long-term past market ranges and are not guaranteed. No guaranteed profits or quick-rich promises.
                </div>

                <div class="grid-3 mb-4">
                    <% 
                        List<InvestmentOption> list = (List<InvestmentOption>) request.getAttribute("investmentOptions");
                        if (list != null) {
                            for (InvestmentOption opt : list) {
                    %>
                    <div class="glass-card d-flex flex-column justify-content-between">
                        <div>
                            <div class="d-flex align-items-center gap-3 mb-3">
                                <div class="stat-card-icon bg-lavender mb-0" style="width: 44px; height: 44px;">
                                    <i class="fa-solid <%= opt.getIconClass() %>"></i>
                                </div>
                                <div>
                                    <h5 style="margin: 0; font-weight: 600;"><%= opt.getName() %></h5>
                                    <span class="badge-glass badge-cyan"><%= opt.getCategory() %></span>
                                </div>
                            </div>

                            <p style="font-size: 13px; color: var(--text-muted); min-height: 40px;"><%= opt.getDescription() %></p>

                            <div class="p-3 mb-3" style="background: rgba(0,0,0,0.03); border-radius: 12px; font-size: 12px;">
                                <div class="d-flex justify-content-between mb-1">
                                    <span style="color: var(--text-muted);">Risk Level:</span>
                                    <strong><%= opt.getRiskLevel() %></strong>
                                </div>
                                <div class="d-flex justify-content-between mb-1">
                                    <span style="color: var(--text-muted);">Investment Horizon:</span>
                                    <strong><%= opt.getInvestmentHorizon() %></strong>
                                </div>
                                <div class="d-flex justify-content-between mb-1">
                                    <span style="color: var(--text-muted);">Liquidity:</span>
                                    <strong><%= opt.getLiquidity() %></strong>
                                </div>
                                <div class="d-flex justify-content-between mt-2 pt-2 border-top">
                                    <span style="color: var(--text-muted);">Historical Returns:</span>
                                    <strong style="color: var(--success-green);"><%= opt.getHistoricalReturns() %></strong>
                                </div>
                            </div>
                        </div>

                        <div style="font-size: 11px; color: var(--text-muted); background: rgba(181, 126, 220, 0.08); padding: 8px 12px; border-radius: 8px;">
                            <i class="fa-solid fa-file-invoice-dollar me-1"></i> <strong>Tax Info:</strong> <%= opt.getTaxInformation() %>
                        </div>
                    </div>
                    <%      }
                        } %>
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
