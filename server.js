const express = require('express');
const path = require('path');
const fs = require('fs');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Serve static assets from WebContent
app.use('/css', express.static(path.join(__dirname, 'WebContent/css')));
app.use('/js', express.static(path.join(__dirname, 'WebContent/js')));
app.use('/uploads', express.static(path.join(__dirname, 'WebContent/uploads')));
app.use('/assets', express.static(path.join(__dirname, 'WebContent/assets')));

// In-Memory Data Store for Preview Server Session
let sampleExpenses = [
  { id: 1, userId: 1, expenseName: 'Weekly Grocery & Vegetables', categoryId: 1, categoryName: 'Food', categoryIcon: 'fa-utensils', label: 'Essential', amount: 3450.00, currency: 'INR', paymentMode: 'UPI', expenseDate: '2026-08-01', description: 'Supermarket shopping' },
  { id: 2, userId: 1, expenseName: 'Electricity Bill - July', categoryId: 4, categoryName: 'Bills', categoryIcon: 'fa-file-invoice-dollar', label: 'Utility', amount: 2850.00, currency: 'INR', paymentMode: 'UPI', expenseDate: '2026-08-02', description: 'State Power Board' },
  { id: 3, userId: 1, expenseName: 'Weekend Dinner with Friends', categoryId: 1, categoryName: 'Food', categoryIcon: 'fa-utensils', label: 'Dining', amount: 2100.00, currency: 'INR', paymentMode: 'Credit Card', expenseDate: '2026-08-03', description: 'Italian restaurant dinner' },
  { id: 4, userId: 1, expenseName: 'Fuel & Petrol refill', categoryId: 2, categoryName: 'Travel', categoryIcon: 'fa-plane', label: 'Commute', amount: 2500.00, currency: 'INR', paymentMode: 'Debit Card', expenseDate: '2026-08-04', description: 'Shell station refill' },
  { id: 5, userId: 1, expenseName: 'New Office Shoes & Shirt', categoryId: 3, categoryName: 'Shopping', categoryIcon: 'fa-bag-shopping', label: 'Apparel', amount: 4800.00, currency: 'INR', paymentMode: 'Credit Card', expenseDate: '2026-08-05', description: 'Zara monsoon sale' }
];

// Helper to render JSP templates smoothly
function renderJspView(res, jspPath, contextData = {}) {
  const fullPath = path.join(__dirname, 'WebContent', jspPath);
  if (!fs.existsSync(fullPath)) {
    return res.status(404).send('JSP View not found');
  }

  let html = fs.readFileSync(fullPath, 'utf8');

  // Replace includes
  const headerHtml = fs.readFileSync(path.join(__dirname, 'WebContent/jsp/common/header.jsp'), 'utf8');
  const sidebarHtml = fs.readFileSync(path.join(__dirname, 'WebContent/jsp/common/sidebar.jsp'), 'utf8');
  const footerHtml = fs.readFileSync(path.join(__dirname, 'WebContent/jsp/common/footer.jsp'), 'utf8');

  html = html.replace(/<jsp:include page="\/jsp\/common\/header\.jsp"\s*\/>/g, headerHtml);
  html = html.replace(/<jsp:include page="\/jsp\/common\/sidebar\.jsp"\s*\/>/g, sidebarHtml);
  html = html.replace(/<jsp:include page="\/jsp\/common\/footer\.jsp"\s*\/>/g, footerHtml);

  // Clean JSP scriptlets and directives
  html = html.replace(/<%@\s*page[^>]*%>/g, '');
  html = html.replace(/<%\s*[\s\S]*?%>/g, '');
  html = html.replace(/\${pageContext\.request\.contextPath}/g, '');
  html = html.replace(/\${currentUser\.fullName}/g, 'Alex Morgan');
  html = html.replace(/\${currentUser\.phoneNumber}/g, '+1 555-0199');
  html = html.replace(/\${currentUser\.monthlyIncome}/g, '85000');
  html = html.replace(/\${currentUser\.occupation}/g, 'Senior Software Engineer');
  html = html.replace(/\${healthScore\.overallScore}/g, '84');
  html = html.replace(/\${healthScore\.statusLabel}/g, 'Excellent');
  html = html.replace(/\${healthScore\.statusColor}/g, '#2ECC71');

  res.send(html);
}

// GET Routes
app.get('/', (req, res) => renderJspView(res, 'jsp/dashboard.jsp'));
app.get('/dashboard', (req, res) => renderJspView(res, 'jsp/dashboard.jsp'));
app.get('/expenses', (req, res) => renderJspView(res, 'jsp/expenses.jsp'));
app.get('/expenses/delete', (req, res) => res.redirect('/expenses'));
app.get('/analytics', (req, res) => renderJspView(res, 'jsp/analytics.jsp'));
app.get('/budgets', (req, res) => renderJspView(res, 'jsp/budgets.jsp'));
app.get('/goals', (req, res) => renderJspView(res, 'jsp/goals.jsp'));
app.get('/goals/delete', (req, res) => res.redirect('/goals'));
app.get('/insights', (req, res) => renderJspView(res, 'jsp/insights.jsp'));
app.get('/investment-learning', (req, res) => renderJspView(res, 'jsp/investment.jsp'));
app.get('/reports', (req, res) => renderJspView(res, 'jsp/reports.jsp'));
app.get('/settings', (req, res) => renderJspView(res, 'jsp/settings.jsp'));
app.get('/auth/login', (req, res) => renderJspView(res, 'jsp/auth/login.jsp'));
app.get('/auth/register', (req, res) => renderJspView(res, 'jsp/auth/register.jsp'));
app.get('/auth/forgot-password', (req, res) => renderJspView(res, 'jsp/auth/forgot-password.jsp'));
app.get('/auth/logout', (req, res) => res.redirect('/auth/login'));

// POST Handlers (Fixed Backend Routing)
app.post('/expenses', (req, res) => {
  const { expenseName, categoryId, amount, paymentMode, expenseDate } = req.body;
  if (expenseName && amount) {
    const newId = sampleExpenses.length + 1;
    sampleExpenses.unshift({
      id: newId,
      userId: 1,
      expenseName,
      categoryId: parseInt(categoryId) || 1,
      categoryName: categoryId == '2' ? 'Travel' : categoryId == '3' ? 'Shopping' : categoryId == '4' ? 'Bills' : 'Food',
      categoryIcon: categoryId == '2' ? 'fa-plane' : categoryId == '3' ? 'fa-bag-shopping' : categoryId == '4' ? 'fa-file-invoice-dollar' : 'fa-utensils',
      label: 'General',
      amount: parseFloat(amount) || 0,
      currency: 'INR',
      paymentMode: paymentMode || 'UPI',
      expenseDate: expenseDate || '2026-08-08',
      description: 'Added via Form'
    });
  }
  res.redirect('/expenses');
});

app.post('/expenses/add', (req, res) => res.redirect('/expenses'));
app.post('/budgets', (req, res) => res.redirect('/budgets'));
app.post('/goals', (req, res) => res.redirect('/goals'));
app.post('/goals/update-progress', (req, res) => res.redirect('/goals'));
app.post('/auth/login', (req, res) => res.redirect('/dashboard'));
app.post('/auth/register', (req, res) => res.redirect('/dashboard'));
app.post('/auth/forgot-password', (req, res) => res.redirect('/auth/login'));
app.post('/auth/verify-otp', (req, res) => res.redirect('/auth/login'));
app.post('/settings', (req, res) => res.redirect('/settings'));

// API Routes
app.post('/api/chat', (req, res) => {
  const query = (req.body && req.body.message) ? req.body.message.toLowerCase() : '';
  let reply = "I'm your AI Expense Assistant! I can help you analyze your spending, budgets, savings, and category breakdowns.";

  if (query.includes('this month') || query.includes('spend')) {
    reply = "You have spent **₹18,550.00** so far in August 2026. Your highest expense category is **Bills & Utilities**.";
  } else if (query.includes('last month')) {
    reply = "In July 2026, your total recorded spending was **₹51,399.00**.";
  } else if (query.includes('food')) {
    reply = "You spent **₹5,550.00** on Food & Dining this month.";
  } else if (query.includes('travel')) {
    reply = "You spent **₹2,500.00** on Travel & Petrol commute this month.";
  } else if (query.includes('budget')) {
    reply = "You are **within budget**! You have used **37.1%** (₹18,550 of ₹50,000). Remaining budget: **₹31,450.00**.";
  } else if (query.includes('save') || query.includes('savings')) {
    reply = "Based on your income of ₹85,000, your total savings accumulation is **₹355,000.00**.";
  } else if (query.includes('most') || query.includes('highest')) {
    reply = "Your highest spending category this month is **Bills & Utilities** at **₹2,850.00**.";
  } else if (query.includes('compare')) {
    reply = "Your spending this month (₹18,550) is **₹32,849 lower (-63.9%)** than last month (₹51,399).";
  }

  res.json({ reply, status: 'success' });
});

app.post('/api/scan-receipt', (req, res) => {
  res.json({
    merchant: "ABC Restaurant & Cafe",
    amount: 750.00,
    date: "2026-08-08",
    categoryId: 1,
    categoryName: "Food",
    confidence: "96%",
    isDuplicate: false,
    duplicateMessage: "",
    status: "success"
  });
});

app.listen(PORT, () => {
  console.log(`Expense Tracker App running live at http://localhost:${PORT}`);
});
