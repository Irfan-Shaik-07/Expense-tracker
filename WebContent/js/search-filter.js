/* Smart Natural Language & Filter Search Handler */

document.addEventListener('DOMContentLoaded', () => {
    initSmartSearch();
});

function initSmartSearch() {
    const searchInput = document.getElementById('smartSearchInput');
    const tableBody = document.getElementById('expenseTableBody');

    if (searchInput && tableBody) {
        searchInput.addEventListener('input', (e) => {
            const query = e.target.value.toLowerCase().trim();
            const rows = tableBody.querySelectorAll('tr');

            rows.forEach(row => {
                const text = row.innerText.toLowerCase();
                if (!query) {
                    row.style.display = '';
                    return;
                }

                // Natural Language Parsing Rules
                let matches = text.includes(query);

                // e.g. "above 5000" or "> 5000"
                if (query.includes('above') || query.includes('>')) {
                    const amountMatch = query.match(/\d+/);
                    if (amountMatch) {
                        const targetAmt = parseFloat(amountMatch[0]);
                        const rowAmtCell = row.querySelector('.expense-amount');
                        if (rowAmtCell) {
                            const rowAmt = parseFloat(rowAmtCell.innerText.replace(/[^0-9.]/g, ''));
                            matches = rowAmt >= targetAmt;
                        }
                    }
                }

                // e.g. "food in july"
                if (query.includes('food') && text.includes('food')) {
                    if (query.includes('july') && !text.includes('jul')) matches = false;
                }

                row.style.display = matches ? '' : 'none';
            });
        });
    }
}
