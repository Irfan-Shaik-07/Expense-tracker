/* Chart.js Visualizations Manager for Expense Tracker */

document.addEventListener('DOMContentLoaded', () => {
    initAllCharts();
});

function initAllCharts() {
    // Shared Glass Colors
    const colorLavender = '#B57EDC';
    const colorCyan = '#00CFCF';
    const colorPurple = '#6A0DAD';
    const colorGreen = '#2ECC71';
    const colorOrange = '#F39C12';
    const colorCoral = '#FF6B6B';
    const colorBlue = '#3498DB';
    const colorGray = '#95A5A6';

    // 1. Expense Trend Graph (Line)
    const trendCtx = document.getElementById('expenseTrendChart');
    if (trendCtx) {
        new Chart(trendCtx.getContext('2d'), {
            type: 'line',
            data: {
                labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                datasets: [{
                    label: 'Daily Spending (₹)',
                    data: [1200, 3450, 850, 2100, 4800, 1650, 2500],
                    borderColor: colorLavender,
                    backgroundColor: 'rgba(181, 126, 220, 0.15)',
                    fill: true,
                    tension: 0.4,
                    pointRadius: 5,
                    pointHoverRadius: 8
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    // 2. Monthly Expense Bar Chart
    const barCtx = document.getElementById('monthlyBarChart');
    if (barCtx) {
        new Chart(barCtx.getContext('2d'), {
            type: 'bar',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug'],
                datasets: [{
                    label: 'Monthly Total (₹)',
                    data: [42000, 38500, 45000, 41200, 49000, 36400, 51399, 18550],
                    backgroundColor: [colorLavender, colorCyan, colorPurple, colorGreen, colorOrange, colorCoral, colorLavender, colorCyan],
                    borderRadius: 8
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    // 3. Category-wise Pie Chart
    const pieCtx = document.getElementById('categoryPieChart');
    if (pieCtx) {
        new Chart(pieCtx.getContext('2d'), {
            type: 'pie',
            data: {
                labels: ['Food', 'Travel', 'Shopping', 'Bills', 'Entertainment', 'Health', 'Education'],
                datasets: [{
                    data: [14450, 14000, 4800, 49849, 2199, 1650, 3999],
                    backgroundColor: [colorCoral, colorCyan, colorLavender, colorOrange, colorPurple, colorGreen, colorBlue]
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    // 4. Doughnut Expense Distribution Chart
    const doughnutCtx = document.getElementById('distributionDoughnutChart');
    if (doughnutCtx) {
        new Chart(doughnutCtx.getContext('2d'), {
            type: 'doughnut',
            data: {
                labels: ['Essentials (Rent/Bills)', 'Lifestyle & Dining', 'Savings & Investments', 'Discretionary'],
                datasets: [{
                    data: [55, 20, 15, 10],
                    backgroundColor: [colorCyan, colorLavender, colorGreen, colorOrange]
                }]
            },
            options: { responsive: true, maintainAspectRatio: false, cutout: '70%' }
        });
    }

    // 5. Savings Progress Graph
    const savingsCtx = document.getElementById('savingsProgressChart');
    if (savingsCtx) {
        new Chart(savingsCtx.getContext('2d'), {
            type: 'line',
            data: {
                labels: ['Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug'],
                datasets: [{
                    label: 'Total Saved Accumulation (₹)',
                    data: [150000, 185000, 220000, 260000, 310000, 355000],
                    borderColor: colorGreen,
                    backgroundColor: 'rgba(46, 204, 113, 0.15)',
                    fill: true,
                    tension: 0.3
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    // 6. Budget vs Actual Expense Chart
    const bvaCtx = document.getElementById('budgetVsActualChart');
    if (bvaCtx) {
        new Chart(bvaCtx.getContext('2d'), {
            type: 'bar',
            data: {
                labels: ['Food', 'Travel', 'Shopping', 'Bills'],
                datasets: [
                    { label: 'Target Budget (₹)', data: [12000, 8000, 10000, 15000], backgroundColor: 'rgba(0, 207, 207, 0.5)' },
                    { label: 'Actual Spent (₹)', data: [9350, 5000, 4800, 12850], backgroundColor: 'rgba(181, 126, 220, 0.8)' }
                ]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    // 7. Expense Forecast Estimate Line Chart
    const forecastCtx = document.getElementById('expenseForecastChart');
    if (forecastCtx) {
        new Chart(forecastCtx.getContext('2d'), {
            type: 'line',
            data: {
                labels: ['May', 'Jun', 'Jul', 'Aug (Curr)', 'Sep (Est)', 'Oct (Est)', 'Nov (Est)'],
                datasets: [
                    {
                        label: 'Historical Spending (₹)',
                        data: [49000, 36400, 51399, 18550, null, null, null],
                        borderColor: colorLavender,
                        pointRadius: 6
                    },
                    {
                        label: 'Forecast Estimate (₹)',
                        data: [null, null, null, 18550, 42000, 44500, 41000],
                        borderColor: colorOrange,
                        borderDash: [6, 6],
                        pointRadius: 6
                    }
                ]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }

    // 8. Running Balance Graph (Area Line)
    const runCtx = document.getElementById('runningBalanceChart');
    if (runCtx) {
        new Chart(runCtx.getContext('2d'), {
            type: 'line',
            data: {
                labels: ['Aug 1', 'Aug 2', 'Aug 3', 'Aug 4', 'Aug 5', 'Aug 6', 'Aug 7'],
                datasets: [{
                    label: 'Available Account Balance (₹)',
                    data: [85000, 81550, 78700, 76600, 74100, 69300, 66450],
                    borderColor: colorCyan,
                    backgroundColor: 'rgba(0, 207, 207, 0.2)',
                    fill: true,
                    tension: 0.4
                }]
            },
            options: { responsive: true, maintainAspectRatio: false }
        });
    }
}
