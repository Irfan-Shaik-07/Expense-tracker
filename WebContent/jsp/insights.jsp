<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Smart Insights - Expense Tracker</title>
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
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Smart Financial Insights</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Automated spending intelligence & personalized savings suggestions</p>
                    </div>
                </div>

                <div class="row g-4">
                    <!-- Column 1: Financial Insights -->
                    <div class="col-md-6">
                        <div class="glass-card h-100">
                            <div class="glass-card-header">
                                <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-wand-magic-sparkles text-gradient me-2"></i> Spending Intelligence</h5>
                            </div>

                            <div class="d-flex flex-direction-column gap-3">
                                <% 
                                    List<String> insights = (List<String>) request.getAttribute("smartInsights");
                                    if (insights != null) {
                                        for (String ins : insights) {
                                %>
                                <div class="p-3 mb-2" style="background: rgba(181, 126, 220, 0.08); border-radius: 12px; border-left: 4px solid var(--primary-lavender);">
                                    <div class="d-flex align-items-start gap-3">
                                        <i class="fa-solid fa-lightbulb text-warning mt-1"></i>
                                        <div style="font-size: 14px;"><%= ins %></div>
                                    </div>
                                </div>
                                <%      }
                                    } %>
                            </div>
                        </div>
                    </div>

                    <!-- Column 2: Savings Recommendations -->
                    <div class="col-md-6">
                        <div class="glass-card h-100">
                            <div class="glass-card-header">
                                <h5 style="margin: 0; font-weight: 600;"><i class="fa-solid fa-piggy-bank text-gradient me-2"></i> Smart Savings Suggestions</h5>
                            </div>

                            <div class="d-flex flex-direction-column gap-3">
                                <% 
                                    List<String> suggestions = (List<String>) request.getAttribute("savingsSuggestions");
                                    if (suggestions != null) {
                                        for (String sug : suggestions) {
                                %>
                                <div class="p-3 mb-2" style="background: rgba(0, 207, 207, 0.08); border-radius: 12px; border-left: 4px solid var(--secondary-cyan);">
                                    <div class="d-flex align-items-start gap-3">
                                        <i class="fa-solid fa-circle-check text-success mt-1"></i>
                                        <div style="font-size: 14px;"><%= sug %></div>
                                    </div>
                                </div>
                                <%      }
                                    } %>
                            </div>
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
