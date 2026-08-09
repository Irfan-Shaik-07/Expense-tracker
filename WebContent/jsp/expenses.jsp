<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.expensetracker.model.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Expenses Management - Expense Tracker</title>
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
                <!-- Page Title & Actions -->
                <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
                    <div>
                        <h2 class="text-gradient" style="font-weight: 700; margin: 0;">Expense Management</h2>
                        <p style="color: var(--text-muted); font-size: 14px; margin: 0;">Manage, filter, search, and track all your transactions</p>
                    </div>
                    <div class="d-flex gap-2">
                        <button class="btn-glass-cyan" data-bs-toggle="modal" data-bs-target="#aiScannerModal">
                            <i class="fa-solid fa-wand-magic-sparkles"></i> AI Receipt Scanner
                        </button>
                        <button class="btn-glass" data-bs-toggle="modal" data-bs-target="#addExpenseModal">
                            <i class="fa-solid fa-plus"></i> Add New Expense
                        </button>
                    </div>
                </div>

                <!-- AI Receipt Scanner Dropzone Feature Card -->
                <div class="glass-card mb-4">
                    <div class="row align-items-center">
                        <div class="col-md-8">
                            <h5 style="font-weight: 700;" class="text-gradient mb-1"><i class="fa-solid fa-camera text-cyan me-2"></i> AI-Powered Bill Scanner Agent</h5>
                            <p style="color: var(--text-muted); font-size: 13px; margin: 0;">Upload any food bill or receipt. AI will extract merchant, total, date & detect category for user confirmation!</p>
                        </div>
                        <div class="col-md-4 text-end mt-2 mt-md-0">
                            <button class="btn-glass-cyan px-4" data-bs-toggle="modal" data-bs-target="#aiScannerModal">
                                <i class="fa-solid fa-upload me-1"></i> Scan Food Bill / Receipt
                            </button>
                        </div>
                    </div>
                </div>

                <!-- Natural Language & Multi-Filter Search Bar -->
                <div class="glass-card mb-4">
                    <div class="row g-3 align-items-center">
                        <div class="col-md-6">
                            <div class="position-relative">
                                <input type="text" id="smartSearchInput" class="form-control-glass ps-5" placeholder="Try natural search e.g. 'Food in July', 'Travel above 5000'...">
                                <i class="fa-solid fa-magnifying-glass position-absolute" style="left: 16px; top: 16px; color: var(--text-muted);"></i>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <select class="form-control-glass">
                                <option value="">All Categories</option>
                                <option value="1">Food</option>
                                <option value="2">Travel</option>
                                <option value="3">Shopping</option>
                                <option value="4">Bills</option>
                            </select>
                        </div>
                        <div class="col-md-3 text-end">
                            <a href="${pageContext.request.contextPath}/reports/export?format=csv" class="btn-outline-glass me-2">
                                <i class="fa-solid fa-file-csv"></i> Export CSV
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Expense Table -->
                <div class="glass-card">
                    <div class="table-responsive">
                        <table class="table-glass">
                            <thead>
                                <tr>
                                    <th>Expense Title</th>
                                    <th>Category</th>
                                    <th>Payment Mode</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Receipt</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody id="expenseTableBody">
                                <% 
                                    List<Expense> list = (List<Expense>) request.getAttribute("expenses");
                                    if (list != null && !list.isEmpty()) {
                                        for (Expense e : list) {
                                %>
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center gap-3">
                                            <div class="stat-card-icon bg-lavender mb-0" style="width: 38px; height: 38px; font-size: 16px;">
                                                <i class="fa-solid <%= e.getCategoryIcon() != null ? e.getCategoryIcon() : "fa-tag" %>"></i>
                                            </div>
                                            <div>
                                                <strong style="font-size: 14px;"><%= e.getExpenseName() %></strong>
                                                <div style="font-size: 11px; color: var(--text-muted);"><%= e.getDescription() != null ? e.getDescription() : e.getLabel() %></div>
                                            </div>
                                        </div>
                                    </td>
                                    <td><span class="badge-glass badge-cyan"><%= e.getCategoryName() %></span></td>
                                    <td><%= e.getPaymentMode() %></td>
                                    <td><%= e.getExpenseDate() %></td>
                                    <td><strong class="expense-amount" style="color: var(--danger-coral);"><%= e.getCurrency() %> <%= String.format("%,.2f", e.getAmount()) %></strong></td>
                                    <td>
                                        <% if (e.getReceiptPath() != null) { %>
                                            <a href="${pageContext.request.contextPath}/<%= e.getReceiptPath() %>" target="_blank" class="badge-glass badge-green" style="text-decoration: none;">
                                                <i class="fa-solid fa-paperclip"></i> View Receipt
                                            </a>
                                        <% } else { %>
                                            <span style="color: var(--text-muted); font-size: 12px;">No Receipt</span>
                                        <% } %>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/expenses/delete?id=<%= e.getId() %>" class="btn-outline-glass text-danger p-2" onclick="return confirm('Delete this expense?');">
                                            <i class="fa-solid fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                                <%      }
                                    } else { %>
                                <tr><td colspan="7" class="text-center py-4 text-muted">No expenses found matching criteria.</td></tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <jsp:include page="/jsp/common/footer.jsp" />
        </div>
    </div>

    <!-- AI Receipt Scanner Upload Modal -->
    <div class="modal fade" id="aiScannerModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-card" style="border-radius: 24px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title text-gradient" style="font-weight: 700;"><i class="fa-solid fa-wand-magic-sparkles text-cyan me-2"></i> AI Expense Scanner Agent</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="dropzone-glass mb-3" id="receiptDropzone" onclick="document.getElementById('aiFileInput').click();">
                        <i class="fa-solid fa-file-invoice-dollar mb-2 text-gradient" style="font-size: 38px;"></i>
                        <h6 style="font-weight: 600;">Drag & Drop Food Bill / Receipt Here</h6>
                        <p style="font-size: 12px; color: var(--text-muted); margin: 0;">Supported: JPG, PNG, PDF</p>
                        <button type="button" class="btn-outline-glass mt-3 py-1 px-3" style="font-size: 12px;">Choose File</button>
                        <input type="file" id="aiFileInput" style="display: none;" accept="image/*,application/pdf" onchange="handleReceiptScan(this.files[0])">
                    </div>
                    <div id="scanStatusArea" style="display: none;" class="text-center py-3">
                        <div class="spinner-border text-cyan mb-2" role="status"></div>
                        <div style="font-size: 13px; font-weight: 500;">AI Agent is scanning bill & extracting items...</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- AI Extracted Receipt Confirmation Modal -->
    <div class="modal fade" id="aiConfirmModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-card" style="border-radius: 24px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title text-gradient" style="font-weight: 700;">Confirm AI Extracted Expense</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <!-- Duplicate Alert Banner if detected -->
                    <div id="duplicateAlertBanner" class="alert alert-warning mb-3" style="display: none; font-size: 13px;">
                        <i class="fa-solid fa-triangle-exclamation me-1"></i> <span id="duplicateAlertMsg"></span>
                    </div>

                    <form action="${pageContext.request.contextPath}/expenses" method="POST">
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Merchant / Expense Title *</label>
                            <input type="text" id="extractedMerchant" name="expenseName" class="form-control-glass" required>
                        </div>
                        <div class="row g-2 mb-3">
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Category *</label>
                                <select id="extractedCategory" name="categoryId" class="form-control-glass" required>
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
                                <label class="form-label" style="font-size: 13px;">Total Amount (₹) *</label>
                                <input type="number" step="0.01" id="extractedAmount" name="amount" class="form-control-glass" required>
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
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Date *</label>
                                <input type="date" id="extractedDate" name="expenseDate" class="form-control-glass" required>
                            </div>
                        </div>
                        <div class="d-flex gap-2 mt-4">
                            <button type="submit" class="btn-glass w-100 py-3 justify-content-center">Confirm & Add Expense</button>
                            <button type="button" class="btn-outline-glass px-4" data-bs-dismiss="modal">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Manual Add Expense Modal -->
    <div class="modal fade" id="addExpenseModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content glass-card" style="border-radius: 24px;">
                <div class="modal-header border-0 pb-0">
                    <h5 class="modal-title text-gradient" style="font-weight: 700;">Add Expense</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/expenses" method="POST" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Expense Title *</label>
                            <input type="text" name="expenseName" class="form-control-glass" placeholder="e.g. Office Lunch" required>
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
                                <input type="number" step="0.01" name="amount" class="form-control-glass" placeholder="1500.00" required>
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
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="form-label" style="font-size: 13px;">Date *</label>
                                <input type="date" name="expenseDate" class="form-control-glass" value="2026-08-08" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" style="font-size: 13px;">Receipt Upload (Image/PDF)</label>
                            <input type="file" name="receipt" class="form-control-glass" accept="image/*,application/pdf">
                        </div>
                        <button type="submit" class="btn-glass w-100 py-3 justify-content-center">Save Expense</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script src="${pageContext.request.contextPath}/js/search-filter.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n-currency.js"></script>
    <script>
        async function handleReceiptScan(file) {
            if (!file) return;
            const statusArea = document.getElementById('scanStatusArea');
            if (statusArea) statusArea.style.display = 'block';

            const formData = new FormData();
            formData.append('receiptFile', file);

            try {
                const response = await fetch(`${window.location.origin}/api/scan-receipt`, {
                    method: 'POST',
                    body: formData
                });
                const data = await response.json();
                if (statusArea) statusArea.style.display = 'none';

                // Close Scanner Modal & Open Confirmation Modal
                const scannerModalEl = document.getElementById('aiScannerModal');
                const scannerModal = bootstrap.Modal.getInstance(scannerModalEl);
                if (scannerModal) scannerModal.hide();

                document.getElementById('extractedMerchant').value = data.merchant || 'Restaurant Bill';
                document.getElementById('extractedAmount').value = data.amount || 750;
                document.getElementById('extractedCategory').value = data.categoryId || 1;
                document.getElementById('extractedDate').value = data.date || '2026-08-08';

                const alertBanner = document.getElementById('duplicateAlertBanner');
                if (data.isDuplicate) {
                    alertBanner.style.display = 'block';
                    document.getElementById('duplicateAlertMsg').textContent = data.duplicateMessage || 'Possible duplicate expense detected.';
                } else {
                    alertBanner.style.display = 'none';
                }

                const confirmModal = new bootstrap.Modal(document.getElementById('aiConfirmModal'));
                confirmModal.show();
            } catch (err) {
                console.error("Scan error:", err);
                if (statusArea) statusArea.style.display = 'none';
                alert("Could not process receipt. Please enter details manually.");
            }
        }
    </script>
</body>
</html>
