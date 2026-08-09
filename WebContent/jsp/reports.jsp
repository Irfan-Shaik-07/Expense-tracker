<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reports & Export - Expense Tracker</title>
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
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Reports & Export Center</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Generate custom statement reports in Excel, PDF, and CSV formats</p>
                    </div>
                </div>

                <div class="grid-3 mb-4">
                    <!-- Export CSV Card -->
                    <div class="glass-card text-center py-4">
                        <div class="stat-card-icon bg-green mx-auto mb-3" style="width: 56px; height: 56px; font-size: 24px;">
                            <i class="fa-solid fa-file-csv"></i>
                        </div>
                        <h5 style="font-weight: 600;">Export CSV Data</h5>
                        <p style="font-size: 13px; color: var(--text-muted);">Download full raw transaction table for analysis in spreadsheets.</p>
                        <a href="${pageContext.request.contextPath}/reports/export?format=csv" class="btn-glass w-100 justify-content-center mt-2">
                            <i class="fa-solid fa-download"></i> Download .CSV Report
                        </a>
                    </div>

                    <!-- Export Excel Card -->
                    <div class="glass-card text-center py-4">
                        <div class="stat-card-icon bg-cyan mx-auto mb-3" style="width: 56px; height: 56px; font-size: 24px;">
                            <i class="fa-solid fa-file-excel"></i>
                        </div>
                        <h5 style="font-weight: 600;">Export Excel (.xlsx)</h5>
                        <p style="font-size: 13px; color: var(--text-muted);">Formatted spreadsheet report with calculated category summaries.</p>
                        <a href="${pageContext.request.contextPath}/reports/export?format=excel" class="btn-glass-cyan w-100 text-center mt-2" style="text-decoration: none; display: inline-block;">
                            <i class="fa-solid fa-download me-1"></i> Download .XLSX Report
                        </a>
                    </div>

                    <!-- Export PDF Card -->
                    <div class="glass-card text-center py-4">
                        <div class="stat-card-icon bg-coral mx-auto mb-3" style="width: 56px; height: 56px; font-size: 24px;">
                            <i class="fa-solid fa-file-pdf"></i>
                        </div>
                        <h5 style="font-weight: 600;">Export PDF Statement</h5>
                        <p style="font-size: 13px; color: var(--text-muted);">Print-ready official financial statement with chart analytics.</p>
                        <a href="${pageContext.request.contextPath}/reports/export?format=pdf" class="btn-outline-glass text-danger w-100 mt-2" style="text-decoration: none; display: inline-block;">
                            <i class="fa-solid fa-download me-1"></i> Download .PDF Statement
                        </a>
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
